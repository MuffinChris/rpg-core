package dev.muffin.rpgcore.rpg.skills.skilltree;

import dev.muffin.rpgcore.Main;
import dev.muffin.rpgcore.chat.utils.ComponentConverter;
import dev.muffin.rpgcore.rpg.player.RPGPlayer;
import dev.muffin.rpgcore.rpg.skills.abstracts.Skill;
import dev.muffin.rpgcore.rpg.skills.StatShard;
import dev.muffin.rpgcore.rpg.utils.RPGStatShard;
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

        SkillTreeNode resistance = new SkillTreeNode(
                Main.getInstance().getClassHandler().getWarrior().getPassiveList().get(0),
                new ArrayList<>(List.of(cleave)),
                15,
                new ArrayList<>(List.of(new PathItem(PathDirection.RIGHT, 14, cleave)))
        );
        warriorSkillTreePages.get(0).add(resistance);

        SkillTreeNode fortitude = new SkillTreeNode(
                Main.getInstance().getClassHandler().getWarrior().getModifiedPassiveList().get(0),
                new ArrayList<>(List.of(resistance)),
                33,
                new ArrayList<>(List.of(new PathItem(PathDirection.DOWN, 24, resistance)))
        );
        warriorSkillTreePages.get(0).add(fortitude);

        SkillTreeNode shatterstrike = new SkillTreeNode(
                Main.getInstance().getClassHandler().getWarrior().getSkillList().get(1),
                new ArrayList<>(List.of(cleave)),
                SHATTERSTRIKE_SKILL_SLOT,
                new ArrayList<>(List.of(new PathItem(PathDirection.DOWN, CLEAVE_SHATTERSTIKE_PATH_SLOT, cleave)))
        );
        warriorSkillTreePages.get(0).add(shatterstrike);

        SkillTreeNode statUpgrade = new SkillTreeNode(
                new StatShard("Stat Shard", 1,
                        new RPGStatShard(10, 0, 0, 5, 0),
                        STAT_SHARD_TEXTURE, STAT_SHARD_TEXTURE),
                new ArrayList<>(List.of(shatterstrike)),
                29,
                new ArrayList<>(List.of(new PathItem(PathDirection.LEFT, 30, shatterstrike)))
        );
        warriorSkillTreePages.get(0).add(statUpgrade);

        SkillTreeNode brutalSlam = new SkillTreeNode(
                Main.getInstance().getClassHandler().getWarrior().getModifiedSkillList().get(0),
                new ArrayList<>(List.of(shatterstrike)),
                BRUTALSLAM_SKILL_SLOT,
                new ArrayList<>(List.of(new PathItem(PathDirection.DOWN, 40, shatterstrike)))
        );
        warriorSkillTreePages.get(0).add(brutalSlam);
    }

    public void openWarriorInventory(RPGPlayer rpgPlayer) {
        currentInventory = warriorInventory;
        player.openInventory(warriorInventory);
        setWarriorInventory(rpgPlayer);
    }

    private void setWarriorInventory(RPGPlayer rpgPlayer) {
        Inventory topInventory = player.getOpenInventory().getTopInventory();
        if (page == 1) {
            topInventory.setItem(4, getWarriorDescriptionItem());
        } else if (page == 2) {
            topInventory.setItem(4, getWarriorDescriptionItem());
        }
        openPage(rpgPlayer);
        setBottomInventory(rpgPlayer.getPlayerClass().getRpgInfo().getSkillpoints());
    }

    private void openPage(RPGPlayer rpgPlayer) {
        for (SkillTreeNode node : warriorSkillTreePages.get(page - 1)) {
            node.loadNodeItem(rpgPlayer);
            node.loadPaths(rpgPlayer);
        }
    }

    private void setBottomInventory(int skillpoints) {
        player.getInventory().setItem(PAGE_UP_SLOT, getPageUpItem());
        player.getInventory().setItem(SKILLPOINTS_SLOT, getSkillpointCountItem(skillpoints));
        player.getInventory().setItem(PAGE_DOWN_SLOT, getPageDownItem());
        player.getInventory().setItem(SKILL_EQUIP_SLOT, getSkillEquipItem());
    }

    public void refreshInventory(RPGPlayer rpgPlayer) {
        if (currentInventory.equals(warriorInventory)) {
            player.getOpenInventory().getTopInventory().clear();
            setWarriorInventory(rpgPlayer);
        }
    }

    public void pageDown(RPGPlayer rpgPlayer) {
        page = Math.min(WARRIOR_MAX_PAGE, page + 1);
        refreshInventory(rpgPlayer);
    }

    public void pageUp(RPGPlayer rpgPlayer) {
        page = Math.max(1, page - 1);
        refreshInventory(rpgPlayer);
    }

    public void unlockSkill(int skillSlot, RPGPlayer rpgPlayer) {
        if (warriorInventory.equals(currentInventory)) {
            unlockWarriorSkill(skillSlot, rpgPlayer);
        }
    }

    private void unlockWarriorSkill(int skillSlot, RPGPlayer rpgPlayer) {
        SkillTreeNode skillNodeToUnlock = null;
        for (SkillTreeNode skillNode : warriorSkillTreePages.get(page - 1)) {
            if (skillNode.getSlot() == skillSlot) {
                skillNodeToUnlock = skillNode;
                break;
            }
        }
        if (skillNodeToUnlock != null) {
            attemptUnlockSkill(skillNodeToUnlock, rpgPlayer);
        }
    }

    private void attemptUnlockSkill(SkillTreeNode skillNode, RPGPlayer rpgPlayer) {

        if (skillNode.getUnlockable() instanceof Skill skill) {
            if (rpgPlayer.getSkillList().getUnlockedSkills().contains(skill)) {
                player.sendMessage(Component.text("Skill already unlocked", NamedTextColor.RED));
                return;
            }
        } else if (skillNode.getUnlockable() instanceof StatShard shard) {
            if (rpgPlayer.getPlayerClass().hasStatShard(shard)) {
                player.sendMessage(Component.text("Shard already unlocked", NamedTextColor.RED));
                return;
            }
        }

        if (!skillNode.isUnlockable(rpgPlayer)) {
            player.sendMessage(Component.text("Requirements not met", NamedTextColor.RED));
            return;
        }

        if (rpgPlayer.getPlayerClass().getRpgInfo().getSkillpoints() < skillNode.getUnlockable().getSkillpointCost()) {
            player.sendMessage(Component.text("Not enough skillpoints", NamedTextColor.RED));
            return;
        }

        rpgPlayer.getPlayerClass().getRpgInfo().setSkillpoints(rpgPlayer.getPlayerClass().getRpgInfo().getSkillpoints() - skillNode.getUnlockable().getSkillpointCost());

        if (skillNode.getUnlockable() instanceof Skill skill) {
            rpgPlayer.getSkillList().addSkill(skill);
        } else if (skillNode.getUnlockable() instanceof StatShard shard) {
            rpgPlayer.getPlayerClass().addRPGStats(shard);
        }
        player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
        player.sendMessage(Component.text("Unlocked " + skillNode.getUnlockable().getName(), NamedTextColor.GREEN));

        refreshInventory(rpgPlayer);
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
