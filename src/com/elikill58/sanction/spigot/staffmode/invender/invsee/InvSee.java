package com.elikill58.sanction.spigot.staffmode.invender.invsee;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.elikill58.sanction.spigot.Msg;
import com.elikill58.sanction.spigot.staffmode.invender.InvEnderSee;
import com.elikill58.sanction.spigot.staffmode.invender.InvEnderType;
import com.elikill58.sanction.spigot.utils.ItemStackBuilder;
import com.elikill58.sanction.spigot.utils.Items;

public class InvSee {
	
	public static void open(Player p, OfflinePlayer cible) {
		InvEnderSee.getSpectating().put(p, InvEnderType.INV);
		Inventory inv = Bukkit.createInventory(new InvSeeHolder(p, cible), 54, Msg.getMsg("invsee.inv_name", "%name%", cible.getName()));
		update(p, cible, inv);
		p.openInventory(inv);
	}
	
	public static void update(Player p, OfflinePlayer cible, Inventory inv) {
		boolean real = cible instanceof Player;
		Player oc = real ? (Player) cible : InvEnderSee.load(cible, p.getWorld());
		PlayerInventory source = oc.getInventory();
		for(int i = 0; i < 18; i++)
			inv.setItem(i, Items.EMPTY);

		inv.setItem(1, InvEnderSee.createHead(cible, oc, real));
		inv.setItem(2, new ItemStackBuilder(Material.EXPERIENCE_BOTTLE, oc.getExpToLevel() == 0 ? 1 : (oc.getExpToLevel() > 64 ? 64 : oc.getExpToLevel())).displayName(Msg.getMsg("invsee.exp_level", "%xp%", oc.getExpToLevel())).build());
		
		inv.setItem(4, source.getHelmet());
		inv.setItem(5, source.getChestplate());
		inv.setItem(6, source.getLeggings());
		inv.setItem(7, source.getBoots());
		
		ItemStack[] items = source.getContents();
		for(int i = 0; i < 9; i++) {
			inv.setItem(45 + i, items[i]);
		}
		for(int i = 0; i < 27; i++) {
			inv.setItem(18 + i, items[i + 9]);
		}
	}
	
	public static void setItemInInventory(Player p, OfflinePlayer cible, int slot, ItemStack item) {
		Player cp = cible instanceof Player ? (Player) cible : InvEnderSee.load(cible, p.getWorld());
		if(slot == 4)
			cp.getInventory().setHelmet(item);
		else if(slot == 5)
			cp.getInventory().setChestplate(item);
		else if(slot == 6)
			cp.getInventory().setLeggings(item);
		else if(slot == 7)
			cp.getInventory().setBoots(item);
		else if(slot >= 18 && slot < 45)
			cp.getInventory().setItem(slot - 9, item);
		else if(slot >= 45)
			cp.getInventory().setItem(slot - 45, item);
		cp.saveData();
	}
}
