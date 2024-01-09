package com.elikill58.sanction.spigot.handler;

import org.bukkit.inventory.ItemStack;

import com.elikill58.sanction.spigot.utils.ItemStackBuilder;

public class Action {
	
	private final String name, lore, command, perm, reason;
	private final int slot;
	private final ActionType type;
	private final boolean proxy;

	public Action(String name, String lore, String command, String perm, String reason, int slot, boolean proxy, ActionType type) {
		this.name = name;
		this.lore = lore;
		this.command = command;
		this.perm = perm;
		this.reason = reason;
		this.type = type;
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
	
	public String getReason() {
		return reason;
	}
	
	public ActionType getType() {
		return type;
	}

	public int getSlot() {
		return slot;
	}
	
	public boolean isProxy() {
		return proxy;
	}
	
	public ItemStack toItem() {
		return new ItemStackBuilder(type.getItem()).displayName(name).lore(lore).build();
	}
}
