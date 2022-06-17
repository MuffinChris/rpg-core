package dev.muffin.rpgcore.rpg.skills;

import dev.muffin.rpgcore.rpg.skills.abstracts.PassiveSkill;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PassiveManager {

    private final Player p;
    private final List<PassiveSkill> passiveSkills;

    public PassiveManager(Player p) {
        this.p = p;
        passiveSkills = new ArrayList<>();
    }

    public void addPassive(PassiveSkill skill) {
        passiveSkills.add(skill);
        skill.activate(p);
    }

    public void removePassive(PassiveSkill skill) {
        skill.deactivate(p);
        passiveSkills.remove(skill);
    }

    public void close() {
        for (PassiveSkill skill : passiveSkills) {
            skill.deactivate(p);
        }
    }

}
