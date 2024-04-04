package com.elikill58.sanction.spigot.staffmode;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.Inventory;

import com.elikill58.sanction.spigot.staffmode.endersee.EnderSee;
import com.elikill58.sanction.spigot.staffmode.endersee.EnderSeeHolder;
import com.elikill58.sanction.spigot.staffmode.invsee.InvSee;
import com.elikill58.sanction.spigot.staffmode.invsee.InvSeeHolder;

public class InvEnderSeeListeners implements Listener {

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		if (e.getPlayer() instanceof Player p && e.getInventory() != null && e.getInventory().getHolder() != null && (e.getInventory().getHolder() instanceof InvSeeHolder
				|| e.getInventory().getHolder() instanceof EnderSeeHolder))
			InvEnderSee.getSpectating().remove(p);
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		onInventoryInteract(e, e.getClickedInventory(), e.getSlot());
	}

	public void onInventoryInteract(InventoryInteractEvent e, Inventory inv, int slot) {
		if (inv == null)
			return;
		if (e.getWhoClicked() instanceof Player cible) {
			if (!InvEnderSee.getSpectating().isEmpty()) {
				InvEnderSee.getSpectating().forEach((all, type) -> {
					if (all.getOpenInventory() != null && all.getOpenInventory().getTopInventory() != null && all.getOpenInventory().getTopInventory().getHolder() != null) {
						Inventory topInv = all.getOpenInventory().getTopInventory();
						if (topInv.getHolder() instanceof InvSeeHolder holder) {
							if (holder.getCible().getUniqueId().equals(cible.getUniqueId())) {
								InvSee.update(holder.getPlayer(), cible, topInv);
							}
						} else if (topInv.getHolder() instanceof EnderSeeHolder holder) {
							if (holder.getCible().getUniqueId().equals(cible.getUniqueId())) {
								EnderSee.update(holder.getPlayer(), cible, topInv);
							}
						}
					}
					if (inv.getHolder() != null && (inv.getHolder() instanceof InvSeeHolder || inv.getHolder() instanceof EnderSeeHolder) && (!type.hasInteractPermission(cible) || type.getSlots().contains(slot)))
						e.setCancelled(true);
				});
			}
		}
	}
}
