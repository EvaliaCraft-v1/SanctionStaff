package com.elikill58.sanction.spigot.staffmode.invender.endersee;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.elikill58.sanction.spigot.staffmode.invender.InvEnderHolder;
import com.elikill58.sanction.spigot.staffmode.invender.InvEnderType;

public class EnderSeeHolder extends InvEnderHolder {
	
	public EnderSeeHolder(Player p, OfflinePlayer cible) {
		super(p, cible, InvEnderType.ENDER);
	}
}
