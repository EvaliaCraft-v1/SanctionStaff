package com.elikill58.sanction.spigot;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;

import com.elikill58.sanction.universal.ChatUtils;
import com.elikill58.sanction.universal.UniversalUtils;

public class Msg {

	public static void sendMsg(CommandSender sender, String name, Object... placeholders) {
		List<String> msg;
		ConfigurationSection config = SanctionSpigot.getInstance().getConfig().getConfigurationSection("messages");
		if(config.isList(name))
			msg = config.getStringList(name);
		else
			msg = Arrays.asList(config.getString(name));
		for(String line : msg)
			sender.sendMessage(ChatUtils.applyColorCodes(UniversalUtils.replacePlaceholder(line, placeholders)));
	}

	public static String getMsg(String name, Object... placeholders) {
		return ChatUtils.applyColorCodes(UniversalUtils.replacePlaceholder(SanctionSpigot.getInstance().getConfig().getString("messages." + name, name), placeholders));
	}
}
