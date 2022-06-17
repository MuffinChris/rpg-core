package dev.muffin.rpgcore.rpg.skills;

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

    public abstract List<String> getDescription(Player player);

    public String getName() {
        return name;
    }

}
