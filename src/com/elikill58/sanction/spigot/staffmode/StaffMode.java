package com.elikill58.sanction.spigot.staffmode;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import com.elikill58.sanction.spigot.Msg;
import com.elikill58.sanction.spigot.utils.Items;

public class StaffMode {

	private static final List<Player> STAFFS = new ArrayList<>();

	public static List<Player> getStaffs() {
		return STAFFS;
	}

	public static boolean isStaffMode(Player p) {
		return STAFFS.contains(p);
	}

	public static void startStaffMode(Player p) {
		Msg.sendMsg(p, "staffmode.enabled");
		STAFFS.add(p);
		p.performCommand("vanish on");
		PlayerInventory inv = p.getInventory();
		StaffModeInventory.save(p, inv);
		inv.setArmorContents(null);
		inv.clear();
		for(int i = 0; i < 9; i++)
			inv.setItem(i, Items.EMPTY);
		for(StaffFeatures sf : StaffFeatures.values()) {
			inv.setItem(sf.getSlot(), sf.getItem());
		}
		p.setAllowFlight(true);
	}

	public static void stopStaffMode(Player p) {
		Msg.sendMsg(p, "staffmode.disabled");
		STAFFS.remove(p);
		p.performCommand("vanish off");
		StaffModeInventory.restore(p, p.getInventory());
	}
}
