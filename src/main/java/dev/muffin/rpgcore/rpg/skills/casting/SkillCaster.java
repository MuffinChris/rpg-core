package dev.muffin.rpgcore.rpg.skills.casting;

import dev.muffin.rpgcore.rpg.player.PlayerClass;
import dev.muffin.rpgcore.rpg.skills.Skill;
import org.bukkit.Bukkit;

import java.util.UUID;

public class SkillCaster {

    private UUID uuid;
    private PlayerClass playerClass;
    private CooldownManager cooldownManager;

    public SkillCaster(UUID uuid, PlayerClass playerClass) {
        this.uuid = uuid;
        this.playerClass = playerClass;
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
    public CastResponse cast(Skill skill) {
        if (cooldownManager.isOnCooldown(skill)) {
            return CastResponse.ON_COOLDOWN;
        }
        if (playerClass.getStats().getMana() < skill.getManaCost()) {
            return CastResponse.NO_MANA;
        }
        playerClass.getStats().setMana(playerClass.getStats().getMana() - skill.getManaCost());
        skill.castSkill(Bukkit.getPlayer(uuid));
        cooldownManager.putOnCooldown(skill);
        return CastResponse.SUCCESS;
    }
}

