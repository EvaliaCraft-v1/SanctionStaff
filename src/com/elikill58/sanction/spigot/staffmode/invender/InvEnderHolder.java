package com.elikill58.sanction.spigot.staffmode.invender;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public abstract class InvEnderHolder implements InventoryHolder {
	
	private final Player p;
	private final OfflinePlayer cible;
	private final InvEnderType type;
	
	public InvEnderHolder(Player p, OfflinePlayer cible, InvEnderType type) {
		this.p = p;
		this.cible = cible;
		this.type = type;
	}
	
	public OfflinePlayer getCible() {
		return cible;
	}
	
	public Player getPlayer() {
		return p;
	}
	
	public InvEnderType getType() {
		return type;
	}
	
	@Override
	public Inventory getInventory() {
		return null;
	}
}
