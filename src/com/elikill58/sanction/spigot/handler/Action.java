package com.elikill58.sanction.spigot.handler;

import org.bukkit.inventory.ItemStack;

import com.elikill58.sanction.spigot.utils.ItemStackBuilder;

public class Action {
	
	private String name, lore, command, perm;
	private int slot;
	private ItemStack item;

	public Action(String name, String lore, String command, String perm, ItemStack item, int slot) {
		this.name = name;
		this.lore = lore;
		this.command = command;
		this.perm = perm;
		this.item = item;
		this.slot = slot;
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
	
	public ItemStack toItem() {
		return new ItemStackBuilder(item).displayName(name).lore(lore).build();
	}
}
