package com.elikill58.sanction.spigot.inventories.holder;

import org.bukkit.entity.Player;

import com.elikill58.sanction.spigot.handler.Action;
import com.elikill58.sanction.spigot.inventories.SanctionHolder;

public class SanctionConfirmHolder extends SanctionHolder {

    private final Player cible;
    private final Action a;

    public SanctionConfirmHolder(Player cible, Action a) {
    	this.cible = cible;
    	this.a = a;
	}
    
    public Player getCible() {
    	return cible;
    }
    
    public Action getAction() {
		return a;
	}
}
