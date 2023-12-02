package com.elikill58.sanction.universal;

import net.md_5.bungee.api.ChatColor;

public class ChatUtils {

	public static String applyColorCodes(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
}
