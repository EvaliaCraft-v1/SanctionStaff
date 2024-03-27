package com.elikill58.sanction.spigot.handler;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

public class EvaliaPlayer {

	private static final HashMap<UUID, EvaliaPlayer> PLAYERS = new HashMap<>();
	public static HashMap<UUID, EvaliaPlayer> getPlayers() {
		return PLAYERS;
	}
	public static EvaliaPlayer get(Player p) {
		return PLAYERS.computeIfAbsent(p.getUniqueId(), a -> new EvaliaPlayer(p));
	}
	
	private final Player p;
	private String clientName = "Inconnu";
	
	public EvaliaPlayer(Player p) {
		this.p = p;
	}
	
	public Player getPlayer() {
		return p;
	}
	
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	public String getClientName() {
		return clientName;
	}
}
