package com.elikill58.sanction.bungee;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.google.common.io.ByteStreams;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class BungeeConfig {

	private static File configFile;
	private static Configuration config;
	
	public static void init(SanctionBungee pl) {
		if(!pl.getDataFolder().exists()) {
			pl.getDataFolder().mkdir();
		}
		configFile = new File(pl.getDataFolder(), "config.yml");
		if(!configFile.exists()) {
			copy(pl, "config_bungee.yml", configFile);
		}
		try {
			config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Configuration getConfig() {
		return config;
	}

	private static File copy(Plugin pl, String fileName, File f) {
		try (InputStream in = pl.getResourceAsStream(fileName); OutputStream out = new FileOutputStream(f)) {
			ByteStreams.copy(in, out);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return f;
	}
	
}
