package com.elikill58.sanction.spigot.inventories.hook;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.elikill58.sanction.spigot.Msg;
import com.elikill58.sanction.spigot.SanctionSpigot;
import com.elikill58.sanction.spigot.handler.Action;
import com.elikill58.sanction.spigot.inventories.AbstractInventory;
import com.elikill58.sanction.spigot.inventories.InventoryManager;
import com.elikill58.sanction.spigot.inventories.holder.SanctionConfirmHolder;
import com.elikill58.sanction.spigot.utils.Items;
import com.elikill58.sanction.universal.UniversalUtils;

public class SanctionConfirmInventory extends AbstractInventory<SanctionConfirmHolder> {

	public SanctionConfirmInventory() {
		super(SanctionConfirmHolder.class);
	}

	@Override
	public void openInventory(Player p, Object... args) {
		Player cible = (Player) args[0];
		Action action = (Action) args[1];

		Inventory inv = createInventory(new SanctionConfirmHolder(cible, action), 9, Msg.getMsg("confirm.inv_name", "%name%", cible.getName()));

		for (int i = 0; i < inv.getSize(); i++)
			inv.setItem(i, new ItemStack(Material.BROWN_STAINED_GLASS_PANE));

		FileConfiguration config = SanctionSpigot.getInstance().getConfig();
		inv.setItem(3, Items.getItem(config.getConfigurationSection("confirm.items.confirm")));
		inv.setItem(5, Items.getItem(config.getConfigurationSection("confirm.items.cancel")));

		inv.setItem(inv.getSize() - 1, action.toItem());
		openInventorySync(p, inv);
	}

	@Override
	public void manageInventory(InventoryClickEvent e, Material m, Player p, SanctionConfirmHolder nh) {
		if (e.getSlot() == 5) {
			InventoryManager.openInventory(p, "SANCTION_PLAYER", nh.getCible(), nh.getAction().getType());
		} else if (e.getSlot() == 3) {
			Player cible = nh.getCible();
			Action ac = nh.getAction();
			String cmd = UniversalUtils.replacePlaceholder(ac.getCommand(), "%player%", cible.getName(), "%executor%", p.getName(), "%executor_uuid%", p.getUniqueId());
			SanctionSpigot.runCommand(p, cmd, ac.isProxy(), false);
			Msg.sendMsg(p, "applied", "%name%", cible.getName());
			p.closeInventory();
		}
	}
}