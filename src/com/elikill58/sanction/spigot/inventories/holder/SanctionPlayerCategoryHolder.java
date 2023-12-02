package com.elikill58.sanction.spigot.inventories.holder;

import org.bukkit.entity.Player;

import com.elikill58.sanction.spigot.inventories.SanctionHolder;

public class SanctionPlayerCategoryHolder extends SanctionHolder {

    private Player cible;

    public SanctionPlayerCategoryHolder(Player cible) {
    	this.cible = cible;
	}
    
    public Player getCible() {
    	return cible;
    }
}
