package dev.muffin.rpgcore.rpg.skills;

import dev.muffin.rpgcore.Main;
import dev.muffin.rpgcore.rpg.utils.RPGInfo;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

import static dev.muffin.rpgcore.rpg.utils.constants.ArchetypeConstants.WARRIOR_SKILLS;

public class SkillTree {

    private Inventory warriorInventory;
    private List<Inventory> inventories;
    private RPGInfo rpgInfo;

    public SkillTree(RPGInfo rpgInfo) {
        this.rpgInfo = rpgInfo;
        warriorInventory = generateWarriorInventory();
        inventories = new ArrayList<>();
        inventories.add(warriorInventory);
    }

    public List<Inventory> getInventories() {
        return inventories;
    }

    private Inventory generateWarriorInventory() {
        warriorInventory = Bukkit.createInventory(null, 54, Component.text(""));

        warriorInventory
    }











    private Inventory generateWarriorInventory() {
        Inventory warriorSkillTreeGUI = Bukkit.createInventory(null, 54, Component.text("Warrior Skill Tree", NamedTextColor.YELLOW));

        warriorSkillTreeGUI.setItem(4, generateItem(Material.IRON_AXE,
                Component.text("The Path of the Warrior"),
                Component.text(""),
                Component.text("Warriors are strong and fierce fighters.", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE),
                Component.text("They can handle any melee fight,", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE),
                Component.text("but struggle with ranged opponents.", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE),
                Component.text("Skillpoints: ", NamedTextColor.GRAY)
                        .append(Component.text(Main.getInstance().getRPGPlayer(p).getPlayerClass().getSkillpoints(), NamedTextColor.YELLOW))
                        .decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE)
                ));

        warriorSkillTreeGUI.setItem(13, generateDownArrow());

        warriorSkillTreeGUI.setItem(22, generateCleaveSkill(p));

        warriorSkillTreeGUI.setItem(31, generateDownArrow());

        warriorSkillTreeGUI.setItem(40, generateShatterstrikeSkill(p));
        // might be smarter to assign all GUI data within a skill
        return warriorSkillTreeGUI;
    }

    public ItemStack generateItem(Material material, Component displayName, Component ... loreStrings) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(displayName.decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE));
        List<Component> lore = Arrays.asList(loreStrings);
        itemMeta.lore(lore);

        item.setItemMeta(itemMeta);

        return item;
    }

    public ItemStack generateDownArrow() {
        ItemStack item = new ItemStack(Material.EMERALD, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(Component.text(""));
        itemMeta.setCustomModelData(100000);
        item.setItemMeta(itemMeta);
        return item;
    }

    public ItemStack generateCleaveSkill(Player p) {

        ItemStack item = generateItem(Material.EMERALD,
                Component.text("Skill: Cleave", NamedTextColor.YELLOW),
                WARRIOR_SKILLS.get(0).getStats());
        ItemMeta meta = item.getItemMeta();

        Component unlocked = Component.text("&aAbility Unlocked");
        boolean unlockedBool = (Main.getInstance().getRPGPlayer(p).getPlayerClass().getSkillsOwned().contains(WARRIOR_SKILLS.get(0)));
        if (unlockedBool) {
            meta.lore().add(unlocked);
        }

        meta.setCustomModelData(WARRIOR_SKILLS.get(0).getTexture());
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack generateShatterstrikeSkill(Player p) {
        ItemStack item = generateItem(Material.EMERALD,
                Component.text("Skill: Shatterstrike", NamedTextColor.YELLOW),
                WARRIOR_SKILLS.get(1).getStats());
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(WARRIOR_SKILLS.get(1).getTexture());
        item.setItemMeta(meta);
        return item;
    }


}
