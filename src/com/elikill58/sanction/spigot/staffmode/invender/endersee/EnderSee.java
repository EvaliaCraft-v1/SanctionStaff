package com.elikill58.sanction.spigot.staffmode.invender.endersee;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.elikill58.sanction.spigot.Msg;
import com.elikill58.sanction.spigot.staffmode.invender.InvEnderSee;
import com.elikill58.sanction.spigot.staffmode.invender.InvEnderType;
import com.elikill58.sanction.spigot.utils.ItemStackBuilder;
import com.elikill58.sanction.spigot.utils.Items;

public class EnderSee {

	public static void open(Player p, OfflinePlayer cible) {
		InvEnderSee.getSpectating().put(p, InvEnderType.ENDER);
		Inventory inv = Bukkit.createInventory(new EnderSeeHolder(p, cible), 45, Msg.getMsg("invsee.inv_name_enderchest", "%name%", cible.getName()));
		update(p, cible, inv);
		p.openInventory(inv);
	}
	
	public static void update(Player p, OfflinePlayer cible, Inventory inv) {
		boolean real = cible instanceof Player;
		Player oc = real ? (Player) cible : InvEnderSee.load(cible, p.getWorld());
		Inventory source = oc.getEnderChest();
		for(int i = 0; i < 18; i++)
			inv.setItem(i, Items.EMPTY);
		
		inv.setItem(3, InvEnderSee.createHead(cible, oc, real));
		inv.setItem(5, new ItemStackBuilder(Material.EXPERIENCE_BOTTLE, oc.getExpToLevel() == 0 ? 1 : (oc.getExpToLevel() > 64 ? 64 : oc.getExpToLevel())).displayName(Msg.getMsg("invsee.exp_level", "%xp%", oc.getExpToLevel())).build());
		
		int i = 18;
		for(ItemStack item : source.getContents()) {
			if(i == 45)
				break;
			inv.setItem(i++, item);
		}
	}
	
	public static void setItemInInventory(Player p, OfflinePlayer cible, int slot, ItemStack item) {
		boolean real = cible instanceof Player;
		Player cp = real ? (Player) cible : InvEnderSee.load(cible, p.getWorld());
		Inventory  inv = cp.getInventory();
		inv.setItem(slot - 18, item);
		cp.saveData();
	}
}
