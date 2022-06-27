package dev.muffin.rpgcore.rpg.skills.abstracts;

import dev.muffin.rpgcore.chat.utils.ComponentConverter;
import dev.muffin.rpgcore.rpg.player.RPGPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class AugmentedPassiveSkill extends PassiveSkill{

    private final PassiveSkill toModify;

    public AugmentedPassiveSkill(PassiveSkill toModify, String skillName, int levelRequirement, int skillpointCost, int unlockedTexture, int unlockableTexture, int lockedTexture) {
        super(skillName, levelRequirement, skillpointCost, unlockedTexture, unlockableTexture, lockedTexture);
        this.toModify = toModify;
    }

    public PassiveSkill getToModify() {
        return toModify;
    }

    @Override
    public List<Component> getSkillDescription(RPGPlayer rpgPlayer) {
        List<Component> skillDescription = super.getSkillDescription(rpgPlayer);

        skillDescription.add(0, ComponentConverter.getComponentFromString("&8Skill replaces &e" + toModify.getSkillName()));
        return skillDescription;
    }

}
