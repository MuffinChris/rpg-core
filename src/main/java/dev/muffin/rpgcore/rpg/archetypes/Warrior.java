package dev.muffin.rpgcore.rpg.archetypes;

import dev.muffin.rpgcore.rpg.utils.RPGClassStats;

import static dev.muffin.rpgcore.rpg.utils.constants.ArchetypeConstants.*;

public class Warrior extends Archetype {

    public Warrior() {
        super(WARRIOR_NAME,
                new RPGClassStats(WARRIOR_BASE_HP, WARRIOR_BASE_MANA, WARRIOR_BASE_MANA_REGEN,
                        WARRIOR_HP_PER_LEVEL, WARRIOR_MANA_PER_LEVEL, WARRIOR_MANA_REGEN_PER_LEVEL),
                WARRIOR_SKILLS);
    }
}
