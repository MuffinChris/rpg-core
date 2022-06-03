package dev.muffin.rpgcore.rpg.rpgutils;

import dev.muffin.rpgcore.rpg.skills.Skill;
import dev.muffin.rpgcore.rpg.skills.warrior.Cleave;

import java.util.ArrayList;
import java.util.List;

public class ArchetypeConstants {

    // Warrior
    public static final double WARRIOR_BASE_HP = 250;
    public static final int WARRIOR_BASE_MANA = 70;
    public static final int WARRIOR_BASE_MANA_REGEN = 5;
    public static List<Skill> WARRIOR_SKILLS = new ArrayList<>(List.of(new Cleave())){};

}
