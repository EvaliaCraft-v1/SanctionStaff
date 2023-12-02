package com.elikill58.sanction.spigot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

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

	public static void logs(String player, String cmd) {
		Calendar cal = Calendar.getInstance();
		String date = cal.get(5) + "-" + (cal.get(2) + 1) + "-" + cal.get(1);
		String heure = cal.get(11) + ":" + cal.get(12) + ":" + cal.get(13);
		String text = date + " " + heure + " " + player + " => " + cmd;
		File logs = new File("./plugins/" + getInstance().getName() + "/logs/log_" + date + ".txt");
		if (!logs.exists()) {
			try {
				logs.mkdirs();
				logs.delete();
				logs.createNewFile();
				FileWriter fw = new FileWriter(logs);
				fw.write(text);
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				FileInputStream inf = new FileInputStream(logs);
				BufferedReader br = new BufferedReader(new InputStreamReader(inf));
				String actext = "";
				String act = "";
				while ((act = br.readLine()) != null) {
					actext = actext + "\r\n" + act;
				}
				FileWriter fw = new FileWriter(logs);
				fw.write(text + actext);
				fw.close();
				br.close();
				inf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void loadActions() {
		actions.clear();
		Arrays.asList(ActionType.values()).forEach(a -> a.loadActions(this));
	}
}
