package dev.muffin.rpgcore.rpg.skills.skilltree;

import dev.muffin.rpgcore.rpg.skills.Skill;
import dev.muffin.rpgcore.utilities.GUIItems;
import org.bukkit.entity.Player;

import java.util.List;

import static dev.muffin.rpgcore.utilities.GUIItems.generateItem;

public class PathItem {

    private final int slot;
    private final PathDirection pathDirection;
    private final SkillTreeNode from;

    public PathItem(PathDirection pathDirection, int slot, SkillTreeNode from) {
        this.pathDirection = pathDirection;
        this.slot = slot;
        this.from = from;
    }

    public void loadItem(SkillTreeNode to, Player p, List<Skill> unlockedSkills) {
        int texture;
        if (from.isUnlocked(unlockedSkills) && to.isUnlocked(unlockedSkills)) {
            texture = pathDirection.getOnTexture();
        } else {
            texture = pathDirection.getOffTexture();
        }
        p.getOpenInventory().getTopInventory().setItem(slot, GUIItems.generateCustomIcon("", texture));
    }

}
