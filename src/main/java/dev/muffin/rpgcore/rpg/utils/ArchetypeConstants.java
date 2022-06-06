package dev.muffin.rpgcore.rpg.utils;

import dev.muffin.rpgcore.rpg.skills.Skill;
import dev.muffin.rpgcore.rpg.skills.warrior.*;

import java.util.ArrayList;
import java.util.List;

public class ArchetypeConstants {

    // Warrior
    public static final String WARRIOR_NAME = "Warrior";
    public static final double WARRIOR_BASE_HP = 250;
    public static final double WARRIOR_HP_PER_LEVEL = 20;
    public static final double WARRIOR_BASE_MANA = 70;
    public static final double WARRIOR_MANA_PER_LEVEL = 3;
    public static final double WARRIOR_BASE_MANA_REGEN = 2;
    public static final double WARRIOR_MANA_REGEN_PER_LEVEL = 0.1;
    public static List<Skill> WARRIOR_SKILLS = new ArrayList<>(List.of(new Cleave(), new Shatterstrike())){};

}
