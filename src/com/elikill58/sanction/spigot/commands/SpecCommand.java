package com.elikill58.sanction.spigot.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpecCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if (!(sender instanceof Player p))
			return false;
		if(p.getGameMode().equals(GameMode.SPECTATOR)) {
			p.setGameMode(GameMode.SURVIVAL);
			
		} else {
			p.setGameMode(GameMode.SPECTATOR);
			
		}
		return false;
	}
}
