package dev.muffin.rpgcore.rpg.skills.skilltree;

import dev.muffin.rpgcore.rpg.player.RPGPlayer;
import dev.muffin.rpgcore.utilities.GUIItems;

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

    public void loadItem(SkillTreeNode to, RPGPlayer rpgPlayer) {
        int texture;
        if (from.isUnlocked(rpgPlayer) && to.isUnlocked(rpgPlayer)) {
            texture = pathDirection.getOnTexture();
        } else {
            texture = pathDirection.getOffTexture();
        }
        rpgPlayer.getPlayer().getOpenInventory().getTopInventory().setItem(slot, GUIItems.generateCustomIcon("", texture));
    }

}
