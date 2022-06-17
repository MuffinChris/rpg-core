package dev.muffin.rpgcore.rpg.skills.abstracts;

import org.bukkit.entity.Player;

public abstract class PassiveSkill extends Skill {

    public PassiveSkill(String skillName, int levelRequirement, int skillpointCost, int unlockedTexture, int unlockableTexture, int lockedTexture) {
        super(skillName, -1, -1, levelRequirement, skillpointCost, unlockedTexture, unlockableTexture, lockedTexture);
    }

    public abstract void activate(Player caster);
    public abstract void deactivate(Player caster);

}
