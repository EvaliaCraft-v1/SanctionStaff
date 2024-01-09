package com.elikill58.sanction.bungee;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class BungeeListener implements Listener {

	@EventHandler
	public void onMessageReceived(PluginMessageEvent e) {
		if (!e.getTag().equalsIgnoreCase("sanction:sanctioncmd") || !(e.getReceiver() instanceof ProxiedPlayer p))
			return;
		try (ByteArrayInputStream ba = new ByteArrayInputStream(e.getData()); DataInputStream in = new DataInputStream(ba)) {
			String channel = in.readUTF();
			if (channel.equals("ExecuteCommand")) {
				String input = in.readUTF();
				SanctionBungee.getInstance().getLogger().info("Run command '" + input + "' as console");
				ProxyServer.getInstance().getPluginManager().dispatchCommand(ProxyServer.getInstance().getConsole(), input);
			} else if (channel.equals("ExecuteCommandAsPlayer")) {
				String input = in.readUTF();
				SanctionBungee.getInstance().getLogger().info("Run command '" + input + "' as " + p.getName());
				ProxyServer.getInstance().getPluginManager().dispatchCommand(p, input);
			} else if (channel.equals("AlertXray")) {
				String name = in.readUTF();
				String perm = BungeeConfig.getConfig().getString("permissions.see_alert_xray");
				for(ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
					if(all.hasPermission(perm)) {
						BMsg.sendMsg(all, "alert_xray", "%name%", p.getName(), "%minerai%", name, "%server%", p.getServer().getInfo().getName());
					}
				}
			} else if (channel.equals("StaffAlert")) {
				String msg = in.readUTF();
				String perm = BungeeConfig.getConfig().getString("permissions.staff");
				for(ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
					if(all.hasPermission(perm)) {
						all.sendMessage(new TextComponent(msg));
					}
				}
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
}
