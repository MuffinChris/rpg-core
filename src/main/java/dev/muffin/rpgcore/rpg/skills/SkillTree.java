package dev.muffin.rpgcore.rpg.skills;

import dev.muffin.rpgcore.Main;
import dev.muffin.rpgcore.chat.utils.ComponentConverter;
import dev.muffin.rpgcore.rpg.player.PlayerClass;
import dev.muffin.rpgcore.rpg.utils.RPGLevelInfo;
import dev.muffin.rpgcore.utilities.GUIItems;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

import static dev.muffin.rpgcore.rpg.skills.SkillTreeConstants.*;
import static dev.muffin.rpgcore.utilities.GUIItems.generateItem;
import static dev.muffin.rpgcore.utilities.GUIItems.generateSkillItem;

public class SkillTree {

    private final Inventory warriorInventory;
    private int page;
    private final List<Inventory> inventories;
    private final Player player;
    private Inventory currentInventory;

    public SkillTree(Player player) {
        page = 1;
        this.player = player;
        warriorInventory = Bukkit.createInventory(null, 54, Component.text("Warrior Skill Tree"));
        inventories = new ArrayList<>();
        inventories.add(warriorInventory);
    }

    public List<Inventory> getInventories() {
        return inventories;
    }

    public void unlockSkill(int skillSlot, List<Skill> unlockedSkills, RPGLevelInfo rpgInfo) {
        if (warriorInventory.equals(currentInventory)) {
            unlockWarriorSkill(skillSlot, unlockedSkills, rpgInfo);
        }
    }

    public void unlockWarriorSkill(int skillSlot, List<Skill> unlockedSkills, RPGLevelInfo rpgInfo) {
        if (skillSlot == CLEAVE_SKILL_SLOT) {
            Skill skill = Main.getInstance().getClassHandler().getWarrior().getSkillList().get(0);
            attemptUnlockSkill(skill, unlockedSkills, rpgInfo);
        }
        else if (skillSlot == SHATTERSTRIKE_SKILL_SLOT) {
            Skill skill = Main.getInstance().getClassHandler().getWarrior().getSkillList().get(1);
            attemptUnlockSkill(skill, unlockedSkills, rpgInfo);
        }
    }

    public void attemptUnlockSkill(Skill skill, List<Skill> unlockedSkills, RPGLevelInfo rpgInfo) {
        if (!unlockedSkills.contains(skill)) {
            if (rpgInfo.getSkillpoints() >= skill.getSkillpointCost()) {
                rpgInfo.setSkillpoints(rpgInfo.getSkillpoints() - skill.getSkillpointCost());
                unlockedSkills.add(skill);
                player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                player.sendMessage(Component.text("Unlocked " + skill.getSkillName(), NamedTextColor.GREEN));

                refreshInventory(rpgInfo.getSkillpoints());
            } else {
                player.sendMessage(Component.text("Not enough skillpoints", NamedTextColor.RED));
            }
        }
    }

    public void openWarriorInventory(int skillpoints) {
        currentInventory = warriorInventory;
        player.openInventory(warriorInventory);
        setWarriorInventory(skillpoints);
    }

    private void setWarriorInventory(int skillpoints) {
        Inventory topInventory = player.getOpenInventory().getTopInventory();
        if (page == 1) {
            topInventory.setItem(4, getWarriorDescriptionItem());

            topInventory.setItem(13, generateDownArrow());

            topInventory.setItem(CLEAVE_SKILL_SLOT, generateSkillItem(Main.getInstance().getClassHandler().getWarrior().getSkillList().get(0), player));

            topInventory.setItem(31, generateDownArrow());

            topInventory.setItem(SHATTERSTRIKE_SKILL_SLOT, generateSkillItem(Main.getInstance().getClassHandler().getWarrior().getSkillList().get(1), player));
        } else if (page == 2) {
            topInventory.setItem(4, getWarriorDescriptionItem());
        }

        setBottomInventory(skillpoints);
    }

    public void setBottomInventory(int skillpoints) {
        player.getInventory().setItem(PAGE_UP_SLOT, getPageUpItem());
        player.getInventory().setItem(13, getSkillpointCountItem(skillpoints));
        player.getInventory().setItem(PAGE_DOWN_SLOT, getPageDownItem());
    }

    public void refreshInventory(int skillpoints) {
        if (currentInventory.equals(warriorInventory)) {
            player.getOpenInventory().getTopInventory().clear();
            setWarriorInventory(skillpoints);
        }
    }

    public void pageDown(int skillpoints) {
        page = Math.min(WARRIOR_MAX_PAGE, page + 1);
        refreshInventory(skillpoints);
    }

    public void pageUp(int skillpoints) {
        page = Math.max(1, page - 1);
        refreshInventory(skillpoints);
    }

    public ItemStack generateDownArrow() {
        return GUIItems.generateCustomIcon("", DOWN_ARROW_TEXTURE);
    }

    public ItemStack getSkillpointCountItem(int skillpoints) {
        List<String> lore = new ArrayList<>();
        lore.add("&7You have &e" + skillpoints + " Skillpoints");
        lore.add("&7Click a skill to unlock it.");
        lore.add("&7Skillpoints are gained from leveling up (+1/Level)");
        lore.add("&7Spend them wisely!");
        return generateItem(Material.EMERALD,
                Component.text("Skillpoints", NamedTextColor.WHITE), ComponentConverter.getComponentListFromStringList(lore));
    }

    public ItemStack getPageDownItem() {
        return GUIItems.generateCustomIcon("Page Down", PAGE_DOWN_TEXTURE);
    }

    public ItemStack getPageUpItem() {
        return GUIItems.generateCustomIcon("Page Up", PAGE_UP_TEXTURE);
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
