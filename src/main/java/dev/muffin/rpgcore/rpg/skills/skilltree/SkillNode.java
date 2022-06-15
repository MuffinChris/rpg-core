package dev.muffin.rpgcore.rpg.skills.skilltree;

import dev.muffin.rpgcore.rpg.skills.Skill;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static dev.muffin.rpgcore.utilities.GUIItems.generateItem;

public class SkillNode {

    private final Skill skill;
    private final List<SkillNode> requirements;
    private final int slot;
    private final List<PathItem> pathItems;

    public SkillNode(Skill skill, List<SkillNode> requirements, int slot, List<PathItem> pathItems) {
        this.skill = skill;
        this.requirements = requirements;
        this.slot = slot;
        this.pathItems = pathItems;
    }

    public Skill getSkill() {
        return skill;
    }

    public List<SkillNode> getRequirements() {
        return requirements;
    }

    public int getSlot() {
        return slot;
    }

    public boolean isUnlocked(List<Skill> unlockedSkills) {
        return unlockedSkills.contains(skill);
    }

    public List<PathItem> getPathItems() {
        return pathItems;
    }

    public void loadSkillNodeItem(Player p, List<Skill> unlockedSkills) {
        ItemStack item = generateItem(Material.EMERALD,
                Component.text("Skill: " + skill.getSkillName(), NamedTextColor.YELLOW),
                skill.getSkillDescription(p), skill.getTexture(p, isUnlockable(unlockedSkills)));
        p.getOpenInventory().getTopInventory().setItem(slot, item);
    }

    // Not all paths are on if unlocked. Path only on if successor is unlocked. Thus successor will call loadPaths
    public void loadPaths(Player p, List<Skill> unlockedSkills) {
        for (PathItem pathItem : pathItems) {
            pathItem.loadItem(this, p, unlockedSkills);
        }
    }

    public boolean isUnlockable(List<Skill> unlockedSkills) {
        for (SkillNode skillNode : requirements) {
            if (!skillNode.isUnlocked(unlockedSkills)) {
                return false;
            }
        }
        return true;
    }

}
