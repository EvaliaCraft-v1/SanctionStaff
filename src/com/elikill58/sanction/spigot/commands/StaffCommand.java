package com.elikill58.sanction.spigot.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.elikill58.sanction.spigot.staffmode.StaffMode;

public class StaffCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if (!(sender instanceof Player p))
			return false;
		if(StaffMode.isStaffMode(p)) {
			StaffMode.stopStaffMode(p);
		} else
			StaffMode.startStaffMode(p);
		return false;
	}
}
