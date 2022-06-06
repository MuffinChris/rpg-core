package dev.muffin.rpgcore.rpg.archetypes;

import dev.muffin.rpgcore.rpg.utils.RPGClassStats;
import dev.muffin.rpgcore.rpg.skills.Skill;

import java.util.List;

public class Archetype {

    private String name;
    private RPGClassStats stats;
    private List<Skill> skillList;

    public Archetype(String name, RPGClassStats baseStats, List<Skill> skillList) {
        this.name = name;
        stats = baseStats;
        this.skillList = skillList;
    }

    public String getName() {
        return name;
    }

    public RPGClassStats getStats() {
        return stats;
    }

    public List<Skill> getSkillList() {
        return skillList;
    }

}
