package com.elikill58.sanction.spigot.staffmode.invender.invsee;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.elikill58.sanction.spigot.staffmode.invender.InvEnderHolder;
import com.elikill58.sanction.spigot.staffmode.invender.InvEnderType;

public class InvSeeHolder extends InvEnderHolder {
	
	public InvSeeHolder(Player p, OfflinePlayer cible) {
		super(p, cible, InvEnderType.INV);
	}
}
