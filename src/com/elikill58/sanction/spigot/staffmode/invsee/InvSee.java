package com.elikill58.sanction.spigot.staffmode.invsee;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.elikill58.sanction.spigot.Msg;
import com.elikill58.sanction.spigot.SanctionSpigot;
import com.elikill58.sanction.spigot.utils.ItemStackBuilder;
import com.elikill58.sanction.spigot.utils.Items;
import com.elikill58.sanction.spigot.utils.PacketUtils;
import com.elikill58.sanction.universal.UniversalUtils;
import com.mojang.authlib.GameProfile;

public class InvSee {
	
	private static final List<Player> spectating = new ArrayList<>();

	public static List<Player> getSpectating() {
		return spectating;
	}
	
	public static void open(Player p, OfflinePlayer cible) {
		spectating.add(p);
		Inventory inv = Bukkit.createInventory(new InvSeeHolder(p, cible), 54, Msg.getMsg("invsee.inv_name", "%name%", cible.getName()));
		update(p, cible, inv);
		p.openInventory(inv);
	}

	public static Player load(OfflinePlayer offline, World w) {
		try {
			GameProfile profile = new GameProfile(offline.getUniqueId(), offline.getName());
			Object server = PacketUtils.getOBCClass("CraftServer").getDeclaredMethod("getServer").invoke(Bukkit.getServer());
			Object ws = PacketUtils.getWorldServer(w);
			Class<?> entityPlayerClass = PacketUtils.getNmsClass("EntityPlayer", "server.level.");
			Class<?> clientinformationClass = PacketUtils.getNmsClass("ClientInformation", "server.level.");
			Object clientInformation = clientinformationClass.getDeclaredMethod("a").invoke(null);
			Object entityPlayer = entityPlayerClass
					.getConstructor(PacketUtils.getNmsClass("MinecraftServer", "server."), PacketUtils.getNmsClass("WorldServer", "server.level."), GameProfile.class, clientinformationClass)
					.newInstance(server, ws, profile, clientInformation);

			// Get the bukkit entity
			Player cible = (Player) entityPlayerClass.getMethod("getBukkitEntity").invoke(entityPlayer);
			if (cible != null) {
				// Load data
				cible.loadData();
			}
			return cible;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void update(Player p, OfflinePlayer cible, Inventory inv) {
		boolean real = cible instanceof Player;
		Player oc = real ? (Player) cible : load(cible, p.getWorld());
		PlayerInventory source = oc.getInventory();
		for(int i = 0; i < 18; i++)
			inv.setItem(i, Items.EMPTY);
		
		ConfigurationSection config = SanctionSpigot.getInstance().getConfig().getConfigurationSection("messages.invsee.head");
		Object[] placeholders = new Object[] { "%name%", cible.getName(), "%uuid%", cible.getName(), "%ping%", real ? oc.getPing() : -1 };
		inv.setItem(1, new ItemStackBuilder(Material.PLAYER_HEAD).tryOwner(cible).displayName(UniversalUtils.replacePlaceholder(config.getString("name"), placeholders)).lore(UniversalUtils.replacePlaceholder(config.getStringList("lore"), placeholders)).build());
		inv.setItem(2, new ItemStackBuilder(Material.EXPERIENCE_BOTTLE, oc.getExpToLevel() == 0 ? 1 : (oc.getExpToLevel() > 64 ? 64 : oc.getExpToLevel())).displayName(Msg.getMsg("invsee.exp_level", "%xp%", oc.getExpToLevel())).build());
		
		inv.setItem(4, source.getHelmet());
		inv.setItem(5, source.getChestplate());
		inv.setItem(6, source.getLeggings());
		inv.setItem(7, source.getBoots());
		
		ItemStack[] items = source.getContents();
		for(int i = 0; i < 9; i++) {
			inv.setItem(45 + i, items[i]);
		}
		for(int i = 0; i < 27; i++) {
			inv.setItem(18 + i, items[i + 9]);
		}
	}
	
}
