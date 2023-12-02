package com.elikill58.sanction.spigot.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import com.elikill58.sanction.spigot.SanctionSpigot;
import com.elikill58.sanction.universal.UniversalUtils;

public class Items {
	
	public static ItemStack getBackItem() {
		return getItem(SanctionSpigot.getInstance().getConfig().getConfigurationSection("back-item"));
	}

	@SuppressWarnings("deprecation")
	public static ItemStack getItem(ConfigurationSection sec, Object... placeholder) {
		if (sec == null)
			return null;
		Material beginItem = Material.getMaterial(sec.getString("material"));
		if (beginItem == null)
			return null;
		ItemStackBuilder builder = new ItemStackBuilder(beginItem);
		if (sec.contains("name"))
			builder.displayName(UniversalUtils.replacePlaceholder(sec.getString("name"), placeholder));
		if (sec.contains("lore")) {
			List<String> lore = new ArrayList<>();
			if (sec.get("lore") instanceof List)
				lore = sec.getStringList("lore");
			else
				lore.add(sec.getString("lore"));
			builder.lore(lore);
		}
		if (sec.contains("unbreakable"))
			builder.unbreakable(sec.getBoolean("unbreakable", false));
		if (sec.contains("flag")) {
			sec.getStringList("flag").stream().map(String::toUpperCase).map(ItemFlag::valueOf).forEach(builder::itemFlags);
		}
		if (sec.contains("color")) {
			if (sec.isConfigurationSection("color"))
				builder.leatherArmorColor(Color.fromRGB(sec.getInt("color.blue", 0), sec.getInt("color.green", 0), sec.getInt("color.red", 0)));
			else
				builder.leatherArmorColor(ColorUtils.getColor(sec.getString("color")));
		}
		if (sec.contains("amount"))
			builder.amount(sec.getInt("amount"));
		if (sec.contains("enchant")) {
			for (String s : sec.getStringList("enchant")) {
				Enchantment enchant;
				int level = 1;
				if (s.contains(":")) {
					enchant = Enchantment.getByName(s.split(":")[0]);
					level = Integer.parseInt(s.split(":")[1]);
				} else if (s.contains(",")) {
					enchant = Enchantment.getByName(s.split(",")[0]);
					level = Integer.parseInt(s.split(",")[1]);
				} else
					enchant = Enchantment.getByName(s);
				if (enchant != null)
					builder.unsafeEnchant(enchant, level);
				else
					SanctionSpigot.getInstance().getLogger().warning("Unknow enchant " + s);
			}
		}
		return builder.build();
	}
	
}
