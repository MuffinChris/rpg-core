package dev.muffin.rpgcore.rpg.skills.skilltree;

import dev.muffin.rpgcore.Main;
import dev.muffin.rpgcore.chat.utils.ComponentConverter;
import dev.muffin.rpgcore.rpg.skills.Skill;
import dev.muffin.rpgcore.rpg.utils.RPGLevelInfo;
import dev.muffin.rpgcore.utilities.GUIItems;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

import static dev.muffin.rpgcore.rpg.skills.skilltree.SkillTreeConstants.*;
import static dev.muffin.rpgcore.utilities.GUIItems.generateItem;

public class SkillTree {

    private final Inventory warriorInventory;
    private int page;
    private final List<Inventory> inventories;
    private final Player player;
    private Inventory currentInventory;

    private final List<List<SkillTreeNode>> warriorSkillTreePages;

    public SkillTree(Player player) {
        page = 1;
        this.player = player;
        warriorInventory = Bukkit.createInventory(null, 54, Component.text("Warrior Skill Tree"));
        inventories = new ArrayList<>();
        inventories.add(warriorInventory);

        warriorSkillTreePages = new ArrayList<>();
        for (int i = 0; i < WARRIOR_MAX_PAGE; i++) {
            warriorSkillTreePages.add(new ArrayList<>());
        }
        generateWarriorSkillTree();
    }

    public List<Inventory> getInventories() {
        return inventories;
    }

    private void generateWarriorSkillTree() {
        SkillTreeNode cleave = new SkillTreeNode(
                Main.getInstance().getClassHandler().getWarrior().getSkillList().get(0),
                new ArrayList<>(),
                CLEAVE_SKILL_SLOT,
                new ArrayList<>()
        );
        warriorSkillTreePages.get(0).add(cleave);

        SkillTreeNode shatterstrike = new SkillTreeNode(
                Main.getInstance().getClassHandler().getWarrior().getSkillList().get(1),
                new ArrayList<>(List.of(cleave)),
                SHATTERSTRIKE_SKILL_SLOT,
                new ArrayList<>(List.of(new PathItem(PathDirection.DOWN, CLEAVE_SHATTERSTIKE_PATH_SLOT, cleave)))
        );
        warriorSkillTreePages.get(0).add(shatterstrike);
    }

    public void openWarriorInventory(int skillpoints, List<Skill> unlockedSkills) {
        currentInventory = warriorInventory;
        player.openInventory(warriorInventory);
        setWarriorInventory(skillpoints, unlockedSkills);
    }

    private void setWarriorInventory(int skillpoints, List<Skill> unlockedSkills) {
        Inventory topInventory = player.getOpenInventory().getTopInventory();
        if (page == 1) {
            topInventory.setItem(4, getWarriorDescriptionItem());
        } else if (page == 2) {
            topInventory.setItem(4, getWarriorDescriptionItem());
        }
        openPage(unlockedSkills);
        setBottomInventory(skillpoints);
    }

    private void openPage(List<Skill> unlockedSkills) {
        for (SkillTreeNode node : warriorSkillTreePages.get(page - 1)) {
            node.loadSkillNodeItem(player, unlockedSkills);
            node.loadPaths(player, unlockedSkills);
        }
    }

    private void setBottomInventory(int skillpoints) {
        player.getInventory().setItem(PAGE_UP_SLOT, getPageUpItem());
        player.getInventory().setItem(SKILLPOINTS_SLOT, getSkillpointCountItem(skillpoints));
        player.getInventory().setItem(PAGE_DOWN_SLOT, getPageDownItem());
        player.getInventory().setItem(SKILL_EQUIP_SLOT, getSkillEquipItem());
    }

    public void refreshInventory(int skillpoints, List<Skill> unlockedSkills) {
        if (currentInventory.equals(warriorInventory)) {
            player.getOpenInventory().getTopInventory().clear();
            setWarriorInventory(skillpoints, unlockedSkills);
        }
    }

    public void pageDown(int skillpoints, List<Skill> unlockedSkills) {
        page = Math.min(WARRIOR_MAX_PAGE, page + 1);
        refreshInventory(skillpoints, unlockedSkills);
    }

    public void pageUp(int skillpoints, List<Skill> unlockedSkills) {
        page = Math.max(1, page - 1);
        refreshInventory(skillpoints, unlockedSkills);
    }

    public void unlockSkill(int skillSlot, List<Skill> unlockedSkills, RPGLevelInfo rpgInfo) {
        if (warriorInventory.equals(currentInventory)) {
            unlockWarriorSkill(skillSlot, unlockedSkills, rpgInfo);
        }
    }

    private void unlockWarriorSkill(int skillSlot, List<Skill> unlockedSkills, RPGLevelInfo rpgInfo) {
        SkillTreeNode skillNodeToUnlock = null;
        for (SkillTreeNode skillNode : warriorSkillTreePages.get(page - 1)) {
            if (skillNode.getSlot() == skillSlot) {
                skillNodeToUnlock = skillNode;
                break;
            }
        }
        if (skillNodeToUnlock != null) {
            attemptUnlockSkill(skillNodeToUnlock, unlockedSkills, rpgInfo);
        }
    }

    private void attemptUnlockSkill(SkillTreeNode skillNode, List<Skill> unlockedSkills, RPGLevelInfo rpgInfo) {
        if (unlockedSkills.contains(skillNode.getSkill())) {
            player.sendMessage(Component.text("Skill already unlocked", NamedTextColor.RED));
            return;
        }
        if (!skillNode.isUnlockable(unlockedSkills)) {
            player.sendMessage(Component.text("Skill requirements not met, unlock the previous skills!", NamedTextColor.RED));
            return;
        }
        if (rpgInfo.getSkillpoints() < skillNode.getSkill().getSkillpointCost()) {
            player.sendMessage(Component.text("Not enough skillpoints", NamedTextColor.RED));
            return;
        }

        rpgInfo.setSkillpoints(rpgInfo.getSkillpoints() - skillNode.getSkill().getSkillpointCost());
        unlockedSkills.add(skillNode.getSkill());
        player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
        player.sendMessage(Component.text("Unlocked " + skillNode.getSkill().getSkillName(), NamedTextColor.GREEN));

        refreshInventory(rpgInfo.getSkillpoints(), unlockedSkills);
    }

    public ItemStack getSkillpointCountItem(int skillpoints) {
        List<String> lore = new ArrayList<>();
        lore.add("&7You have &e" + skillpoints + " Skillpoints");
        lore.add("&7Click a skill to unlock it.");
        lore.add("&7Skillpoints are gained from leveling up (+1/Level)");
        lore.add("&7Spend them wisely!");
        return generateItem(Material.EMERALD,
                Component.text("Skillpoints", NamedTextColor.WHITE), ComponentConverter.getComponentListFromStringList(lore), SKILLPOINTS_TEXTURE);
    }

    public ItemStack getSkillEquipItem() {
        List<String> lore = new ArrayList<>();
        lore.add("&7Click to open the skill selector");
        return generateItem(Material.EMERALD,
                Component.text("Equip Skills", NamedTextColor.WHITE), ComponentConverter.getComponentListFromStringList(lore), SKILL_EQUIP_TEXTURE);
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
