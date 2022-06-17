package dev.muffin.rpgcore.rpg.classes;

import dev.muffin.rpgcore.rpg.skills.warrior.*;

import java.util.ArrayList;
import java.util.List;

public class ClassHandler {
    private final RPGClass warrior;

    public ClassHandler() {

        Resistance resistance = new Resistance();
        Fortitude fortitude = new Fortitude(resistance);
        Cleave cleave = new Cleave();
        Shatterstrike shatterstrike = new Shatterstrike();
        BrutalSlam bluntForceSwing = new BrutalSlam(cleave);
        warrior = new Warrior(new ArrayList<>(List.of(cleave, shatterstrike)), new ArrayList<>(List.of(bluntForceSwing)),
                new ArrayList<>(List.of(resistance)), new ArrayList<>(List.of(fortitude)));
    }

    public RPGClass getWarrior() {
        return warrior;
    }
}
