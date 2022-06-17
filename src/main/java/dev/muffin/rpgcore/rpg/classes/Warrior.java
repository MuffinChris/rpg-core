package dev.muffin.rpgcore.rpg.classes;

import dev.muffin.rpgcore.rpg.skills.Skill;
import dev.muffin.rpgcore.rpg.utils.RPGClassStats;

import java.util.List;

import static dev.muffin.rpgcore.rpg.classes.ClassConstants.*;

public class Warrior extends RPGClass {
    public Warrior(List<Skill> warriorSkills, List<Skill> warriorModifiedSkills) {
        super(WARRIOR_NAME,
                new RPGClassStats(WARRIOR_BASE_HP, WARRIOR_BASE_MANA, WARRIOR_BASE_MANA_REGEN,
                        WARRIOR_HP_PER_LEVEL, WARRIOR_MANA_PER_LEVEL, WARRIOR_MANA_REGEN_PER_LEVEL),
                warriorSkills, warriorModifiedSkills);
    }
}
