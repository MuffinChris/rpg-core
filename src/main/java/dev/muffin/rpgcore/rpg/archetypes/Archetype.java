package dev.muffin.rpgcore.rpg.archetypes;

import dev.muffin.rpgcore.rpg.rpgutils.RPGClassStats;
import dev.muffin.rpgcore.rpg.skills.Skill;

import java.util.List;

public class Archetype {

    private RPGClassStats stats;
    private List<Skill> skillList;

    public Archetype(RPGClassStats baseStats, List<Skill> skillList) {
        stats = baseStats;
        this.skillList = skillList;
    }

    public RPGClassStats getStats() {
        return stats;
    }

    public List<Skill> getSkillList() {
        return skillList;
    }

}
