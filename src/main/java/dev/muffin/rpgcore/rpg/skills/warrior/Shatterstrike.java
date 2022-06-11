package dev.muffin.rpgcore.rpg.skills.warrior;

import dev.muffin.rpgcore.rpg.utils.constants.RPGSymbols;
import dev.muffin.rpgcore.rpg.skills.Skill;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Shatterstrike extends Skill {

    public Shatterstrike() {
        super("Shatterstrike", 10.0, 40, 1, 1, 10000002);
    }

    @Override
    public List<String> getDescription(Player caster) {
        List<String> description = new ArrayList<>();
        description.add("&7STRIKE in a wide arc in front of you.");
        description.add("&7Deals &c60%% ATK &7as &c" + RPGSymbols.SLASH_DAMAGE.content() + " Slash &7damage.");
        return description;
    }

    @Override
    public void castSkill(Player caster) {
        caster.sendMessage(RPGSymbols.EARTH_DAMAGE);
    }
}
