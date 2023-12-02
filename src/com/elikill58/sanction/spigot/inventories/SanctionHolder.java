package com.elikill58.sanction.spigot.inventories;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public abstract class SanctionHolder implements InventoryHolder {
	
	@Override
	public Inventory getInventory() {
		return null;
	}
}
