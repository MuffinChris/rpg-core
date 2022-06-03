package dev.muffin.rpgcore.rpg.player;

import dev.muffin.rpgcore.rpg.archetypes.Archetype;
import dev.muffin.rpgcore.rpg.classes.RPGClass;
import dev.muffin.rpgcore.rpg.rpgutils.RPGConstants;
import dev.muffin.rpgcore.rpg.rpgutils.RPGStats;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

import java.util.Objects;

/**
 * A PlayerClass stores a player's RPG info related to classes and archetypes
 */
public class PlayerClass {

    // Stats
    private RPGStats stats;

    // Class Numbers
    private int level;
    private double exp;


    // Class Info
    private Archetype archetype;
    private RPGClass rpgClass;

    private final Player player;

    public PlayerClass(Player player, Archetype archetype, int level, double exp) {
        this.player = player;
        this.archetype = archetype;
        this.level = level;
        this.exp = exp;
        stats = new RPGStats(getMaxMana(), 0, 0);

        player.setHealthScale(RPGConstants.HEALTH_SCALE);
    }

    public RPGStats getStats() {
        return stats;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getExp() {
        return exp;
    }

    /**
     * Get a player's maximum HP based on class, archetype, items, etc
     * @return max hp
     */
    public double getMaxHP() {
        return archetype.getStats().hp;
    }

    /**
     * Get a player's maximum Mana based on class, archetype, items, etc
     * @return max mana
     */
    public int getMaxMana() {
        return archetype.getStats().mana;
    }

    /**
     * Get a player's mana regen based on class, archetype, items, etc
     * @return mana regen
     */
    public int getManaRegen() {
        return archetype.getStats().manaRegen;
    }

    /**
     * Update attributes of a player based on their RPG stats
     */
    public void updateStats() {
        // Set health if necessary
        AttributeInstance hp = Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH));
        if (getMaxHP() != hp.getBaseValue()) {
            double previousHPPercent = player.getHealth() / hp.getBaseValue();
            hp.setBaseValue(getMaxHP());
            player.setHealth(Math.min(previousHPPercent * getMaxHP(), getMaxHP()));
        }

        // Increment mana based on regen
        stats.setMana(Math.min(stats.getMana() + getManaRegen(), getMaxMana()));
    }
}
