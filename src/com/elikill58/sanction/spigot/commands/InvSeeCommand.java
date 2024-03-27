package com.elikill58.sanction.spigot.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.elikill58.sanction.spigot.Msg;
import com.elikill58.sanction.spigot.staffmode.invsee.InvSee;

public class InvSeeCommand implements CommandExecutor {

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
				InvSee.open(p, cible);
			}
		} else
			Msg.sendMsg(p, "invsee.help");
		return false;
	}
}
