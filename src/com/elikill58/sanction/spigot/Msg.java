package com.elikill58.sanction.spigot;

import org.bukkit.command.CommandSender;

import com.elikill58.sanction.universal.ChatUtils;
import com.elikill58.sanction.universal.UniversalUtils;

public class Msg {

	public static void sendMsg(CommandSender sender, String name, Object... placeholders) {
		sender.sendMessage(getMsg(name, placeholders));
	}

	public static String getMsg(String name, Object... placeholders) {
		return ChatUtils.applyColorCodes(UniversalUtils.replacePlaceholder(SanctionSpigot.getInstance().getConfig().getString("messages." + name, name), placeholders));
	}
}
