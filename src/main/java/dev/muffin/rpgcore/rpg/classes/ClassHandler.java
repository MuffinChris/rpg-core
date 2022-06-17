package dev.muffin.rpgcore.rpg.classes;

import dev.muffin.rpgcore.rpg.skills.Skill;
import dev.muffin.rpgcore.rpg.skills.warrior.BluntForceSwing;
import dev.muffin.rpgcore.rpg.skills.warrior.Cleave;
import dev.muffin.rpgcore.rpg.skills.warrior.Shatterstrike;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClassHandler {
    private final RPGClass warrior;

    public ClassHandler() {

        Cleave cleave = new Cleave();
        Shatterstrike shatterstrike = new Shatterstrike();
        BluntForceSwing bluntForceSwing = new BluntForceSwing(cleave);
        warrior = new Warrior(new ArrayList<>(List.of(cleave, shatterstrike)), new ArrayList<>(List.of(bluntForceSwing)));
    }

    public RPGClass getWarrior() {
        return warrior;
    }
}
