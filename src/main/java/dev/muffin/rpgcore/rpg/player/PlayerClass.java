package dev.muffin.rpgcore.rpg.player;

import dev.muffin.rpgcore.rpg.archetypes.Archetype;
import dev.muffin.rpgcore.rpg.classes.RPGClass;
import dev.muffin.rpgcore.rpg.skills.StatShard;
import dev.muffin.rpgcore.rpg.utils.RPGLevelInfo;
import dev.muffin.rpgcore.rpg.utils.RPGStats;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static dev.muffin.rpgcore.rpg.utils.constants.RPGConstants.HEALTH_SCALE;

/**
 * A PlayerClass stores a player's RPG info related to classes and archetypes
 */
public class PlayerClass {

    // Stats
    private final RPGStats currentStats;
    private final List<StatShard> rpgStatShards;
    private final RPGLevelInfo rpgInfo;

    // Class Info
    private RPGClass rpgClass;
    private Archetype archetype;

    private final Player player;

    public PlayerClass(Player player, RPGClass rpgClass, RPGLevelInfo rpgInfo) {
        this.player = player;
        this.rpgClass = rpgClass;
        this.rpgInfo = rpgInfo;
        currentStats = new RPGStats(0, 0, 0);

        rpgStatShards = new ArrayList<>();

        player.setHealthScale(HEALTH_SCALE);
        updateStats();
    }

    public RPGStats getCurrentStats() {
        return currentStats;
    }

    public RPGLevelInfo getRpgInfo() {
        return rpgInfo;
    }

    /**
     * Get a player's maximum HP based on class, archetype, items, etc
     * @return max hp
     */
    public double getMaxHP() {
        double addHp = 0;
        for (StatShard shard : rpgStatShards) {
            addHp+=shard.getStatChanges().hp();
        }
        return rpgClass.getStats().hp + rpgClass.getStats().hpPerLevel * rpgInfo.getLevel() + addHp;
    }

    /**
     * Get a player's maximum Mana based on class, archetype, items, etc
     * @return max mana
     */
    public double getMaxMana() {
        double addMana = 0;
        for (StatShard shard : rpgStatShards) {
            addMana+=shard.getStatChanges().mana();
        }
        return rpgClass.getStats().mana + rpgClass.getStats().manaPerLevel * rpgInfo.getLevel() + addMana;
    }

    /**
     * Get a player's mana regen based on class, archetype, items, etc
     * @return mana regen
     */
    public double getManaRegen() {
        double addManaRegen = 0;
        for (StatShard shard : rpgStatShards) {
            addManaRegen+=shard.getStatChanges().manaRegen();
        }
        return rpgClass.getStats().manaRegen + rpgClass.getStats().manaRegenPerLevel * rpgInfo.getLevel() + addManaRegen;
    }

    public RPGClass getRpgClass() {
        return rpgClass;
    }

    public void addRPGStats(StatShard shard) {
        rpgStatShards.add(shard);
    }

    public void removeRPGStats(StatShard shard) {
        rpgStatShards.remove(shard);
    }

    public boolean hasStatShard(StatShard shard) {
        return rpgStatShards.contains(shard);
    }

    /**
     * Healer a player to their max health and restore their mana
     */
    public void fullHeal() {
        if (!player.isDead()) {
            AttributeInstance hp = Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH));
            player.setHealth(hp.getBaseValue());
            currentStats.setMana(getMaxMana());
        }
    }

    /**
     * Update attributes of a player based on their RPG stats
     */
    public void updateStats() {
        if (!player.isDead()) {
            AttributeInstance hp = Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH));
            if (getMaxHP() != hp.getBaseValue()) {
                double previousHPPercent = player.getHealth() / hp.getBaseValue();
                hp.setBaseValue(getMaxHP());
                player.setHealth(Math.min(previousHPPercent * getMaxHP(), getMaxHP()));
            }
        }
    }

    public void updateMana() {
        if (!player.isDead()) {
            currentStats.setMana(Math.min(currentStats.getMana() + getManaRegen(), getMaxMana()));
        }
    }
}
