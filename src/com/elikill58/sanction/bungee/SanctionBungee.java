package com.elikill58.sanction.bungee;

import net.md_5.bungee.api.plugin.Plugin;

public class SanctionBungee extends Plugin {

	public static SanctionBungee instance;
	public static SanctionBungee getInstance() {
		return instance;
	}
	
	@Override
	public void onEnable() {
		instance = this;

		getProxy().getPluginManager().registerListener(this, new BungeeListener());

		getProxy().registerChannel("sanction:sanctioncmd");
	}
}
