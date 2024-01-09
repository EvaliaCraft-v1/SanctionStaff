package com.elikill58.sanction.spigot.inventories.hook;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import com.elikill58.sanction.spigot.Msg;
import com.elikill58.sanction.spigot.SanctionSpigot;
import com.elikill58.sanction.spigot.handler.Action;
import com.elikill58.sanction.spigot.handler.ActionType;
import com.elikill58.sanction.spigot.inventories.AbstractInventory;
import com.elikill58.sanction.spigot.inventories.InventoryManager;
import com.elikill58.sanction.spigot.inventories.holder.ReportHolder;
import com.elikill58.sanction.spigot.utils.ItemStackBuilder;
import com.elikill58.sanction.spigot.utils.Items;

public class ReportInventory extends AbstractInventory<ReportHolder> {

	public ReportInventory() {
		super(ReportHolder.class);
	}

	@Override
	public void openInventory(Player p, Object... args) {
		Player cible = (Player) args[0];
		Inventory inv = createInventory(new ReportHolder(cible), 27, Msg.getMsg("main.inv_name", "%name%", cible.getName()));
		FileConfiguration config = SanctionSpigot.getInstance().getConfig();

		for (int i = 0; i < inv.getSize(); i++)
			inv.setItem(i, Items.EMPTY);
		
		inv.setItem(10, new ItemStackBuilder(Items.getItem(config.getConfigurationSection("main.items.head"), "%name%", cible.getName())).tryOwner(cible).build());
		
		ActionType type = ActionType.REPORT;

		for(int i = 0; i < inv.getSize(); i++)
			inv.setItem(i, Items.EMPTY);
		
		for(Action a : type.getActions()) {
			if(a.getPermission() == null || p.hasPermission(a.getPermission()))
				inv.setItem(a.getSlot(), a.toItem());
		}
		
		openInventorySync(p, inv);
	}

	@Override
	public void manageInventory(InventoryClickEvent e, Material m, Player p, ReportHolder nh) {
		for(Action a : ActionType.REPORT.getActions()) {
			if(a.getSlot() == e.getSlot()) {
				InventoryManager.openInventory(p, "SANCTION_CONFIRM", nh.getCible(), a);
				return;
			}
		}
	}
}
