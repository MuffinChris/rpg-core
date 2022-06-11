package dev.muffin.rpgcore.rpg.classes;

import dev.muffin.rpgcore.rpg.utils.RPGClassStats;
import dev.muffin.rpgcore.rpg.skills.Skill;

import java.util.List;

public class RPGClass {

    private final String name;
    private final RPGClassStats stats;
    private final List<Skill> skillList;

    public RPGClass(String name, RPGClassStats baseStats, List<Skill> skillList) {
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
