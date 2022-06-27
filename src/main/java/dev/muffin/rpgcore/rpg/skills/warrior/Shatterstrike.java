package dev.muffin.rpgcore.rpg.skills.warrior;

import dev.muffin.rpgcore.rpg.player.RPGPlayer;
import dev.muffin.rpgcore.rpg.utils.constants.RPGSymbols;
import dev.muffin.rpgcore.rpg.skills.abstracts.Skill;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static dev.muffin.rpgcore.rpg.skills.warrior.WarriorSkillTextures.*;

public class Shatterstrike extends Skill {

    public Shatterstrike() {
        super("Shatterstrike", 10.0, 40, 1, 1,
                SHATTERSTRIKE_TEXTURE_UNLOCKED, SHATTERSTRIKE_TEXTURE_UNLOCKABLE, SHATTERSTRIKE_TEXTURE_LOCKED);
    }

    @Override
    public List<String> getDescription(RPGPlayer rpgPlayer) {
        List<String> description = new ArrayList<>();
        description.add("&7STRIKE in a wide arc in front of you.");
        description.add("&7Deals &c60% ATK &7as &c" + RPGSymbols.SLASH_DAMAGE.content() + " Slash &7damage.");
        return description;
    }

    @Override
    public void castSkill(RPGPlayer rpgPlayer) {
        rpgPlayer.getPlayer().sendMessage(RPGSymbols.EARTH_DAMAGE);
    }
}
