package com.elikill58.sanction.spigot;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.elikill58.sanction.spigot.commands.BanRsStaffCommand;
import com.elikill58.sanction.spigot.commands.EnderSeeCommand;
import com.elikill58.sanction.spigot.commands.InvSeeCommand;
import com.elikill58.sanction.spigot.commands.ReportCommand;
import com.elikill58.sanction.spigot.commands.SanctionCommand;
import com.elikill58.sanction.spigot.commands.SpecCommand;
import com.elikill58.sanction.spigot.commands.StaffCommand;
import com.elikill58.sanction.spigot.handler.Action;
import com.elikill58.sanction.spigot.handler.ActionType;
import com.elikill58.sanction.spigot.inventories.InventoryManager;
import com.elikill58.sanction.spigot.inventories.hook.ReportInventory;
import com.elikill58.sanction.spigot.inventories.hook.SanctionConfirmInventory;
import com.elikill58.sanction.spigot.inventories.hook.SanctionMainInventory;
import com.elikill58.sanction.spigot.inventories.hook.SanctionPlayerCategoryInventory;
import com.elikill58.sanction.spigot.inventories.hook.SanctionPlayerInventory;
import com.elikill58.sanction.spigot.listeners.BlockListener;
import com.elikill58.sanction.spigot.listeners.ChannelListeners;
import com.elikill58.sanction.spigot.listeners.StaffModeListener;
import com.elikill58.sanction.spigot.staffmode.InvEnderSeeListeners;
import com.elikill58.sanction.spigot.staffmode.endersee.EnderSee;
import com.elikill58.sanction.spigot.staffmode.endersee.EnderSeeHolder;
import com.elikill58.sanction.spigot.staffmode.invsee.InvSee;
import com.elikill58.sanction.spigot.staffmode.invsee.InvSeeHolder;
import com.elikill58.sanction.spigot.utils.SpigotToBungee;

public class SanctionSpigot extends JavaPlugin {

	private static SanctionSpigot instance;
	private static boolean hasDiscordSrv;

	public static SanctionSpigot getInstance() {
		return instance;
	}

	public static boolean hasDiscordSrv() {
		return hasDiscordSrv;
	}

	public static ArrayList<Action> actions = new ArrayList<>();

	@Override
	public void onEnable() {
		instance = this;
		saveDefaultConfig();
		loadActions();
		getCommand("report").setExecutor(new ReportCommand());
		getCommand("spec").setExecutor(new SpecCommand());
		getCommand("staff").setExecutor(new StaffCommand());
		getCommand("banrsstaff").setExecutor(new BanRsStaffCommand());
		getCommand("invsee").setExecutor(new InvSeeCommand());
		getCommand("endersee").setExecutor(new EnderSeeCommand());
		getCommand("sanction").setExecutor(new SanctionCommand());
		getCommand("sanctionrl").setExecutor(new CommandExecutor() {
			public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
				reloadConfig();
				loadActions();
				Msg.sendMsg(sender, "reloaded");
				return false;
			}
		});

		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new InventoryManager(), this);
		pm.registerEvents(new BlockListener(), this);
		pm.registerEvents(new StaffModeListener(), this);
		pm.registerEvents(new InvEnderSeeListeners(), this);

		hasDiscordSrv = pm.isPluginEnabled("DiscordSRV");

		InventoryManager.registerInventory("SANCTION_MAIN", new SanctionMainInventory());
		InventoryManager.registerInventory("SANCTION_CATEGORY", new SanctionPlayerCategoryInventory());
		InventoryManager.registerInventory("SANCTION_PLAYER", new SanctionPlayerInventory());
		InventoryManager.registerInventory("SANCTION_CONFIRM", new SanctionConfirmInventory());
		InventoryManager.registerInventory("REPORT", new ReportInventory());

		getServer().getScheduler().runTaskTimer(this, () -> {
			InvSee.getSpectating().forEach(all -> {
				if (all.getOpenInventory() == null || all.getOpenInventory().getTopInventory() == null)
					return;
				Inventory topInv = all.getOpenInventory().getTopInventory();
				if (topInv.getHolder() == null)
					return;
				if (topInv.getHolder() instanceof InvSeeHolder holder)
					InvSee.update(holder.getPlayer(), holder.getCible(), topInv);
				else if (topInv.getHolder() instanceof EnderSeeHolder holder)
					EnderSee.update(holder.getPlayer(), holder.getCible(), topInv);
			});
		}, 20, 20);

		getServer().getMessenger().registerOutgoingPluginChannel(this, "sanction:sanctioncmd");
		getServer().getMessenger().registerIncomingPluginChannel(this, ChannelListeners.CHANNEL_NAME_BRAND, new ChannelListeners());
	}

	private void loadActions() {
		actions.clear();
		Arrays.asList(ActionType.values()).forEach(a -> a.loadActions(this));
	}

	public static void runCommand(Player p, String command, boolean proxy, boolean asPlayer) {
		if (proxy) {
			getInstance().getLogger().info(p.getName() + " running '" + command + "' bungee command.");
			if (asPlayer)
				SpigotToBungee.sendPlayerCmdToBungee(p, command);
			else
				SpigotToBungee.sendCmdToBungee(p, command);
		} else {
			getInstance().getLogger().info(p.getName() + " running '" + command + "' spigot command.");
			Bukkit.dispatchCommand(asPlayer ? p : Bukkit.getConsoleSender(), command);
		}
	}
}
