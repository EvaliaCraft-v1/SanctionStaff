package com.elikill58.sanction.spigot.staffmode.invender;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
			if (inv.getHolder() != null && inv.getHolder() instanceof InvEnderHolder holder) {
				InvEnderType type = holder.getType();
				OfflinePlayer cible = holder.getCible();
				if (!type.hasInteractPermission(p) || type.getSlots().contains(slot))
					e.setCancelled(true);
				else { // change item into other inv
					if(type.equals(InvEnderType.INV) && e.getCurrentItem() != null) {
						Material mat = e.getCurrentItem().getType();
						if((slot == 4 && !mat.name().contains("_HELMET"))
								|| (slot == 5 && !mat.name().contains("_CHESTPLATE"))
								|| (slot == 6 && !mat.name().contains("_LEGGINGS"))
								|| (slot == 7 && !mat.name().contains("_BOOTS")))
							e.setCancelled(true);
					}
					Bukkit.getScheduler().runTaskLater(SanctionSpigot.getInstance(), () -> {
						type.setItemInInventory(p, cible, slot, inv.getItem(slot));
						type.update(p, cible, inv);
					}, 0);
				}
			}
		}
	}
}
