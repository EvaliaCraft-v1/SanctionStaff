package com.elikill58.sanction.spigot;

import org.bukkit.command.CommandSender;

import com.elikill58.sanction.universal.ChatUtils;

public class Msg {

	public static void sendMsg(CommandSender sender, String name, String... placeholders) {
		sender.sendMessage(getMsg(name, placeholders));
	}

	public static String getMsg(String name, String... placeholders) {
		String message = SanctionSpigot.getInstance().getConfig().getString("messages." + name, name);
		for (int index = 0; index <= placeholders.length - 1; index += 2)
			message = message.replaceAll(placeholders[index], placeholders[index + 1]);
		return ChatUtils.applyColorCodes(message);
	}
}
