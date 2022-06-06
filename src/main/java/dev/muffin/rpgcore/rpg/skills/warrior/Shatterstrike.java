package dev.muffin.rpgcore.rpg.skills.warrior;

import dev.muffin.rpgcore.rpg.utils.RPGSymbols;
import dev.muffin.rpgcore.rpg.skills.Skill;
import org.bukkit.entity.Player;

public class Shatterstrike extends Skill {

    public Shatterstrike() {
        super("Shatterstrike", "Fiercely strike in front of you", 10.0, 40, 1, 1);
    }

    @Override
    public void castSkill(Player caster) {
        caster.sendMessage(RPGSymbols.EARTH_DAMAGE);
    }
}
