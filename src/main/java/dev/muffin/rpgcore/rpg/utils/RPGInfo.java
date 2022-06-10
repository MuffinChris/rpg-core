package dev.muffin.rpgcore.rpg.utils;

import dev.muffin.rpgcore.rpg.skills.Skill;

import java.util.List;

public class RPGInfo {

    private int level;
    private double exp;

    private int skillpoints;
    private List<Skill> skillsList;

    public RPGInfo(int level, double exp, int skillpoints, List<Skill> skillsList) {
        this.level = level;
        this.exp = exp;
        this.skillpoints = skillpoints;
        this.skillsList = skillsList;
    }


    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getExp() {
        return exp;
    }

    public void setExp(double exp) {
        this.exp = exp;
    }

    public int getSkillpoints() {
        return skillpoints;
    }

    public void setSkillpoints(int skillpoints) {
        this.skillpoints = skillpoints;
    }

    public List<Skill> getSkillsList() {
        return skillsList;
    }
}
