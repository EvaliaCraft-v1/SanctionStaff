package com.elikill58.sanction.spigot.staffmode;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.elikill58.sanction.spigot.SanctionSpigot;

public class StaffModeInventory {
	
	public static boolean couldBeStaffMode(UUID uuid) {
		return YamlConfiguration.loadConfiguration(getFile()).contains(uuid.toString());
	}

	private static File getFile() {
		File f = new File(SanctionSpigot.getInstance().getDataFolder(), "staffmode-save-inv.yml");
		if(!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return f;
	}
	
	public static void save(Player p, PlayerInventory inv) {
		UUID uuid = p.getUniqueId();
		File f = getFile();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		setInConfig(config, uuid.toString() + ".armor.", inv.getArmorContents());
		setInConfig(config, uuid.toString() + ".content.", inv.getContents());
		config.set(uuid + ".fly_mode", p.getAllowFlight());
		try {
			config.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void restore(Player p, PlayerInventory inv) {
		UUID uuid = p.getUniqueId();
		File f = getFile();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		inv.setArmorContents(getFromConfig(config, uuid.toString() + ".armor.", inv.getArmorContents()));
		inv.setContents(getFromConfig(config, uuid.toString() + ".content.", inv.getContents()));
		p.setAllowFlight(config.getBoolean(uuid + ".fly_mode", false));
		config.set(uuid.toString(), null); // remove old content
		try {
			config.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void setInConfig(YamlConfiguration config, String prefix, ItemStack[] items) {
		for(int i = 0; i < items.length; i++) {
			config.set(prefix + i, items[i]);
		}
	}
	
	private static ItemStack[] getFromConfig(YamlConfiguration config, String prefix, ItemStack[] items) {
		ItemStack[] res = new ItemStack[items.length];
		for(int i = 0; i < items.length; i++) {
			res[i] = config.getItemStack(prefix + i);
		}
		return res;
	}
}
