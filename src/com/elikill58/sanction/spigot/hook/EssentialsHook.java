package com.elikill58.sanction.spigot.hook;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.earth2me.essentials.Essentials;
import com.elikill58.sanction.spigot.SanctionSpigot;
import com.elikill58.sanction.spigot.commands.VerifyAfkCommand;

import net.ess3.api.IUser;
import net.ess3.api.events.AfkStatusChangeEvent;

public class EssentialsHook implements Listener {

	private static final List<IUser> afkPlayers = new ArrayList<>();
	
	@SuppressWarnings("deprecation")
	public static void load(SanctionSpigot pl) {
		Bukkit.getPluginManager().registerEvents(new EssentialsHook(), pl);
		
		Bukkit.getScheduler().runTaskTimer(pl, () -> {
			for(IUser user : new ArrayList<>(afkPlayers)) {
				long diff = System.currentTimeMillis() - user.getAfkSince();
				if(diff > 10 * 60 * 1000) { // 10 minutes afk
					afkPlayers.remove(user);
					Bukkit.getOnlinePlayers().forEach(all -> {
						if(all.hasPermission(SanctionSpigot.getInstance().getConfig().getString("permissions.verifyafk_alerts", "verifyafk.alerts"))) {
							VerifyAfkCommand.sendMsg(all, user.getBase(), "afk");
						}
					});
				}
			}
		}, 20, 20);
	}
	
	private static Essentials get() {
		return ((Essentials) Bukkit.getPluginManager().getPlugin("Essentials"));
	}

	public static boolean isAfk(UUID uuid) {
		return get().getUser(uuid).isAfk();
	}
	
	public static void tpToAfk(Player p) {
		try {
			Location loc = get().getWarps().getWarp("afk");
			if(loc != null)
				p.teleport(loc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@EventHandler
	public void onAfkChange(AfkStatusChangeEvent e) {
		if(e.getValue()) {
			afkPlayers.add(e.getAffected());
		}
	}
}
