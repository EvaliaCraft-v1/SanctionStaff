package com.elikill58.sanction.spigot.utils;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class PacketUtils {

	public static final String VERSION = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
	public static final String NMS_PREFIX = "net.minecraft.", OBC_PREFIX;

	/**
	 * This Map is to reduce Reflection action which take more ressources than just
	 * RAM action
	 */
	private static final HashMap<String, Class<?>> ALL_CLASS = new HashMap<>();

	static {
		OBC_PREFIX = "org.bukkit.craftbukkit." + VERSION + ".";
	}

	public static Class<?> getNmsClass(String name) {
		return getNmsClass(name, "");
	}

	/**
	 * Get the Class in NMS, with a processing reducer
	 * 
	 * @param name of the NMS class (in net.minecraft package ONLY, because it's
	 *             NMS)
	 * @return clazz the searched class
	 */
	public static Class<?> getNmsClass(String name, String packagePrefix) {
		if (ALL_CLASS.containsKey(name))
			return ALL_CLASS.get(name);
		try {
			Class<?> clazz = Class.forName(NMS_PREFIX + packagePrefix + name);
			ALL_CLASS.put(name, clazz);
			return clazz;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Get the Class in OBC, with a processing reducer
	 * 
	 * @param name of the OBC class (in org.bukkit.craftbukkit package ONLY, because
	 *             it's NMS)
	 * @return clazz the searched class
	 */
	public static Class<?> getOBCClass(String name) {
		if (ALL_CLASS.containsKey(name))
			return ALL_CLASS.get(name);
		try {
			Class<?> clazz = Class.forName(OBC_PREFIX + name);
			ALL_CLASS.put(name, clazz);
			return clazz;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Create a new instance of a packet (without any parameters)
	 * 
	 * @param packetName the name of the packet which is in NMS
	 * @return the created packet
	 */
	public static Object createPacket(String packetName) {
		try {
			return getNmsClass(packetName, "network.protocol.game.").getConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Get NMS entity player of specified one
	 * 
	 * @param p the player that we want the NMS entity player
	 * @return the entity player
	 */
	public static Object getEntityPlayer(Player p) {
		try {
			return getOBCClass("entity.CraftPlayer").getMethod("getHandle").invoke(p);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Object getWorldServer(World w) {
		try {
			return getOBCClass("CraftWorld").getMethod("getHandle").invoke(w);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
