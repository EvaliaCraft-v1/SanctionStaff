package com.elikill58.sanction.spigot.staffmode;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.elikill58.sanction.spigot.SanctionSpigot;
import com.elikill58.sanction.spigot.utils.ItemStackBuilder;
import com.elikill58.sanction.spigot.utils.PacketUtils;
import com.elikill58.sanction.universal.UniversalUtils;
import com.mojang.authlib.GameProfile;

public class InvEnderSee {


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
	
	public static ItemStack createHead(OfflinePlayer cible, Player oc, boolean real) {
		ConfigurationSection config = SanctionSpigot.getInstance().getConfig().getConfigurationSection("messages.invsee.head");
		Object[] placeholders = new Object[] { "%name%", cible.getName(), "%uuid%", cible.getName(), "%ping%", real ? oc.getPing() : -1 };
		return new ItemStackBuilder(Material.PLAYER_HEAD).tryOwner(cible).displayName(UniversalUtils.replacePlaceholder(config.getString("name"), placeholders)).lore(UniversalUtils.replacePlaceholder(config.getStringList("lore"), placeholders)).build();
	}
}
