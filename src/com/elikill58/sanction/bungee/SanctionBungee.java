package com.elikill58.sanction.bungee;

import com.elikill58.sanction.bungee.command.ChatStaffCommand;
import com.elikill58.sanction.bungee.command.StaffsCommand;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class SanctionBungee extends Plugin {

	public static SanctionBungee instance;
	public static SanctionBungee getInstance() {
		return instance;
	}
	
	@Override
	public void onEnable() {
		instance = this;
		BungeeConfig.init(this);
		
		PluginManager pm = getProxy().getPluginManager();
		pm.registerListener(this, new BungeeListener());
		pm.registerCommand(this, new ChatStaffCommand());
		pm.registerCommand(this, new StaffsCommand());

		getProxy().registerChannel("sanction:sanctioncmd");
	}
}
