package dev.muffin.rpgcore.rpg.skills;

import dev.muffin.rpgcore.chat.utils.ComponentConverter;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class AugmentedSkill extends Skill {

    private final Skill toModify;

    public AugmentedSkill(Skill toModify, String skillName, double cooldown, double manaCost, int levelRequirement, int skillpointCost, int unlockedTexture, int unlockableTexture, int lockedTexture) {
        super(skillName, cooldown, manaCost, levelRequirement, skillpointCost, unlockedTexture, unlockableTexture, lockedTexture);
        this.toModify = toModify;
    }

    public Skill getToModify() {
        return toModify;
    }

    @Override
    public List<Component> getSkillDescription(Player caster) {
        List<Component> skillDescription = super.getSkillDescription(caster);

        skillDescription.add(0, ComponentConverter.getComponentFromString("&8Skill replaces &e" + toModify.getSkillName()));
        return skillDescription;
    }
}
