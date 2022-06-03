package dev.muffin.rpgcore.rpg.archetypes;

import dev.muffin.rpgcore.rpg.rpgutils.RPGClassStats;

public class Archetype {

    private RPGClassStats stats;

    public Archetype(RPGClassStats baseStats) {
        stats = baseStats;
    }

    public RPGClassStats getStats() {
        return stats;
    }

}
