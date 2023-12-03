package com.elikill58.sanction.spigot.utils;

import org.bukkit.entity.Player;

import com.elikill58.sanction.spigot.SanctionSpigot;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class SpigotToBungee {

	public static void sendCmdToBungee(Player p, String cmd) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("ExecuteCommand");
		out.writeUTF(cmd);
		p.sendPluginMessage(SanctionSpigot.getInstance(), "sanction:sanctioncmd", out.toByteArray());
	}

	public static void sendPlayerCmdToBungee(Player p, String cmd) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("ExecuteCommandAsPlayer");
		out.writeUTF(cmd);
		p.sendPluginMessage(SanctionSpigot.getInstance(), "sanction:sanctioncmd", out.toByteArray());
	}

	public static void sendAlertXrayToBungee(Player p, String name) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("AlertXray");
		out.writeUTF(name);
		p.sendPluginMessage(SanctionSpigot.getInstance(), "sanction:sanctioncmd", out.toByteArray());
	}
}
