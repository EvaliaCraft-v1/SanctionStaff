package com.elikill58.sanction.spigot.staffmode.invsee;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.elikill58.sanction.spigot.utils.PacketUtils;
import com.mojang.authlib.GameProfile;

public class InvSeeManager {

	private static final HashMap<Player, OfflinePlayer> VIEWING = new HashMap<>();

	public static HashMap<Player, OfflinePlayer> getViewing() {
		return VIEWING;
	}

	public static void open(Player p, OfflinePlayer cible) {
		VIEWING.put(p, cible);
		Inventory open = Bukkit.createInventory(null, 54, "tmp");
		updateInventory(p, cible, open, (cible instanceof Player cp ? cp : load(cible, p.getWorld())).getInventory());
		p.openInventory(open);
	}

	public static void close(Player p, OfflinePlayer cible) {
		VIEWING.remove(p);
		p.closeInventory();
	}

	public static void updateInventory(Player p, OfflinePlayer cible, Inventory open, Inventory source) {
		if (source == null)
			return;
		ItemStack[] content = source.getContents();
		for (int i = 0; i < content.length; i++) {
			open.setItem(i + 9, content[i]);
		}
	}

	public static Player load(OfflinePlayer offline, World w) {
		try {
			GameProfile profile = new GameProfile(offline.getUniqueId(), offline.getName());
			Object server = PacketUtils.getOBCClass("CraftServer").getDeclaredMethod("getServer").invoke(Bukkit.getServer());
			Object ws = PacketUtils.getWorldServer(w);
			Class<?> entityPlayerClass = PacketUtils.getNmsClass("EntityPlayer", "server.level.");
			Object entityPlayer = entityPlayerClass
					.getConstructor(PacketUtils.getNmsClass("MinecraftServer", "server."), PacketUtils.getNmsClass("WorldServer", "server.level."), GameProfile.class)
					.newInstance(server, ws, profile);

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
}
