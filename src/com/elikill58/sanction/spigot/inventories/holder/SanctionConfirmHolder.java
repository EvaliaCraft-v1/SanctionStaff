package com.elikill58.sanction.spigot.inventories.holder;

import org.bukkit.OfflinePlayer;

import com.elikill58.sanction.spigot.handler.Action;
import com.elikill58.sanction.spigot.inventories.SanctionHolder;

public class SanctionConfirmHolder extends SanctionHolder {

    private final OfflinePlayer cible;
    private final Action a;

    public SanctionConfirmHolder(OfflinePlayer cible, Action a) {
    	this.cible = cible;
    	this.a = a;
	}
    
    public OfflinePlayer getCible() {
    	return cible;
    }
    
    public Action getAction() {
		return a;
	}
}
