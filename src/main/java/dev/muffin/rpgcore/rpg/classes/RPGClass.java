package dev.muffin.rpgcore.rpg.classes;

import dev.muffin.rpgcore.rpg.utils.RPGClassStats;
import dev.muffin.rpgcore.rpg.skills.abstracts.Skill;

import java.util.List;

public class RPGClass {

    private final String name;
    private final RPGClassStats stats;
    private final List<Skill> skillList;
    private final List<Skill> modifiedSkillList;
    private final List<Skill> passiveList;
    private final List<Skill> modifiedPassiveList;

    public RPGClass(String name, RPGClassStats baseStats, List<Skill> skillList, List<Skill> modifiedSkillList, List<Skill> passiveList, List<Skill> modifiedPassiveList) {
        this.name = name;
        stats = baseStats;
        this.skillList = skillList;
        this.modifiedSkillList = modifiedSkillList;
        this.passiveList = passiveList;
        this.modifiedPassiveList = modifiedPassiveList;
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

    public List<Skill> getPassiveList() {
        return passiveList;
    }

    public List<Skill> getModifiedPassiveList() {
        return modifiedPassiveList;
    }
}
