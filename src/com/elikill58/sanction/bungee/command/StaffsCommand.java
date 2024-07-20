package com.elikill58.sanction.bungee.command;

import java.util.StringJoiner;

import com.elikill58.sanction.bungee.BMsg;
import com.elikill58.sanction.bungee.BungeeConfig;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class StaffsCommand extends Command {

	private static final String PERM = BungeeConfig.getConfig().getString("permissions.staffs");

	public StaffsCommand() {
		super("staffs", PERM);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		BMsg.sendMsg(sender, "staffs.header");
		for(String srvname : ProxyServer.getInstance().getServers().keySet().stream().sorted((a, b) -> {
				if(b.equalsIgnoreCase("Creatif"))
					return -1;
				return a.compareTo(b);
			}).toList()) {
			ServerInfo srv = ProxyServer.getInstance().getServerInfo(srvname);
			StringJoiner sj = new StringJoiner(", ");
			for(ProxiedPlayer pp : srv.getPlayers())
				if(pp.hasPermission(PERM))
					sj.add(pp.getName());
			BMsg.sendMsg(sender, "staffs.line", "%names%", sj.length() == 0 ? BMsg.getMsg("staffs.none") : sj.toString(), "%server%", srv.getName());
		}
		BMsg.sendMsg(sender, "staffs.footer");
	}
}
