package com.elikill58.sanction.spigot.inventories.holder;

import org.bukkit.OfflinePlayer;

import com.elikill58.sanction.spigot.handler.ActionType;
import com.elikill58.sanction.spigot.inventories.SanctionHolder;

public class SanctionPlayerHolder extends SanctionHolder {

    private final OfflinePlayer cible;
    private final ActionType type;

    public SanctionPlayerHolder(OfflinePlayer cible, ActionType type) {
    	this.cible = cible;
    	this.type = type;
    }
    
    public OfflinePlayer getCible() {
    	return cible;
    }
    
    public ActionType getType() {
		return type;
	}
}
