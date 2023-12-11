package com.elikill58.sanction.spigot.staffmode;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.elikill58.sanction.spigot.Msg;
import com.elikill58.sanction.spigot.SanctionSpigot;
import com.elikill58.sanction.spigot.inventories.InventoryManager;
import com.elikill58.sanction.spigot.utils.Items;

public enum StaffFeatures {

	INVENTORY(3, null, (p, cible) -> InventoryManager.openInventory(p, "")),
	SANCTION(4, null, (p, cible) -> InventoryManager.openInventory(p, "SANCTION_MAIN", cible)),
	TELEPORT_RANDOM(5, (p) -> {
		List<Player> all = Bukkit.getOnlinePlayers().stream().filter(ap -> !ap.hasPermission(SanctionSpigot.getInstance().getConfig().getString("permissions.no_tp", "sanction.use"))).collect(Collectors.toList());
		if(all.isEmpty())
			Msg.sendMsg(p, "teleport.no_one");
		else {
			Player cible = all.get(new Random().nextInt(all.size()));
			Msg.sendMsg(p, "teleport.go", "%name%", cible.getName());
			p.teleport(cible);
		}
	}, null);
	
	private final int slot;
	private final Consumer<Player> action;
	private final BiConsumer<Player, Player> actionWithCible;
	
	private StaffFeatures(int slot, Consumer<Player> action, BiConsumer<Player, Player> actionWithCible) {
		this.slot = slot;
		this.action = action;
		this.actionWithCible = actionWithCible;
	}
	
	public Consumer<Player> getAction() {
		return action;
	}
	
	public BiConsumer<Player, Player> getActionWithCible() {
		return actionWithCible;
	}
	
	public int getSlot() {
		return slot;
	}
	
	public ItemStack getItem() {
		return Items.getItem(SanctionSpigot.getInstance().getConfig().getConfigurationSection("staffmode.items." + name().toLowerCase()));
	}
}
