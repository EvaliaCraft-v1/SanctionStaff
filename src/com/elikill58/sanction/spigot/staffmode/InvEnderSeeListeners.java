package com.elikill58.sanction.spigot.staffmode;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.Inventory;

import com.elikill58.sanction.spigot.SanctionSpigot;
import com.elikill58.sanction.spigot.staffmode.endersee.EnderSee;
import com.elikill58.sanction.spigot.staffmode.endersee.EnderSeeHolder;
import com.elikill58.sanction.spigot.staffmode.invsee.InvSee;
import com.elikill58.sanction.spigot.staffmode.invsee.InvSeeHolder;

public class InvEnderSeeListeners implements Listener {

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		if (e.getPlayer() instanceof Player p && e.getInventory() != null && e.getInventory().getHolder() != null) {
			if(e.getInventory().getHolder() instanceof InvSeeHolder)
				InvSee.getSpectating().remove(p);
			else if(e.getInventory().getHolder() instanceof EnderSeeHolder)
				EnderSee.getSpectating().remove(p);
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		onInventoryInteract(e, e.getClickedInventory());
	}

	@EventHandler
	public void onInventoryDrag(InventoryDragEvent e) {
		onInventoryInteract(e, e.getInventory());
	}

	public void onInventoryInteract(InventoryInteractEvent e, Inventory inv) {
		if (inv == null)
			return;
		if (e.getWhoClicked() instanceof Player cible) {
			if (!InvSee.getSpectating().isEmpty()) {
				Bukkit.getScheduler().runTaskLater(SanctionSpigot.getInstance(), () -> {
					InvSee.getSpectating().stream()
							.filter(all -> all.getOpenInventory() != null && all.getOpenInventory().getTopInventory() != null && all.getOpenInventory().getTopInventory().getHolder() != null)
							.forEach(all -> {
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
							});
				}, 1);
			}
			if (inv.getHolder() != null && (inv.getHolder() instanceof InvSeeHolder || inv.getHolder() instanceof EnderSeeHolder) && !cible.isOp())
				e.setCancelled(true);
		}
	}
}
