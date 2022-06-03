package dev.muffin.rpgcore.rpg.skills.warrior;

import dev.muffin.rpgcore.rpg.rpgutils.RPGSymbols;
import dev.muffin.rpgcore.rpg.skills.Skill;
import org.bukkit.entity.Player;

public class Cleave extends Skill {

    public Cleave() {
        super("Cleave", "Swing powerfully in an arc.", 7.0, 20, 1, 1);
    }

    @Override
    public void castSkill(Player caster) {
        caster.sendMessage(RPGSymbols.WATER_DAMAGE);
    }
}
