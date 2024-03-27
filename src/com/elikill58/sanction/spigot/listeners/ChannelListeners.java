package com.elikill58.sanction.spigot.listeners;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.elikill58.sanction.spigot.SanctionSpigot;
import com.elikill58.sanction.spigot.handler.EvaliaPlayer;

public class ChannelListeners implements PluginMessageListener {

	public static final String CHANNEL_NAME_BRAND = "minecraft:brand";
	
	@Override
	public void onPluginMessageReceived(String channel, Player p, byte[] data) {
		if(channel.equalsIgnoreCase(CHANNEL_NAME_BRAND)) {
			SanctionSpigot.getInstance().getLogger().info("Received channel event on " + channel + " > " + new String(data).substring(1));
			EvaliaPlayer.get(p).setClientName(new String(data).substring(1));
		}
	}
}
