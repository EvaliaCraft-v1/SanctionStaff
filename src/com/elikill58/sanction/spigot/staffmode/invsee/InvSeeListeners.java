package com.elikill58.sanction.spigot.staffmode.invsee;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.Inventory;

public class InvSeeListeners implements Listener {

	@EventHandler
	public void onInventoryInteract(InventoryInteractEvent e) {
		if(e.getWhoClicked() instanceof Player cible) {
			for(Entry<Player, OfflinePlayer> entries : new HashMap<>(InvSeeManager.getViewing()).entrySet()) {
				if(entries.getValue() == cible) {
					Player p = entries.getKey();
					Inventory open = p.getOpenInventory() != null ? p.getOpenInventory().getTopInventory() : null;
					if(open == null) { // inv closed
						InvSeeManager.getViewing().remove(p);
						return;
					}
					InvSeeManager.updateInventory(p, cible, open, e.getInventory());
				}
			}
		}
	}
}
