package com.elikill58.sanction.spigot.utils;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import com.elikill58.sanction.spigot.SanctionSpigot;
import com.elikill58.sanction.universal.ChatUtils;

public class ItemStackBuilder implements Listener {

    protected ItemStack itemStack;
    protected ItemMeta itemMeta;

    /**
     * @param itemStack l'ItemStack servant de base au builder
     */
    public ItemStackBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
        itemMeta = itemStack.hasItemMeta() ? this.itemStack.getItemMeta() : Bukkit.getItemFactory().getItemMeta(this.itemStack.getType());
    }

    /**
     * @param material le matériel de l'ItemStack
     */
    public ItemStackBuilder(Material material) { this(new ItemStack(material)); }

    /**
     * @param material le matériel de l'ItemStack
     * @param amount le nombre d'items de l'ItemStack
     */
    public ItemStackBuilder(Material material, int amount) { this(new ItemStack(material, amount)); }

    /**
     * Définit le nom de l'ItemStack qui sera affiché en jeu.
     * Il sera également utilisé lors du processing des events.
     *
     * @param displayName le nom de l'ItemStack qui sera affiché en jeu. La valeur null reset le displayName.
     *
     * @return ce builder
     */
    public ItemStackBuilder displayName(@Nullable String displayName) {
        this.itemMeta.setDisplayName(ChatColor.RESET + ChatUtils.applyColorCodes(displayName));
        return this;
    }

    /**
     * Réinitialise le nom de l'ItemStack.
     * Équivaut à {@code #displayName(null)}
     *
     * @return ce builder
     */
    public ItemStackBuilder resetDisplayName() {
        return this.displayName(null);
    }

    /**
     * Ajoute un enchantement à cet ItemStack.
     *
     * @param enchantment l'enchantement à ajouter
     * @param level le niveau de cet enchantement
     *
     * @return ce builder
     */
    public ItemStackBuilder enchant(Enchantment enchantment, int level) {
        this.itemMeta.addEnchant(enchantment, level, true);
        return this;
    }

    /**
     * Ajoute un enchantement pouvant être unsafe sur cet ItemStack.
     *
     * @param enchantment l'enchantement à ajouter
     * @param level le niveau de cet enchantement
     *
     * @return ce builder
     */
    public ItemStackBuilder unsafeEnchant(Enchantment enchantment, int level) {
        this.itemMeta.addEnchant(enchantment, level, true);
        return this;
    }


	public ItemStackBuilder leatherArmorColor(Color color) {
		try {
			((LeatherArmorMeta) itemMeta).setColor(color);
		} catch (ClassCastException localClassCastException) {
			SanctionSpigot.getInstance().getLogger().severe("Cannot make " + itemMeta.getClass() + " (type: " + itemStack.getType().name() + ") a LeatherArmorMeta data.");
		}
		return this;
	}
	
    /**
     * Définit le type de l'ItemStack.
     *
     * @param type le type de l'ItemStack
     *
     * @return ce builder
     */
    public ItemStackBuilder type(Material type) {
        this.itemStack.setType(type);
        return this;
    }

    /**
     * Définit le nombre d'item sur cet ItemStack.
     *
     * @param amount le nombre d'items sur cet ItemStack
     *
     * @return ce builder
     */
    public ItemStackBuilder amount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    /**
     * Ajoute des {@link ItemFlag}s à l'ItemStack.
     *
     * @param itemFlags les ItemFlags à ajouter
     *
     * @return ce builder
     */
    public ItemStackBuilder itemFlags(ItemFlag... itemFlags) {
        this.itemMeta.addItemFlags(itemFlags);
        return this;
    }

    /**
     * Indique si oui ou non l'ItemStack est indestructible.
     * Il est souvent plus pratique d'utiliser {@link #unbreakable()}
     *
     *
     * @param unbreakable si, et seulement si true, l'ItemStack sera indestructible.
     * @return ce builder
     */
    public ItemStackBuilder unbreakable(boolean unbreakable) {
        this.itemMeta.setUnbreakable(unbreakable);
        return this;
    }

    /**
     * Définit l'ItemStack comme indestructible. Équivalent à {@code #unbreakable(true)}.
     *
     * @return ce builder
     */
    public ItemStackBuilder unbreakable() {
        return this.unbreakable(true);
    }

    /**
     * Définit le lore donnée en paramètre.
     *
     * @param lore le lore qu'aura l'{@link ItemStack}
     *
     * @return ce builder
     */
    public ItemStackBuilder lore(String... lore) {
        List<String> list = this.itemMeta.hasLore() ? this.itemMeta.getLore() : new ArrayList<>();
    	for(String s : lore)
    		for(String temp : s.split("\\n"))
        		for(String tt : temp.split("/n"))
        			list.add(ChatUtils.applyColorCodes(tt));
        this.itemMeta.setLore(list);
        return this;
    }
    
    public ItemStackBuilder lore(List<String> lore) {
        List<String> list = this.itemMeta.hasLore() ? this.itemMeta.getLore() : new ArrayList<>();
    	for(String s : lore)
    		for(String temp : s.split("\\n"))
        		for(String tt : temp.split("/n"))
        			list.add(ChatUtils.applyColorCodes(tt));
        this.itemMeta.setLore(list);
        return this;
    }

    /**
     * Ajoute au lore déjà présent les lignes données en paramètre.
     *
     * @param loreToAdd les lignes à ajouter au lore
     *
     * @return ce builder
     */
    public ItemStackBuilder addToLore(String... loreToAdd) {
        return lore(loreToAdd);
    }

    public ItemStack build() {
        this.itemStack.setItemMeta(this.itemMeta);

        return this.itemStack;
    }
}
