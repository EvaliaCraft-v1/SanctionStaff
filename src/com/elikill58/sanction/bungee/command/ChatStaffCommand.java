package com.elikill58.sanction.bungee.command;

import com.elikill58.sanction.bungee.BMsg;
import com.elikill58.sanction.bungee.BungeeConfig;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ChatStaffCommand extends Command {

	private static final String PERM = BungeeConfig.getConfig().getString("permissions.chatstaff");

	public ChatStaffCommand() {
		super("cs", PERM);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (args.length == 0)
			BMsg.sendMsg(sender, "cs.help");
		else {
			String msg = BMsg.getMsg("cs.message", "%name%", sender.getName(), "%message%", String.join(" ", args), "%server%",
					sender instanceof ProxiedPlayer p ? p.getServer().getInfo().getName() : "Console");
			for (ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
				if (all.hasPermission(PERM)) {
					all.sendMessage(new TextComponent(msg));
				}
			}
		}
	}
}
