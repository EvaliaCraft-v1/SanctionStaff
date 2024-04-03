package com.elikill58.sanction.spigot.listeners;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.elikill58.sanction.spigot.SanctionSpigot;
import com.elikill58.sanction.spigot.utils.SpigotToBungee;

public class BlockListener implements Listener {
	
	private static final HashMap<Material, String> BLOCK_ALERTS = new HashMap<>();

	public static void load(SanctionSpigot pl) {
		BLOCK_ALERTS.clear();
		ConfigurationSection section = pl.getConfig().getConfigurationSection("block_alert");
		for(String key : section.getKeys(false)) {
			Material type = Material.matchMaterial(key);
			if(type == null)
				pl.getLogger().warning("Impossible de trouver le type " + key);
			else
				BLOCK_ALERTS.put(type, section.getString(key, key));
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if(p.getGameMode().equals(GameMode.CREATIVE))
			return;
		Material type = e.getBlock().getType();
		for(Entry<Material, String> entries : BLOCK_ALERTS.entrySet()) {
			if(type.equals(entries.getKey())) {
				SpigotToBungee.sendAlertXrayToBungee(p, entries.getValue());
				return;
			}
		}
	}
}
