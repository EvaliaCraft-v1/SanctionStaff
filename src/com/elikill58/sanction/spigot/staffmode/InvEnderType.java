package com.elikill58.sanction.spigot.staffmode;

import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;

import com.elikill58.sanction.spigot.SanctionSpigot;

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
}
