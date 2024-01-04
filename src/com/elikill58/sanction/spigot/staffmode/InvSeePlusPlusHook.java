package com.elikill58.sanction.spigot.staffmode;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.elikill58.sanction.spigot.SanctionSpigot;
import com.janboerman.invsee.spigot.api.CreationOptions;
import com.janboerman.invsee.spigot.api.InvseeAPI;
import com.janboerman.invsee.spigot.api.InvseePlusPlus;
import com.janboerman.invsee.spigot.api.template.Mirror;
import com.janboerman.invsee.spigot.api.template.PlayerInventorySlot;

public class InvSeePlusPlusHook {
	
	public static CreationOptions<PlayerInventorySlot> options;
	private static InvseeAPI invseeApi;

	public static void load() {
        InvseePlusPlus invseePlusPlus = (InvseePlusPlus) Bukkit.getPluginManager().getPlugin("InvSeePlusPlus");
        invseeApi = invseePlusPlus.getApi();
        options = invseeApi.mainInventoryCreationOptions().withMirror(new Mirror<PlayerInventorySlot>() {
			
			@Override
			public PlayerInventorySlot getSlot(int index) {
				for(PlayerInventorySlot slot : PlayerInventorySlot.values())
					if(getIndex(slot) == index)
						return slot;
				SanctionSpigot.getInstance().getLogger().info("Failed to find slot for index " + index);
				return null;
			}
			
			@Override
			public Integer getIndex(PlayerInventorySlot slot) {
				if(slot.name().contains("EMPTY")) {
					switch(slot.name()) {
					case "EMPTY_00":
						return 0;
					case "EMPTY_01":
						return 3;
					case "EMPTY_02":
						return 8;
					case "EMPTY_03":
						return 9;
					case "EMPTY_04":
						return 10;
					case "EMPTY_05":
						return 11;
					case "EMPTY_06":
						return 12;
					case "EMPTY_07":
						return 13;
					case "EMPTY_08":
						return 14;
					case "EMPTY_09":
						return 15;
					case "EMPTY_10":
						return 16;
					case "EMPTY_11":
						return 17;
					case "EMPTY_12":
						return 18;
					}
				} else if(slot.isArmour()) {
					if(slot.equals(PlayerInventorySlot.ARMOUR_HELMET))
						return 4;
					else if(slot.equals(PlayerInventorySlot.ARMOUR_CHESTPLATE))
						return 5;
					else if(slot.equals(PlayerInventorySlot.ARMOUR_LEGGINGS))
						return 6;
					else if(slot.equals(PlayerInventorySlot.ARMOUR_BOOTS))
						return 7;
				} else if(slot.isContainer()) {
					return slot.ordinal() + 18;
				} else if(slot.name().equals("CUSTOM_HEAD"))
					return 1;
				else if(slot.name().equals("CUSTOM_EXP"))
					return 2;
				return 54;
			}
		});
	}

	public static void open(Player p, String playername) {
		invseeApi.spectateInventory(p, playername, options).whenComplete((msi, e) -> {
			if(e == null) {
				//msi.getOpenInventory().setItem(1, new ItemStack(Material.PLAYER_HEAD));
				msi.getOpenInventory().getTopInventory().setItem(1, new ItemStack(Material.PLAYER_HEAD));
				p.openInventory(msi.getOpenInventory());
			} else {
				e.printStackTrace();
			}
		});
	}
}
