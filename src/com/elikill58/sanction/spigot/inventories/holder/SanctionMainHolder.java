package com.elikill58.sanction.spigot.inventories.holder;

import org.bukkit.OfflinePlayer;

import com.elikill58.sanction.spigot.inventories.SanctionHolder;

public class SanctionMainHolder extends SanctionHolder {
	
    private OfflinePlayer cible;

    public SanctionMainHolder(OfflinePlayer cible) {
    	this.cible = cible;
	}
    
    public OfflinePlayer getCible() {
    	return cible;
    }
}
