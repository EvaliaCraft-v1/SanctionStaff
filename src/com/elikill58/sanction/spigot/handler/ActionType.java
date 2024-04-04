package com.elikill58.sanction.spigot.handler;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import com.elikill58.sanction.spigot.SanctionSpigot;
import com.elikill58.sanction.spigot.utils.Items;

public enum ActionType {

	BAN("Ban", 15),
	BANRSSTAFF("BanRsStaff", -1),
	KICK("Kick", 13),
	MUTE("Mute", 11),
	REPORT("Report", -1);

	private final String name;
	private final int slot;
	private final List<Action> actions = new ArrayList<>();
	private ItemStack item;
	
	private ActionType(String name, int slot) {
		this.name = name;
		this.slot = slot;
	}
	
	public String getName() {
		return name;
	}
	
	public List<Action> getActions() {
		return actions;
	}
	
	public int getSlot() {
		return slot;
	}
	
	public ItemStack getItem() {
		return item.clone();
	}
	
	public void loadActions(SanctionSpigot pl) {
		actions.clear();
		item = Items.getItem(pl.getConfig().getConfigurationSection(name().toLowerCase() + ".items.main"));
		ConfigurationSection config = pl.getConfig().getConfigurationSection(name().toLowerCase() + ".actions");
		if(config == null)
			return;
		config.getKeys(false).stream().map(config::getConfigurationSection).forEach(a -> {
			actions.add(new Action(a.getString("name"), a.isList("lore") ? String.join("\n", a.getStringList("lore")) : a.getString("lore"), a.getString("command"), a.getString("permission"), a.getString("reason"), a.getInt("slot"), a.getBoolean("proxy", false), this));
		});
	}
}
