package dev.muffin.rpgcore.rpg.classes;

import dev.muffin.rpgcore.rpg.utils.RPGClassStats;
import dev.muffin.rpgcore.rpg.skills.Skill;

import java.util.List;

public class RPGClass {

    private final String name;
    private final RPGClassStats stats;
    private final List<Skill> skillList;
    private final List<Skill> modifiedSkillList;

    public RPGClass(String name, RPGClassStats baseStats, List<Skill> skillList, List<Skill> modifiedSkillList) {
        this.name = name;
        stats = baseStats;
        this.skillList = skillList;
        this.modifiedSkillList = modifiedSkillList;
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
    public List<Skill> getModifiedSkillList() {
        return modifiedSkillList;
    }

}
