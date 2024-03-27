package com.elikill58.sanction.spigot.inventories.hook;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import com.elikill58.sanction.spigot.Msg;
import com.elikill58.sanction.spigot.SanctionSpigot;
import com.elikill58.sanction.spigot.handler.ActionType;
import com.elikill58.sanction.spigot.inventories.AbstractInventory;
import com.elikill58.sanction.spigot.inventories.InventoryManager;
import com.elikill58.sanction.spigot.inventories.holder.SanctionPlayerCategoryHolder;
import com.elikill58.sanction.spigot.utils.Items;

public class SanctionPlayerCategoryInventory extends AbstractInventory<SanctionPlayerCategoryHolder> {

	public SanctionPlayerCategoryInventory() {
		super(SanctionPlayerCategoryHolder.class);
	}

	@Override
	public void openInventory(Player p, Object... args) {
		OfflinePlayer cible = (OfflinePlayer) args[0];
		FileConfiguration config = SanctionSpigot.getInstance().getConfig();
		Inventory inv = createInventory(new SanctionPlayerCategoryHolder(cible), 27, Msg.getMsg("category.inv_name", "%name%", cible.getName()));
		for (int i = 0; i < inv.getSize(); i++)
			inv.setItem(i, Items.EMPTY);
		for (ActionType type : ActionType.values()) {
			if(type.getSlot() < 0)
				continue;
			String perm = config.getString(type.name().toLowerCase() + ".permission");
			if(perm == null || p.hasPermission(perm))
				inv.setItem(type.getSlot(), Items.getItem(config.getConfigurationSection(type.name().toLowerCase() + ".items.main")));
		}
		inv.setItem(inv.getSize() - 1, Items.getBackItem());
		openInventorySync(p, inv);
	}

	@Override
	public void manageInventory(InventoryClickEvent e, Material m, Player p, SanctionPlayerCategoryHolder nh) {
		if (e.getCurrentItem().isSimilar(Items.getBackItem())) {
			InventoryManager.openInventory(p, "SANCTION_MAIN", nh.getCible());
			return;
		}
		if(e.getCurrentItem().isSimilar(Items.EMPTY))
			return;
		for (ActionType type : ActionType.values()) {
			if(type.getSlot() < 0)
				return;
			if (type.getSlot() == e.getSlot())
				InventoryManager.openInventory(p, "SANCTION_PLAYER", nh.getCible(), type);
		}
	}
}
