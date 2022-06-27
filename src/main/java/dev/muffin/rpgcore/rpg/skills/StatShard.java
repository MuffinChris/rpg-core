package dev.muffin.rpgcore.rpg.skills;

import dev.muffin.rpgcore.Main;
import dev.muffin.rpgcore.rpg.player.RPGPlayer;
import dev.muffin.rpgcore.rpg.skills.abstracts.Unlockable;
import dev.muffin.rpgcore.rpg.utils.RPGStatShard;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class StatShard extends Unlockable {

    private final RPGStatShard statChanges;
    private final int offTexture;
    private final int onTexture;

    public StatShard(String name, int skillpointCost, RPGStatShard statChanges, int offTexture, int onTexture) {
        super(name, skillpointCost);
        this.statChanges = statChanges;
        this.offTexture = offTexture;
        this.onTexture = onTexture;
    }

    public RPGStatShard getStatChanges() {
        return statChanges;
    }

    public int getTexture(RPGPlayer rpgPlayer) {
        if (rpgPlayer.getPlayerClass().hasStatShard(this)) {
            return onTexture;
        } else {
            return offTexture;
        }
    }

    @Override
    public List<String> getDescription(RPGPlayer rpgPlayer) {
        List<String> description = new ArrayList<>();
        description.add("");
        description.add("&7Changes stats by the following:");
        description.add(statChanges.toString());
        description.add("");
        if (rpgPlayer.getPlayerClass().hasStatShard(this)) {
            description.add("&aStat Shard Unlocked");
        } else {
            description.add("&7Skillpoint Cost: &e" + getSkillpointCost());
        }
        return description;
    }
}
