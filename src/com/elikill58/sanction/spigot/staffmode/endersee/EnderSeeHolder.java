package com.elikill58.sanction.spigot.staffmode.endersee;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class EnderSeeHolder implements InventoryHolder {
	
	private final Player p;
	private final OfflinePlayer cible;
	
	public EnderSeeHolder(Player p, OfflinePlayer cible) {
		this.p = p;
		this.cible = cible;
	}
	
	public OfflinePlayer getCible() {
		return cible;
	}
	
	public Player getPlayer() {
		return p;
	}
	
	@Override
	public Inventory getInventory() {
		return null;
	}
}
