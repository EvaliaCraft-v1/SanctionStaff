package com.elikill58.sanction.spigot.inventories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

import com.elikill58.sanction.spigot.SanctionSpigot;

public abstract class AbstractInventory<T extends SanctionHolder> {

	private final Class<T> holderClass;
	
	public AbstractInventory(Class<T> holderClass) {
		this.holderClass = holderClass;
	}
	
	public boolean isInstance(SanctionHolder nh) {
		return nh.getClass().isAssignableFrom(holderClass);
	}
	
	public Inventory createInventory(T holder, int size, String name) {
		return Bukkit.createInventory(holder, size, name);
	}

	public void onPlayerJoin(PlayerJoinEvent e) {}
	public void onPlayerLeft(PlayerQuitEvent e) {}
	public void onInteract(PlayerInteractEvent e) {}
	public void onInteractEntity(PlayerInteractAtEntityEvent e) {}
	public abstract void openInventory(Player p, Object... args);
	public void closeInventory(Player p, InventoryCloseEvent e) {}
	public void manageInventory(InventoryClickEvent e, Material m, Player p, T nh) {}
	public void actualizeInventory(Player p, Inventory inv, T holder) {}
	
	protected void openInventorySync(Player p, Inventory inv) {
		if(Bukkit.isPrimaryThread())
			p.openInventory(inv);
		else
			Bukkit.getScheduler().runTask(SanctionSpigot.getInstance(), () -> p.openInventory(inv));
	}
}
