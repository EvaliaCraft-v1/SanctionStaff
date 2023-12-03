package com.elikill58.sanction.spigot.listeners;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.elikill58.sanction.spigot.SanctionSpigot;
import com.elikill58.sanction.spigot.utils.SpigotToBungee;

public class BlockListener implements Listener {

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if(p.getGameMode().equals(GameMode.CREATIVE))
			return;
		Material type = e.getBlock().getType();
		for(String blocks : SanctionSpigot.getInstance().getConfig().getStringList("block_alert")) {
			if(type.name().contains(blocks.toUpperCase())) {
				String name = "";
				boolean upper = true;
				for(String c : type.name().split("")) {
					if(c == "_") {
						name += " ";
						upper = true;
					} else if(upper) {
						name += c.toUpperCase();
						upper = false;
					} else
						name += c.toLowerCase();
				}
				SpigotToBungee.sendAlertXrayToBungee(p, name);
				return;
			}
		}
	}
}
