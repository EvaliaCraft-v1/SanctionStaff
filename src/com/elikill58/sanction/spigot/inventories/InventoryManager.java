package com.elikill58.sanction.spigot.inventories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import com.elikill58.sanction.spigot.SanctionSpigot;

public class InventoryManager implements Listener {

	private static final HashMap<String, AbstractInventory<?>> INV = new HashMap<>();

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		INV.values().forEach((inv) -> inv.onPlayerJoin(e));
	}
	
	@EventHandler
	public void onLeft(PlayerQuitEvent e) {
		INV.values().forEach((inv) -> inv.onPlayerLeft(e));
	}
	
	@SuppressWarnings({ "rawtypes" })
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		ItemStack item = e.getCurrentItem();
		if (item == null || e.getClickedInventory() == null || !(e.getWhoClicked() instanceof Player))
			return;
		Player p = (Player) e.getWhoClicked();
		InventoryHolder holder = e.getClickedInventory().getHolder();
		if(holder instanceof SanctionHolder) {
			SanctionHolder nh = (SanctionHolder) holder;
			for(AbstractInventory inv : INV.values()) {
				if(inv.isInstance(nh)) {
					e.setCancelled(true);
					inv.manageInventory(e, item.getType(), p, nh);
					return;
				}
			}
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		INV.values().forEach((inv) -> inv.onInteract(e));
	}

	@EventHandler
	public void onInteractEntity(PlayerInteractEntityEvent e) {
		PlayerInteractEvent newEvent = new PlayerInteractEvent(e.getPlayer(), Action.RIGHT_CLICK_BLOCK, e.getPlayer().getInventory().getItemInMainHand(), null, null);
		INV.values().forEach(i -> i.onInteract(newEvent));
	}

	@EventHandler
	public void onInteractEntity(PlayerInteractAtEntityEvent e) {
		INV.values().forEach(i -> i.onInteractEntity(e));
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		if(e.getInventory() == null || !(e.getPlayer() instanceof Player))
			return;
		InventoryHolder holder = e.getInventory().getHolder();
		if(!(holder instanceof SanctionHolder))
			return;
		for(AbstractInventory<?> inv : INV.values()) {
			if(inv.isInstance((SanctionHolder) holder)) {
				inv.closeInventory((Player) e.getPlayer(), e);
			}
		}
	}
	
	public static void unregisterInventory(String invName) {
		INV.remove(invName);
	}
	
	public static void registerInventory(String invName, AbstractInventory<?> inv) {
		AbstractInventory<?> old = INV.put(invName, inv);
		if(old != null)
			SanctionSpigot.getInstance().getLogger().severe("The inventory " + invName + " is in multiple times ! Old: " + old.getClass().getCanonicalName() + ", new: " + inv.getClass().getCanonicalName());
	}
	
	public static List<AbstractInventory<?>> getInventories() {
		return new ArrayList<>(INV.values());
	}
	
	public static AbstractInventory<?> get(String type) {
		return INV.get(type.toUpperCase());
	}
	
	public static void openInventory(Player p, String type, Object... args) {
		AbstractInventory<?> abs = INV.get(type);
		if(abs != null)
			abs.openInventory(p, args);
		else
			SanctionSpigot.getInstance().getLogger().warning("Failed to find inventory " + type + ".");
	}
}
