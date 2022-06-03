package dev.muffin.rpgcore.rpg.player;

import dev.muffin.rpgcore.rpg.archetypes.Warrior;
import dev.muffin.rpgcore.rpg.skills.casting.SkillCaster;
import dev.muffin.rpgcore.rpg.skills.casting.Skillbar;
import dev.muffin.rpgcore.utilities.PluginLogger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * RPGPlayer is attached to each player. Stores all RPG information
 */
public class RPGPlayer {

    private UUID playerUUID;
    private PlayerClass playerClass;
    private Skillbar skillbar;
    private SkillCaster skillCaster;

    public RPGPlayer(Player p) {
        PluginLogger.getLogger().info("Creating RPGPlayer for " + p.getName() + ".");
        playerUUID = p.getUniqueId();
        playerClass = new PlayerClass(p, new Warrior(), 1, 0);
        skillbar = new Skillbar(p);
        skillCaster = new SkillCaster(playerUUID, playerClass);
    }

    /**
     * Get UUID of owner of the RPGPlayer
     * @return UUID of owner
     */
    public UUID getUUID() {
        return playerUUID;
    }

    /**
     * Get Player of the RPGPlayer
     * @return the player
     */
    public Player getPlayer() {
        return Bukkit.getPlayer(playerUUID);
    }

    /**
     * Get the playerClass object of the RPGPlayer
     * @return the playerClass
     */
    public PlayerClass getPlayerClass() {
        return playerClass;
    }

    /**
     * Get player's skillbar
     * @return skillbar
     */
    public Skillbar getSkillbar() {
        return skillbar;
    }

    /**
     * Get skill caster
     * @return skill caster
     */
    public SkillCaster getSkillCaster() {
        return skillCaster;
    }

    /**
     * Update any gameplay related stats for the player
     */
    public void updatePlayerInfo() {
        playerClass.updateStats();
    }

    /**
     * Enable and update the skillbar
     */
    public void enableSkillbar() {
        skillbar.enable();
        skillbar.updateSkillbar(getPlayerClass().getCastableSkills());
    }

    /**
     * Disable the skillbar
     */
    public void disableSkillbar() {
        skillbar.disable();
        skillbar.updateSkillbar(getPlayerClass().getCastableSkills());
    }

    /**
     * Safe-close of RPGPlayer (save data, etc)
     */
    public void close() {
        //TODO: close
    }

}
