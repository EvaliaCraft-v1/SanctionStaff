package com.elikill58.sanction.bungee;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class BungeeListener implements Listener {

	@EventHandler
	public void onMessageReceived(PluginMessageEvent e) {
		if (!e.getTag().equalsIgnoreCase("sanction:sanctioncmd"))
			return;
		try (ByteArrayInputStream ba = new ByteArrayInputStream(e.getData()); DataInputStream in = new DataInputStream(ba)) {
			String channel = in.readUTF();
			if (channel.equals("ExecuteCommand")) {
				String input = in.readUTF();
				SanctionBungee.getInstance().getLogger().info("Run command '" + input + "' as console");
				ProxyServer.getInstance().getPluginManager().dispatchCommand(ProxyServer.getInstance().getConsole(), input);
			} else if (channel.equals("ExecuteCommandAsPlayer")) {
				String input = in.readUTF();
				CommandSender sender = (CommandSender) e.getReceiver();
				SanctionBungee.getInstance().getLogger().info("Run command '" + input + "' as " + sender.getName());
				ProxyServer.getInstance().getPluginManager().dispatchCommand(sender, input);
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
}
