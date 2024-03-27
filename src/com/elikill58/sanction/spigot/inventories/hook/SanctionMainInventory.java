package com.elikill58.sanction.spigot.inventories.hook;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import com.elikill58.sanction.spigot.Msg;
import com.elikill58.sanction.spigot.SanctionSpigot;
import com.elikill58.sanction.spigot.inventories.AbstractInventory;
import com.elikill58.sanction.spigot.inventories.InventoryManager;
import com.elikill58.sanction.spigot.inventories.holder.SanctionMainHolder;
import com.elikill58.sanction.spigot.utils.ItemStackBuilder;
import com.elikill58.sanction.spigot.utils.Items;

public class SanctionMainInventory extends AbstractInventory<SanctionMainHolder> {

	public SanctionMainInventory() {
		super(SanctionMainHolder.class);
	}

	@Override
	public void openInventory(Player p, Object... args) {
		OfflinePlayer cible = (OfflinePlayer) args[0];
		Inventory inv = createInventory(new SanctionMainHolder(cible), 27, Msg.getMsg("main.inv_name", "%name%", cible.getName()));
		FileConfiguration config = SanctionSpigot.getInstance().getConfig();

		for (int i = 0; i < inv.getSize(); i++)
			inv.setItem(i, Items.EMPTY);

		if(cible instanceof Player oc) {
			inv.setItem(10, new ItemStackBuilder(Items.getItem(config.getConfigurationSection("main.items.head"), "%name%", cible.getName(), "%ping%", oc.getPing(), "%loc_x%",
					oc.getLocation().getBlockX(), "%loc_y%", oc.getLocation().getBlockY(), "%loc_z%", oc.getLocation().getBlockZ())).tryOwner(cible).build());
		} else {
			inv.setItem(10, new ItemStackBuilder(Items.getItem(config.getConfigurationSection("main.items.head_offline"), "%name%", cible.getName())).tryOwner(cible).build());
		}
		
		inv.setItem(12, Items.getItem(config.getConfigurationSection("main.items.sanctions"), "%name%", cible.getName()));
		inv.setItem(13, Items.getItem(config.getConfigurationSection("main.items.dupeip"), "%name%", cible.getName()));
		inv.setItem(14, Items.getItem(config.getConfigurationSection("main.items.history"), "%name%", cible.getName()));
		inv.setItem(15, Items.getItem(config.getConfigurationSection("main.items.teleport"), "%name%", cible.getName()));
		inv.setItem(16, Items.getItem(config.getConfigurationSection("main.items.bell"), "%name%", cible.getName()));

		openInventorySync(p, inv);
	}

	@Override
	public void manageInventory(InventoryClickEvent e, Material m, Player p, SanctionMainHolder nh) {
		int slot = e.getSlot();
		if (slot == 10) {
			p.closeInventory();
			runCmd(p, nh.getCible(), "main.items.head");
		} else if (slot == 12)
			InventoryManager.openInventory(p, "SANCTION_CATEGORY", nh.getCible());
		else if (slot == 13) {
			p.closeInventory();
			runCmd(p, nh.getCible(), "main.items.dupeip");
		} else if (slot == 14) {
			p.closeInventory();
			runCmd(p, nh.getCible(), "main.items.history");
		} else if (slot == 15) {
			p.closeInventory();
			runCmd(p, nh.getCible(), "main.items.teleport");
		} else if (slot == 16) {
			p.closeInventory();
			runCmd(p, nh.getCible(), "main.items.bell");
		}
	}

	private void runCmd(Player p, OfflinePlayer cible, String dir) {
		FileConfiguration config = SanctionSpigot.getInstance().getConfig();
		String cmd = config.getString(dir + ".command");
		if (cmd == null)
			return;
		boolean proxy = config.getBoolean(dir + ".proxy", false);
		boolean asPlayer = config.getBoolean(dir + ".as_player", true);
		SanctionSpigot.runCommand(p, cmd.replace("%name%", cible.getName()), proxy, asPlayer);
	}
}
