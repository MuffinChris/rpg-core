package dev.muffin.rpgcore.rpg.skills;

import dev.muffin.rpgcore.Main;
import dev.muffin.rpgcore.chat.utils.ComponentConverter;
import dev.muffin.rpgcore.rpg.utils.RPGLevelInfo;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

import static dev.muffin.rpgcore.rpg.utils.constants.ArchetypeConstants.WARRIOR_SKILLS;

public class SkillTree {

    private Inventory warriorInventory;
    private List<Inventory> inventories;
    private Player player;

    public SkillTree(Player player) {
        this.player = player;
        warriorInventory = generateWarriorInventory();
        inventories = new ArrayList<>();
        inventories.add(warriorInventory);
    }

    public Inventory getWarriorInventory() {
        return warriorInventory;
    }

    public List<Inventory> getInventories() {
        return inventories;
    }

    private Inventory generateWarriorInventory() {
        warriorInventory = Bukkit.createInventory(null, 54, Component.text(""));

        warriorInventory.setItem(4, getWarriorDescriptionItem());

        warriorInventory.setItem(13, generateDownArrow());

        warriorInventory.setItem(22, generateSkillItem(WARRIOR_SKILLS.get(0), player));

        warriorInventory.setItem(31, generateDownArrow());

        warriorInventory.setItem(40, generateSkillItem(WARRIOR_SKILLS.get(1), player));

        return warriorInventory;
    }

    public ItemStack generateDownArrow() {
        ItemStack item = new ItemStack(Material.EMERALD, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(Component.text(""));
        itemMeta.setCustomModelData(100000);
        item.setItemMeta(itemMeta);
        return item;
    }

    public ItemStack generateSkillItem(Skill skill, Player p) {
        ItemStack item = generateItem(Material.EMERALD,
                Component.text("Skill: " + skill.getSkillName(), NamedTextColor.YELLOW),
                skill.getSkillDescription(p));
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(skill.getTexture());
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getWarriorDescriptionItem() {
        List<String> lore = new ArrayList<>();
        lore.add("&7Warriors are strong and fierce fighters");
        lore.add("&7They can handle any melee fight,");
        lore.add("&7but struggle with ranged combat.");
        return generateItem(Material.IRON_AXE, Component.text("The Path of the Warrior"),ComponentConverter.getComponentListFromStringList(lore));
    }

    public ItemStack generateItem(Material material, Component displayName, List<Component> lore) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(displayName.decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE));
        itemMeta.lore(lore);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(itemMeta);

        return item;
    }

}
