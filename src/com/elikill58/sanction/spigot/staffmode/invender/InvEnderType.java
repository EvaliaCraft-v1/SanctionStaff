package com.elikill58.sanction.spigot.staffmode.invender;

import java.util.Arrays;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.elikill58.sanction.spigot.SanctionSpigot;
import com.elikill58.sanction.spigot.staffmode.invender.endersee.EnderSee;
import com.elikill58.sanction.spigot.staffmode.invender.invsee.InvSee;

public enum InvEnderType {

	INV("invsee", 0, 1, 2, 3, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17),
	ENDER("endersee", 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17);

	private final String key;
	private final List<Integer> slots;

	private InvEnderType(String key, int... i) {
		this.key = key;
		this.slots = Arrays.stream(i).boxed().toList();
	}
	
	public String getKey() {
		return key;
	}

	public List<Integer> getSlots() {
		return slots;
	}
	
	public boolean hasInteractPermission(Player p) {
		return p.hasPermission(SanctionSpigot.getInstance().getConfig().getString("permissions." + key + "_interact"));
	}
	
	public void setItemInInventory(Player p, OfflinePlayer cible, int slot, ItemStack item) {
		if(this == INV)
			InvSee.setItemInInventory(p, cible, slot, item);
		else if(this == ENDER)
			EnderSee.setItemInInventory(p, cible, slot, item);
	}
	
	public void update(Player p, OfflinePlayer cible, Inventory inv) {
		if(this == INV)
			InvSee.update(p, cible, inv);
		else if(this == ENDER)
			EnderSee.update(p, cible, inv);
	}
}
