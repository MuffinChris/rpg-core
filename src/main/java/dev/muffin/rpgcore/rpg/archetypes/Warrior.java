package dev.muffin.rpgcore.rpg.archetypes;

import dev.muffin.rpgcore.rpg.rpgutils.RPGClassStats;

import static dev.muffin.rpgcore.rpg.rpgutils.ArchetypeConstants.*;

public class Warrior extends Archetype {

    public Warrior() {
        super(new RPGClassStats(WARRIOR_BASE_HP, WARRIOR_BASE_MANA, WARRIOR_BASE_MANA_REGEN));
    }
}
