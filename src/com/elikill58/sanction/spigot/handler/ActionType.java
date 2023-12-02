package com.elikill58.sanction.spigot.handler;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import com.elikill58.sanction.spigot.SanctionSpigot;
import com.elikill58.sanction.spigot.utils.Items;

public enum ActionType {

	BAN(11), KICK(13), MUTE(15);

	private final int slot;
	private final List<Action> actions = new ArrayList<>();
	
	private ActionType(int slot) {
		this.slot = slot;
	}
	
	public List<Action> getActions() {
		return actions;
	}
	
	public int getSlot() {
		return slot;
	}
	
	public void loadActions(SanctionSpigot pl) {
		actions.clear();
		ConfigurationSection config = pl.getConfig().getConfigurationSection(name().toLowerCase() + ".actions");
		if(config == null)
			return;
		ItemStack item = Items.getItem(pl.getConfig().getConfigurationSection(name().toLowerCase() + ".items.main"));
		config.getKeys(false).stream().map(config::getConfigurationSection).forEach(a -> {
			actions.add(new Action(a.getString("name"), a.getString("lore"), a.getString("command"), a.getString("permission"), item, a.getInt("slot")));
		});
	}
}
