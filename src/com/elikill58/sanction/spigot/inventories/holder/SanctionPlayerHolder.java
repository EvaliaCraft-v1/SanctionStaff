package com.elikill58.sanction.spigot.inventories.holder;

import org.bukkit.entity.Player;

import com.elikill58.sanction.spigot.handler.ActionType;
import com.elikill58.sanction.spigot.inventories.SanctionHolder;

public class SanctionPlayerHolder extends SanctionHolder {

    private final Player cible;
    private final ActionType type;

    public SanctionPlayerHolder(Player cible, ActionType type) {
    	this.cible = cible;
    	this.type = type;
    }
    
    public Player getCible() {
    	return cible;
    }
    
    public ActionType getType() {
		return type;
	}
}
