package dev.muffin.rpgcore.rpg.player;

import dev.muffin.rpgcore.rpg.archetypes.Archetype;
import dev.muffin.rpgcore.rpg.classes.RPGClass;
import dev.muffin.rpgcore.rpg.skills.SkillTree;
import dev.muffin.rpgcore.rpg.skills.SkillsGUI;
import dev.muffin.rpgcore.rpg.utils.RPGLevelInfo;
import dev.muffin.rpgcore.rpg.utils.RPGStats;
import dev.muffin.rpgcore.rpg.skills.Skill;
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
    private RPGStats stats;
    private final RPGLevelInfo rpgInfo;

    // Class Info
    private RPGClass rpgClass;
    private Archetype archetype;

    private final Player player;

    public PlayerClass(Player player, RPGClass rpgClass, RPGLevelInfo rpgInfo) {
        this.player = player;
        this.rpgClass = rpgClass;
        this.rpgInfo = rpgInfo;
        stats = new RPGStats(0, 0, 0);

        player.setHealthScale(HEALTH_SCALE);
        updateStats();
    }

    public RPGStats getStats() {
        return stats;
    }

    public RPGLevelInfo getRpgInfo() {
        return rpgInfo;
    }

    /**
     * Get a player's maximum HP based on class, archetype, items, etc
     * @return max hp
     */
    public double getMaxHP() {
        return rpgClass.getStats().hp + rpgClass.getStats().hpPerLevel * rpgInfo.getLevel();
    }

    /**
     * Get a player's maximum Mana based on class, archetype, items, etc
     * @return max mana
     */
    public double getMaxMana() {
        return rpgClass.getStats().mana + rpgClass.getStats().manaPerLevel * rpgInfo.getLevel();
    }

    /**
     * Get a player's mana regen based on class, archetype, items, etc
     * @return mana regen
     */
    public double getManaRegen() {
        return rpgClass.getStats().manaRegen + rpgClass.getStats().manaRegenPerLevel * rpgInfo.getLevel();
    }

    public RPGClass getRpgClass() {
        return rpgClass;
    }

    /**
     * Healer a player to their max health and restore their mana
     */
    public void fullHeal() {
        if (!player.isDead()) {
            AttributeInstance hp = Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH));
            player.setHealth(hp.getBaseValue());
            stats.setMana(getMaxMana());
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
            stats.setMana(Math.min(stats.getMana() + getManaRegen(), getMaxMana()));
        }
    }
}
