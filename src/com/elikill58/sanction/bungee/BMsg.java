package com.elikill58.sanction.bungee;

import com.elikill58.sanction.universal.ChatUtils;
import com.elikill58.sanction.universal.UniversalUtils;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class BMsg {

	public static void sendMsg(CommandSender sender, String name, Object... placeholders) {
		sender.sendMessage(new TextComponent(getMsg(name, placeholders)));
	}

	public static String getMsg(String name, Object... placeholders) {
		return ChatUtils.applyColorCodes(UniversalUtils.replacePlaceholder(BungeeConfig.getConfig().getString("messages." + name, name), placeholders));
	}
}
