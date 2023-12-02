package com.elikill58.sanction.spigot.inventories.hook;

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
import com.elikill58.sanction.spigot.utils.ItemStackBuilder;
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

		for (int i = 0; i < inv.getSize(); i++)
			inv.setItem(i, new ItemStack(Material.BROWN_STAINED_GLASS_PANE));
		
		inv.setItem(10, new ItemStackBuilder(Material.PLAYER_HEAD).tryOwner(cible).build());
		
		inv.setItem(12, Items.getItem(config.getConfigurationSection("main.items.sanctions")));
		inv.setItem(13, Items.getItem(config.getConfigurationSection("main.items.dupeip")));
		inv.setItem(14, Items.getItem(config.getConfigurationSection("main.items.history")));
		inv.setItem(15, Items.getItem(config.getConfigurationSection("main.items.teleport")));
		inv.setItem(16, Items.getItem(config.getConfigurationSection("main.items.find")));

		openInventorySync(p, inv);
	}

	@Override
	public void manageInventory(InventoryClickEvent e, Material m, Player p, SanctionMainHolder nh) {
		int slot = e.getSlot();
		if(slot == 10) 
			SpigotToBungee.sendPlayerCmdToBungee(p, "find");
		else if (slot == 12)
			InventoryManager.openInventory(p, "SANCTION_CATEGORY", nh.getCible());
		else if (slot == 13) {
			p.closeInventory();
			SpigotToBungee.sendPlayerCmdToBungee(p, "dupeip");
		} else if (slot == 14) {
			p.closeInventory();
			SpigotToBungee.sendPlayerCmdToBungee(p, "history");
		} else if (slot == 15) {
			p.teleport(nh.getCible());
		} else if (slot == 16) {
			p.closeInventory();
			p.performCommand(SanctionSpigot.getInstance().getConfig().getString("main.items.bell.command"));
		}
	}
}
