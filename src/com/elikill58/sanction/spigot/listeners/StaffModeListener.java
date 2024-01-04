package com.elikill58.sanction.spigot.listeners;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import com.elikill58.sanction.spigot.staffmode.InvSeePlusPlusHook;
import com.elikill58.sanction.spigot.staffmode.StaffFeatures;
import com.elikill58.sanction.spigot.staffmode.StaffMode;
import com.elikill58.sanction.spigot.staffmode.StaffModeInventory;

public class StaffModeListener implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if(StaffModeInventory.couldBeStaffMode(e.getPlayer().getUniqueId())) {
			StaffMode.getStaffs().add(e.getPlayer());
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPickupItem(EntityPickupItemEvent e) {
		if(e.getEntity() instanceof Player p && StaffMode.isStaffMode(p)) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onInteractAtEntity(PlayerInteractAtEntityEvent e) {
		Player p = e.getPlayer();
		if(!StaffMode.isStaffMode(p))
			return;
		e.setCancelled(true);
		if(!(e.getRightClicked() instanceof Player cible))
			return;
		for(StaffFeatures sf : StaffFeatures.values()) {
			if(p.getInventory().getHeldItemSlot() == sf.getSlot() && sf.getActionWithCible() != null) {
				sf.getActionWithCible().accept(p, cible);
			}
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(!StaffMode.isStaffMode(p))
			return;
		e.setCancelled(true);
		for(StaffFeatures sf : StaffFeatures.values()) {
			if(p.getInventory().getHeldItemSlot() == sf.getSlot() && sf.getAction() != null) {
				sf.getAction().accept(p);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e) {
		if(!e.getMessage().startsWith("/invsee"))
			return;
		String[] args = e.getMessage().split(" ");
		OfflinePlayer cible = Bukkit.getOfflinePlayer(args[args.length - 1]);
		if(cible != null) {
			e.setCancelled(true);
			InvSeePlusPlusHook.open(e.getPlayer(), cible.getName());
		} // else just let the basic plugin handle it
	}
}
