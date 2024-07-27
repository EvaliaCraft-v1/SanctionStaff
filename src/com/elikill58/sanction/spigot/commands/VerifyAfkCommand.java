package com.elikill58.sanction.spigot.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.elikill58.sanction.spigot.Msg;
import com.elikill58.sanction.spigot.hook.EssentialsHook;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class VerifyAfkCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if (!(sender instanceof Player p))
			return false;
		if (args.length >= 1) {
			Player cible = Bukkit.getPlayer(args[0]);
			if (cible == null) {
				Msg.sendMsg(p, "not_found", "%arg%", args[0]);
			} else {
				if(args.length > 1 && args[1].equalsIgnoreCase("tp_to_warp")) {
					EssentialsHook.tpToAfk(cible);
					Msg.sendMsg(p, "verifyafk.teleported", "%name%", cible.getName());
					return false;
				}
				sendMsg(sender, cible, EssentialsHook.isAfk(cible.getUniqueId()) ? "afk" : "not_afk");
			}
		} else
			Msg.sendMsg(p, "verifyafk.help");
		return false;
	}
	
	public static void sendMsg(CommandSender p, Player cible, String key) {
		Msg.sendMsg(p, "verifyafk." + key, "%name%", cible.getName());
		TextComponent text = new TextComponent();
		text.addExtra(create("verifyafk.to_warp", "/verifyafk " + cible.getName() + " tp_to_warp"));
		text.addExtra(" ");
		text.addExtra(create("verifyafk.sanction", "/sanction " + cible.getName()));
		p.spigot().sendMessage(text);
	}
	
	private static TextComponent create(String key, String cmd) {
		TextComponent tc = new TextComponent(Msg.getMsg(key + ".text"));
		tc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, cmd));
		tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(Msg.getMsg(key + ".hover"))));
		return tc;
	}
}
