package com.elikill58.sanction.spigot.utils;

import java.util.Arrays;

import org.bukkit.Color;

import net.md_5.bungee.api.ChatColor;

public class ColorUtils {

	public static boolean isColor(ChatColor c) {
		return !Arrays.asList(ChatColor.RESET, ChatColor.ITALIC, ChatColor.MAGIC, ChatColor.UNDERLINE, ChatColor.STRIKETHROUGH).contains(c);
	}
	
	public static Color fromHex(String code) {
		if(!code.startsWith("#"))
			code = "#" + code;
		return Color.fromRGB(Integer.valueOf(code.substring(1, 3), 16), Integer.valueOf(code.substring(3, 5), 16), Integer.valueOf(code.substring(5, 7), 16));
	}

	/**
	 * Get the color from the name
	 * 
	 * @param name the name of the color
	 * @return the color or null if not found
	 */
	public static Color getColor(String name) {
		if (name == null)
			return null;
		if(name.startsWith("#"))
			return fromHex(name);
		switch (name.toLowerCase()) {
		case "aqua":
			return Color.AQUA;
		case "blue":
			return Color.BLUE;
		case "navy":
			return Color.NAVY;
		case "teal":
			return Color.TEAL;
		case "olive":
			return Color.OLIVE;
		case "green":
			return Color.GREEN;
		case "lime":
			return Color.LIME;
		case "white":
			return Color.WHITE;
		case "yellow":
			return Color.YELLOW;
		case "orange":
			return Color.ORANGE;
		case "red":
			return Color.RED;
		case "fuchsia":
			return Color.FUCHSIA;
		case "purple":
			return Color.PURPLE;
		case "maroon":
			return Color.MAROON;
		case "black":
			return Color.BLACK;
		case "gray":
			return Color.GRAY;
		case "silver":
			return Color.SILVER;
		case "light_purple":
			return Color.fromRGB(16733695);
		case "dark_blue":
			return Color.fromRGB(170);
		case "dark_green":
			return Color.fromRGB(43520);
		case "dark_aqua":
			return Color.fromRGB(43690);
		case "dark_red":
			return Color.fromRGB(11141120);
		case "dark_purple":
			return Color.fromRGB(11141290);
		case "gold":
			return Color.fromRGB(16755200);
		case "dark_gray":
			return Color.fromRGB(5592405);
		default:
			if(name.length() == 6)
				return fromHex(name);
			return null;
		}
	}
}
