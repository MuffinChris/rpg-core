package dev.muffin.rpgcore.rpg.skills.skilltree;

import dev.muffin.rpgcore.chat.utils.ComponentConverter;
import dev.muffin.rpgcore.rpg.player.RPGPlayer;
import dev.muffin.rpgcore.rpg.skills.*;
import dev.muffin.rpgcore.rpg.skills.abstracts.AugmentedPassiveSkill;
import dev.muffin.rpgcore.rpg.skills.abstracts.AugmentedSkill;
import dev.muffin.rpgcore.rpg.skills.abstracts.Skill;
import dev.muffin.rpgcore.rpg.skills.abstracts.Unlockable;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static dev.muffin.rpgcore.utilities.GUIItems.generateItem;
import static dev.muffin.rpgcore.utilities.GUIItems.generateSkillItem;

public class SkillTreeNode {

    private final Unlockable unlockable;
    private final List<SkillTreeNode> requirements;
    private final int slot;
    private final List<PathItem> pathItems;

    public SkillTreeNode(Unlockable unlockable, List<SkillTreeNode> requirements, int slot, List<PathItem> pathItems) {
        this.unlockable = unlockable;
        this.requirements = requirements;
        this.slot = slot;
        this.pathItems = pathItems;
    }

    public Unlockable getUnlockable() {
        return unlockable;
    }

    public List<SkillTreeNode> getRequirements() {
        return requirements;
    }

    public int getSlot() {
        return slot;
    }

    public boolean isUnlocked(RPGPlayer rpgPlayer) {

        if (unlockable instanceof Skill skill) {
            for (Skill sk : rpgPlayer.getSkillList().getUnlockedSkills()) {
                if (sk instanceof AugmentedSkill augmentedSkill) {
                    if (augmentedSkill.getToModify().equals(skill)) {
                        return true;
                    }
                } else if (sk instanceof AugmentedPassiveSkill augmentedPassiveSkill) {
                    if (augmentedPassiveSkill.getToModify().equals(skill)) {
                        return true;
                    }
                }
            }
            return rpgPlayer.getSkillList().getUnlockedSkills().contains(skill);
        }

        if (unlockable instanceof StatShard shard) {
            return rpgPlayer.getPlayerClass().hasStatShard(shard);
        }

        return false;
    }

    public List<PathItem> getPathItems() {
        return pathItems;
    }

    public void loadNodeItem(RPGPlayer rpgPlayer) {
        if (unlockable instanceof Skill skill) {
            ItemStack item = generateSkillItem(skill, rpgPlayer.getPlayer(), isUnlockable(rpgPlayer));
            rpgPlayer.getPlayer().getOpenInventory().getTopInventory().setItem(slot, item);
            return;
        }

        if (unlockable instanceof StatShard shard) {
            ItemStack item = generateItem(Material.EMERALD,
                    Component.text("Stat Shard", NamedTextColor.YELLOW),
                    ComponentConverter.getComponentListFromStringList(shard.getDescription(rpgPlayer.getPlayer())), shard.getTexture(rpgPlayer)
                    );
            rpgPlayer.getPlayer().getOpenInventory().getTopInventory().setItem(slot, item);
            return;
        }
    }

    // Not all paths are on if unlocked. Path only on if successor is unlocked. Thus successor will call loadPaths
    public void loadPaths(RPGPlayer rpgPlayer) {
        for (PathItem pathItem : pathItems) {
            pathItem.loadItem(this, rpgPlayer);
        }
    }

    public boolean isUnlockable(RPGPlayer rpgPlayer) {
        for (SkillTreeNode skillNode : requirements) {
            if (!skillNode.isUnlocked(rpgPlayer)) {
                return false;
            }
        }
        return true;
    }

}
