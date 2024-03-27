package com.elikill58.sanction.spigot.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.elikill58.sanction.spigot.Msg;
import com.elikill58.sanction.spigot.SanctionSpigot;
import com.elikill58.sanction.spigot.inventories.InventoryManager;

public class SanctionCommand implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if (!(sender instanceof Player p))
			return false;
		if (args.length == 1) {
			OfflinePlayer cible = Bukkit.getOfflinePlayer(args[0]);
			if (cible == null) {
				Msg.sendMsg(p, "not_found", "%arg%", args[0]);
			} else {
				Msg.sendMsg(p, "select", "%name%", cible.getName());
				String superPerm = SanctionSpigot.getInstance().getConfig().getString("permissions.admin");
				if (((cible instanceof Player oc && oc.hasPermission(superPerm)) || !(cible instanceof Player)) && !p.hasPermission(superPerm)) {
					Msg.sendMsg(p, "no_touch");
				} else {
					InventoryManager.openInventory(p, "SANCTION_MAIN", cible);
				}
			}
		} else
			Msg.sendMsg(p, "help");
		return false;
	}
}
