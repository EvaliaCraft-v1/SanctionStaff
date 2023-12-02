package com.elikill58.sanction.bungee;

import com.elikill58.sanction.universal.ChatUtils;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class BMsg {

	public static void sendMsg(CommandSender sender, String name, String... placeholders) {
		sender.sendMessage(new TextComponent(getMsg(name, placeholders)));
	}

	public static String getMsg(String name, String... placeholders) {
		String message = BungeeConfig.getConfig().getString("messages." + name, name);
		for (int index = 0; index <= placeholders.length - 1; index += 2)
			message = message.replaceAll(placeholders[index], placeholders[index + 1]);
		return ChatUtils.applyColorCodes(message);
	}
}
