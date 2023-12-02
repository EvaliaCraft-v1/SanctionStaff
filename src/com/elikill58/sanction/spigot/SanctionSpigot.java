package com.elikill58.sanction.spigot;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.elikill58.sanction.spigot.commands.SanctionCmd;
import com.elikill58.sanction.spigot.handler.Action;
import com.elikill58.sanction.spigot.handler.ActionType;
import com.elikill58.sanction.spigot.inventories.InventoryManager;
import com.elikill58.sanction.spigot.inventories.hook.SanctionMainInventory;
import com.elikill58.sanction.spigot.inventories.hook.SanctionPlayerCategoryInventory;
import com.elikill58.sanction.spigot.inventories.hook.SanctionPlayerInventory;

public class SanctionSpigot extends JavaPlugin {

	private static SanctionSpigot instance;

	public static SanctionSpigot getInstance() {
		return instance;
	}

	public static ArrayList<Action> actions = new ArrayList<>();

	@Override
	public void onEnable() {
		instance = this;
		saveDefaultConfig();
		loadActions();
		getCommand("sanction").setExecutor(new SanctionCmd());
		getCommand("sanctionrl").setExecutor(new CommandExecutor() {
			public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
				reloadConfig();
				loadActions();
				Msg.sendMsg(sender, "reloaded");
				return false;
			}
		});

		getServer().getPluginManager().registerEvents(new InventoryManager(), this);

		InventoryManager.registerInventory("SANCTION_MAIN", new SanctionMainInventory());
		InventoryManager.registerInventory("SANCTION_CATEGORY", new SanctionPlayerCategoryInventory());
		InventoryManager.registerInventory("SANCTION_PLAYER", new SanctionPlayerInventory());

		getServer().getMessenger().registerOutgoingPluginChannel(this, "sanction:sanctioncmd");
	}

	private void loadActions() {
		actions.clear();
		Arrays.asList(ActionType.values()).forEach(a -> a.loadActions(this));
	}
}
