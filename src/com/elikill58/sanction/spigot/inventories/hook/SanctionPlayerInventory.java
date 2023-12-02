package com.elikill58.sanction.spigot.inventories.hook;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.elikill58.sanction.spigot.Msg;
import com.elikill58.sanction.spigot.SanctionSpigot;
import com.elikill58.sanction.spigot.handler.Action;
import com.elikill58.sanction.spigot.handler.ActionType;
import com.elikill58.sanction.spigot.inventories.AbstractInventory;
import com.elikill58.sanction.spigot.inventories.InventoryManager;
import com.elikill58.sanction.spigot.inventories.holder.SanctionPlayerHolder;
import com.elikill58.sanction.spigot.utils.Items;
import com.elikill58.sanction.spigot.utils.SpigotToBungee;

public class SanctionPlayerInventory extends AbstractInventory<SanctionPlayerHolder> {

	public SanctionPlayerInventory() {
		super(SanctionPlayerHolder.class);
	}

	@Override
	public void openInventory(Player p, Object... args) {
		Player cible = (Player) args[0];
		ActionType type = (ActionType) args[1];
		
		Inventory inv = Bukkit.createInventory(new SanctionPlayerHolder(cible, type), 27, "inv");
		
		for(int i = 0; i < inv.getSize(); i++)
			inv.setItem(i, new ItemStack(Material.BROWN_STAINED_GLASS_PANE));
		
		for(Action a : type.getActions()) {
			if(a.getPermission() == null || p.hasPermission(a.getPermission()))
				inv.setItem(a.getSlot(), a.toItem());
		}
		
		inv.setItem(inv.getSize() - 1, Items.getBackItem());
		openInventorySync(p, inv);
	}

	@Override
	public void manageInventory(InventoryClickEvent e, Material m, Player p, SanctionPlayerHolder nh) {
		if(e.getCurrentItem().isSimilar(Items.getBackItem())) {
			InventoryManager.openInventory(p, "SANCTION_CATEGORY", nh.getCible());
			return;
		}
		for(Action a : nh.getType().getActions()) {
			if(a.getSlot() == e.getSlot()) {
				runCommand(p, nh.getCible(), a);
				return;
			}
		}
	}

	public static void runCommand(Player player, Player cible, Action ac) {
		String cmd = ac.getCommand().replace("%player%", cible.getName()).replace("%executor%", player.getName()).replace("%executor_uuid%", player.getUniqueId().toString());
		SanctionSpigot.getInstance().getLogger().info(player.getName() + " sanction " + cible.getName() + ": running '" + cmd + "' bungee command.");
		SpigotToBungee.sendCmdToBungee(player, cmd);
		SanctionSpigot.logs(player.getName(), ac.getCommand().replace("%player%", cible.getName()));
		Msg.sendMsg(player, "applied", "%name%", cible.getName());
		player.closeInventory();
	}
}
