package com.elikill58.sanction.spigot.hook;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.earth2me.essentials.Essentials;
import com.elikill58.sanction.spigot.SanctionSpigot;

import net.ess3.api.IUser;
import net.ess3.api.events.AfkStatusChangeEvent;

public class EssentialsHook implements Listener {

	private static final HashMap<IUser, Long> afkPlayers = new HashMap<>();
	
	@SuppressWarnings("deprecation")
	public static void load(SanctionSpigot pl) {
		Bukkit.getPluginManager().registerEvents(new EssentialsHook(), pl);
		
		Bukkit.getScheduler().runTaskTimer(pl, () -> {
			for(Entry<IUser, Long> entry : new HashMap<>(afkPlayers).entrySet()) {
				IUser user = entry.getKey();
				long diff = System.currentTimeMillis() - entry.getValue();
				if(diff > pl.getConfig().getLong("verifyafk-time", 10 * 1000)) { // 10 minutes afk
					afkPlayers.remove(user);
					tpToAfk(user.getBase());
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
			afkPlayers.put(e.getAffected(), System.currentTimeMillis());
		} else
			afkPlayers.remove(e.getAffected());
	}
}
