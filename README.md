# SanctionStaff

## Les items

Voici un item avec tout les attributs :

```yml
material: "TYPE"
amount: 12
name: "Item incroyable."
unbreakable: true
lore:
- "&7C'est incroyable"
enchant:
- "DURABILITY"
flag:
- "HIDE_ENCHANTS"
color: "red"
```

Explications:
- `material` est le type de l'item. C'est **obligatoire**. C'est le seul. Il ne peut **PLUS** être un chiffre depuis la 1.13. [Correspondance ID <=> nom](https://www.digminecraft.com/lists/item_id_list_pc_1_8.php). Il faut mettre un nom: `MATERIAL` ([liste des materials](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html))
- `amount` est la quantité d'item. Défaut: 1.
- `name` est le nom de l'item. (Le message sera colorisé) Défaut: 
- `lore` correspond aux lores de l'item. Peux être :
     1. Un seul message: `lore: "lore"`
     2. Une liste de message: `lore: - "lore 1" - "lore 2"`
     3. Un seul message avec `\n` comme découpeur: `lore: "line 1\nline 2"`
- `unbreakable` indique si l'item sera cassable. Défaut: false.
- `enchant` est la liste des enchantements. Écrit comme ça: `ENCHANT_NAME` ou `ENCHANT_NAME:LVL`. Exemple: `DAMAGE_ALL:2`. ([liste des enchantements](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/enchantments/Enchantment.html))
- `flag` est la liste des flags de l'item. ([liste des flags](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/inventory/ItemFlag.html))
- `color` est la couleur des items fait en cuir. ([liste des couleurs](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Color.html))
