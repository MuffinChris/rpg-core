package dev.muffin.rpgcore.rpg.skills.casting;

import dev.muffin.rpgcore.rpg.player.PlayerClass;
import dev.muffin.rpgcore.rpg.skills.Skill;
import org.bukkit.entity.Player;

public class SkillCaster {

    private final Player player;
    private final CooldownManager cooldownManager;

    public SkillCaster(Player player) {
        this.player = player;
        cooldownManager = new CooldownManager();
    }

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }

    /**
     * Cast a skill
     * @param skill the skill to cast
     * @return status of cast
     */
    public CastResponse cast(Skill skill, PlayerClass playerClass) {
        if (skill == null) {
            return CastResponse.NOT_EQUIPPED;
        }
        if (cooldownManager.isOnCooldown(skill)) {
            return CastResponse.ON_COOLDOWN;
        }
        if (playerClass.getCurrentStats().getMana() < skill.getManaCost()) {
            return CastResponse.NO_MANA;
        }
        playerClass.getCurrentStats().setMana(playerClass.getCurrentStats().getMana() - skill.getManaCost());
        skill.castSkill(player);
        cooldownManager.putOnCooldown(skill);
        return CastResponse.SUCCESS;
    }
}

