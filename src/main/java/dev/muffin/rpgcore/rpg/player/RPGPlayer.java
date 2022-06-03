package dev.muffin.rpgcore.rpg.player;

import dev.muffin.rpgcore.rpg.archetypes.Warrior;
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

    public RPGPlayer(Player p) {
        PluginLogger.getLogger().info("Creating RPGPlayer for " + p.getName() + ".");
        playerUUID = p.getUniqueId();
        playerClass = new PlayerClass(p, new Warrior(), 1, 0);
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
     * Update any gameplay related stats for the player
     */
    public void gameplayUpdate() {
        playerClass.updateStats();
    }

    /**
     * Safe-close of RPGPlayer (save data, etc)
     */
    public void close() {
        //TODO: close
    }

}
