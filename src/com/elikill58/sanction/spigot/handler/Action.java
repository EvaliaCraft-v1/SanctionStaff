package com.elikill58.sanction.spigot.handler;

import org.bukkit.inventory.ItemStack;

import com.elikill58.sanction.spigot.utils.ItemStackBuilder;

public class Action {
	
	private final String name, lore, command, perm;
	private final int slot;
	private final ItemStack item;
	private final boolean proxy;

	public Action(String name, String lore, String command, String perm, ItemStack item, int slot, boolean proxy) {
		this.name = name;
		this.lore = lore;
		this.command = command;
		this.perm = perm;
		this.item = item;
		this.slot = slot;
		this.proxy = proxy;
	}
	
	public String getName() {
		return name;
	}
	
	public String getLore() {
		return lore;
	}
	
	public String getCommand() {
		return command;
	}
	
	public String getPermission() {
		return perm;
	}

	public int getSlot() {
		return slot;
	}
	
	public boolean isProxy() {
		return proxy;
	}
	
	public ItemStack toItem() {
		return new ItemStackBuilder(item.clone()).displayName(name).lore(lore).build();
	}
}
