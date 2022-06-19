package dev.muffin.rpgcore.rpg.damage;

import java.util.ArrayList;
import java.util.List;

public class DamageStack {
    private final List<DamageInstance> damageInstances;

    public DamageStack() {
        this.damageInstances = new ArrayList<>();
        // generate per event?. then use helper methods here to show holograms, etc.
        // store in players. review old therift. system was decent. just revise it. store damages and just handle them over and over.
    }
}
