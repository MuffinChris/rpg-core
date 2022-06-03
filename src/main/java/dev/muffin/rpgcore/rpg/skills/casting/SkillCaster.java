package dev.muffin.rpgcore.rpg.skills.casting;

import dev.muffin.rpgcore.rpg.player.PlayerClass;
import dev.muffin.rpgcore.rpg.skills.Skill;
import org.bukkit.Bukkit;

import java.util.UUID;

public class SkillCaster {

    private UUID uuid;
    private PlayerClass playerClass;

    public SkillCaster(UUID uuid, PlayerClass playerClass) {
        this.uuid = uuid;
        this.playerClass = playerClass;
    }

    /**
     * Cast a skill
     * @param skill the skill to cast
     * @return status of cast
     */
    public CastResponse cast(Skill skill) {
        if (playerClass.getStats().getMana() < skill.getManaCost()) {
            return CastResponse.NO_MANA;
        }
        skill.castSkill(Bukkit.getPlayer(uuid));
        return CastResponse.SUCCESS;
    }
}

