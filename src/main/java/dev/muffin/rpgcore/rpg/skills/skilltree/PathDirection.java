package dev.muffin.rpgcore.rpg.skills.skilltree;

import static dev.muffin.rpgcore.rpg.skills.skilltree.SkillTreeConstants.*;

public enum PathDirection {
    UP(UP_ARROW_TEXTURE, UP_ARROW_TEXTURE_ON),DOWN(DOWN_ARROW_TEXTURE, DOWN_ARROW_TEXTURE_ON),
    LEFT(LEFT_ARROW_TEXTURE, LEFT_ARROW_TEXTURE_ON),RIGHT(RIGHT_ARROW_TEXTURE, RIGHT_ARROW_TEXTURE_ON);

    private final int offTexture;
    private final int onTexture;

    public int getOffTexture() {
        return offTexture;
    }

    public int getOnTexture() {
        return onTexture;
    }

    PathDirection(int offTexture, int onTexture) {
        this.offTexture = offTexture;
        this.onTexture = onTexture;
    }
}
