package com.elikill58.sanction.spigot.staffmode.invender;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import com.elikill58.sanction.spigot.SanctionSpigot;

public class InvEnderSeeListeners implements Listener {

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		if (e.getPlayer() instanceof Player p && e.getInventory() != null && e.getInventory().getHolder() != null && e.getInventory().getHolder() instanceof InvEnderHolder)
			InvEnderSee.getSpectating().remove(p);
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getClickedInventory() == null || !(e.getWhoClicked() instanceof Player p))
			return;
		Inventory inv = e.getClickedInventory();
		int slot = e.getSlot();
		if (!InvEnderSee.getSpectating().isEmpty()) {
			if (inv.getHolder() != null && inv.getHolder() instanceof InvEnderHolder holder) {
				InvEnderType type = holder.getType();
				OfflinePlayer cible = holder.getCible();
				if (!type.hasInteractPermission(p) || type.getSlots().contains(slot) || e.getAction().equals(InventoryAction.COLLECT_TO_CURSOR))
					e.setCancelled(true);
				else { // change item into other inv
					Bukkit.getScheduler().runTaskLater(SanctionSpigot.getInstance(), () -> {
						type.setItemInInventory(p, cible, slot, inv.getItem(slot));
						type.update(p, cible, inv);
					}, 0);
				}
			}
			if(!e.isCancelled()) {
				InvEnderSee.getSpectating().forEach((staff, type) -> {
					if (staff.getOpenInventory() != null && staff.getOpenInventory().getTopInventory() != null && staff.getOpenInventory().getTopInventory().getHolder() != null) {
						Inventory topInv = staff.getOpenInventory().getTopInventory();
						if (topInv.getHolder() instanceof InvEnderHolder holder) {
							if (holder.getCible().getUniqueId().equals(p.getUniqueId())) {
								holder.getType().update(holder.getPlayer(), p, topInv);
							}
						}
					}
				});
			}
		}
	}
}
