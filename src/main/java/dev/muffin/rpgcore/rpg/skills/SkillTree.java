package dev.muffin.rpgcore.rpg.skills;

import dev.muffin.rpgcore.Main;
import dev.muffin.rpgcore.chat.utils.ComponentConverter;
import dev.muffin.rpgcore.rpg.player.PlayerClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

import static dev.muffin.rpgcore.rpg.skills.SkillTreeConstants.*;
import static dev.muffin.rpgcore.utilities.GUIItems.generateItem;

public class SkillTree {

    private final Inventory warriorInventory;
    private int page;
    private final List<Inventory> inventories;
    private final Player player;
    private Inventory currentInventory;

    public SkillTree(Player player) {
        page = 1;
        this.player = player;
        warriorInventory = Bukkit.createInventory(null, 54, Component.text(""));
        inventories = new ArrayList<>();
        inventories.add(warriorInventory);
    }

    public List<Inventory> getInventories() {
        return inventories;
    }

    public void openWarriorInventory(PlayerClass playerClass) {
        currentInventory = warriorInventory;
        player.openInventory(warriorInventory);
        setWarriorInventory(playerClass);
    }

    private void setWarriorInventory(PlayerClass playerClass) {
        Inventory topInventory = player.getOpenInventory().getTopInventory();
        if (page == 1) {
            topInventory.setItem(4, getWarriorDescriptionItem());

            topInventory.setItem(13, generateDownArrow());

            topInventory.setItem(22, generateSkillItem(Main.getInstance().getClassHandler().getWarrior().getSkillList().get(0), player));

            topInventory.setItem(31, generateDownArrow());

            topInventory.setItem(40, generateSkillItem(Main.getInstance().getClassHandler().getWarrior().getSkillList().get(1), player));
        } else if (page == 2) {
            topInventory.setItem(4, getWarriorDescriptionItem());
        }

        setBottomInventory(playerClass);
    }

    public void setBottomInventory(PlayerClass playerClass) {
        player.getInventory().setItem(PAGE_UP_SLOT, getPageUpItem());
        player.getInventory().setItem(13, getSkillpointCountItem(playerClass));
        player.getInventory().setItem(PAGE_DOWN_SLOT, getPageDownItem());
    }

    public void refreshInventory(PlayerClass playerClass) {
        if (warriorInventory.equals(currentInventory)) {
            player.getOpenInventory().getTopInventory().clear();
            setWarriorInventory(playerClass);
        }
    }

    public void pageDown(PlayerClass playerClass) {
        page = Math.min(WARRIOR_MAX_PAGE, page + 1);
        refreshInventory(playerClass);
    }

    public void pageUp(PlayerClass playerClass) {
        page = Math.max(1, page - 1);
        refreshInventory(playerClass);
    }

    public ItemStack generateDownArrow() {
        ItemStack item = new ItemStack(Material.EMERALD, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(Component.text(""));
        itemMeta.setCustomModelData(DOWN_ARROW_TEXTURE);
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

    public ItemStack getSkillpointCountItem(PlayerClass playerClass) {
        List<String> lore = new ArrayList<>();
        lore.add("&7You have &e" + playerClass.getRpgInfo().getSkillpoints() + " Skillpoints");
        lore.add("&7Click a skill to unlock it.");
        lore.add("&7Skillpoints are gained from leveling up (+1/Level)");
        lore.add("&7Spend them wisely!");
        return generateItem(Material.EMERALD,
                Component.text("Skillpoints", NamedTextColor.WHITE), ComponentConverter.getComponentListFromStringList(lore));
    }

    public ItemStack getPageDownItem() {
        ItemStack item = new ItemStack(Material.EMERALD, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(Component.text("Page Down").decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE));
        itemMeta.setCustomModelData(PAGE_DOWN_TEXTURE);
        item.setItemMeta(itemMeta);
        return item;
    }

    public ItemStack getPageUpItem() {
        ItemStack item = new ItemStack(Material.EMERALD, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(Component.text("Page Up").decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE));
        itemMeta.setCustomModelData(PAGE_UP_TEXTURE);
        item.setItemMeta(itemMeta);
        return item;
    }

    public ItemStack getWarriorDescriptionItem() {
        List<String> lore = new ArrayList<>();
        lore.add("&7Warriors are strong and fierce fighters");
        lore.add("&7They can handle any melee fight,");
        lore.add("&7but struggle with ranged combat.");
        return generateItem(Material.IRON_AXE,
                Component.text("The Path of the Warrior"),ComponentConverter.getComponentListFromStringList(lore));
    }

}
