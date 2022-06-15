package dev.muffin.rpgcore.rpg.classes;

import dev.muffin.rpgcore.rpg.skills.Skill;

import java.util.HashMap;

public class ClassHandler {
    private final RPGClass warrior;

    public ClassHandler() {
        warrior = new Warrior();
    }

    public RPGClass getWarrior() {
        return warrior;
    }
}
