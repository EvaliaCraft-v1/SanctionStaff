package com.elikill58.sanction.spigot.inventories.hook;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.elikill58.sanction.spigot.Msg;
import com.elikill58.sanction.spigot.SanctionSpigot;
import com.elikill58.sanction.spigot.inventories.AbstractInventory;
import com.elikill58.sanction.spigot.inventories.InventoryManager;
import com.elikill58.sanction.spigot.inventories.holder.SanctionMainHolder;
import com.elikill58.sanction.spigot.utils.Items;
import com.elikill58.sanction.spigot.utils.SpigotToBungee;

public class SanctionMainInventory extends AbstractInventory<SanctionMainHolder> {

	public SanctionMainInventory() {
		super(SanctionMainHolder.class);
	}

	@Override
	public void openInventory(Player p, Object... args) {
		Player cible = (Player) args[0];
		Inventory inv = createInventory(new SanctionMainHolder(cible), 27, Msg.getMsg("main.inv_name", "%name%", cible.getName()));
		FileConfiguration config = SanctionSpigot.getInstance().getConfig();

		for (int slot : Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26))
			inv.setItem(slot, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
		inv.setItem(10, new ItemStack(Material.RED_STAINED_GLASS_PANE));
		inv.setItem(11, Items.getItem(config.getConfigurationSection("main.items.sanctions")));
		inv.setItem(12, Items.getItem(config.getConfigurationSection("main.items.dupeip")));
		inv.setItem(13, Items.getItem(config.getConfigurationSection("main.items.history")));
		inv.setItem(14, Items.getItem(config.getConfigurationSection("main.items.teleport")));
		inv.setItem(15, Items.getItem(config.getConfigurationSection("main.items.find")));
		inv.setItem(16, new ItemStack(Material.RED_STAINED_GLASS_PANE));

		openInventorySync(p, inv);
	}

	@Override
	public void manageInventory(InventoryClickEvent e, Material m, Player p, SanctionMainHolder nh) {
		int slot = e.getSlot();
		if (slot == 11)
			InventoryManager.openInventory(p, "SANCTION_CATEGORY", nh.getCible());
		else if (slot == 12) {
			p.closeInventory();
			SpigotToBungee.sendPlayerCmdToBungee(p, "dupeip");
		} else if (slot == 13) {
			p.closeInventory();
			SpigotToBungee.sendPlayerCmdToBungee(p, "history");
		} else if (slot == 14) {
			p.teleport(nh.getCible());
		} else if (slot == 15) {
			p.closeInventory();
			SpigotToBungee.sendPlayerCmdToBungee(p, "find");
		}
	}
}
