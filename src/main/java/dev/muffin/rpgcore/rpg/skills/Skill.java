package dev.muffin.rpgcore.rpg.skills;

import org.bukkit.entity.Player;

public abstract class Skill {
    private final String skillName;
    private final String description;
    private final double cooldown;

    private final int manaCost;

    private final int levelRequirement;
    private final int skillpointCost;

    public Skill(String skillName, String description, double cooldown, int manaCost, int levelRequirement, int skillpointCost) {
        this.skillName = skillName;
        this.description = description;
        this.cooldown = cooldown;
        this.manaCost = manaCost;
        this.levelRequirement = levelRequirement;
        this.skillpointCost = skillpointCost;
    }

    public String getSkillName() {
        return skillName;
    }

    public String getDescription() {
        return description;
    }

    public double getCooldown() {
        return cooldown;
    }

    public int getManaCost() {
        return manaCost;
    }

    public int getLevelRequirement() {
        return levelRequirement;
    }

    public int getSkillpointCost() {
        return skillpointCost;
    }

    public abstract void castSkill(Player caster);

}
