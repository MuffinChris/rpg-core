package dev.muffin.rpgcore.rpg.skills.abstracts;

import dev.muffin.rpgcore.rpg.player.RPGPlayer;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class Unlockable {

    private final String name;
    private final int skillpointCost;

    public Unlockable(String name, int skillpointCost) {
        this.name = name;
        this.skillpointCost = skillpointCost;
    }

    public int getSkillpointCost() {
        return skillpointCost;
    }

    public abstract List<String> getDescription(RPGPlayer rpgPlayer);

    public String getName() {
        return name;
    }

}
