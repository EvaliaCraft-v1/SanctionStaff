package com.elikill58.sanction.bungee;

import java.util.List;

import com.elikill58.sanction.universal.ChatUtils;
import com.elikill58.sanction.universal.UniversalUtils;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class BMsg {

	public static void sendMsg(CommandSender sender, String name, Object... placeholders) {
		List<String> list = BungeeConfig.getConfig().getStringList("messages." + name);
		if(list.isEmpty())
			list.add(BungeeConfig.getConfig().getString("messages." + name, name));
		list.forEach(s -> sender.sendMessage(new TextComponent(ChatUtils.applyColorCodes(UniversalUtils.replacePlaceholder(s, placeholders)))));
	}

	public static String getMsg(String name, Object... placeholders) {
		return ChatUtils.applyColorCodes(UniversalUtils.replacePlaceholder(BungeeConfig.getConfig().getString("messages." + name, name), placeholders));
	}
}
