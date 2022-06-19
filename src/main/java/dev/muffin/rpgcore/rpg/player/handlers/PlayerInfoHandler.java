package dev.muffin.rpgcore.rpg.player.handlers;

import dev.muffin.rpgcore.rpg.player.RPGPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class PlayerInfoHandler {

    private Map<Player, RPGPlayer> rpgPlayerMap;

    public PlayerInfoHandler(Plugin plugin) {
        rpgPlayerMap = new HashMap<>();

        plugin.getLogger().info("starting update runnable");
        new BukkitRunnable() {
            public void run() {
                updateAll();
            }
        }.runTaskTimer(plugin, 1L, 5L);

        new BukkitRunnable() {
            public void run() {
                manaRegen();
            }
        }.runTaskTimer(plugin, 1L, 20L);
    }

    public Map<Player, RPGPlayer> getRpgPlayerMap() {
        return rpgPlayerMap;
    }

    /**
     * Instantiate a player's RPGPlayer
     * @param p the player
     */
    public void createPlayer(Player p) {
        RPGPlayer rpgPlayer = new RPGPlayer(p);
        rpgPlayerMap.put(p, rpgPlayer);
    }

    /**
     * Remove a player's RPGPlayer before logoff
     */
    public void removePlayer(Player p) {
        if (rpgPlayerMap.containsKey(p)) {
            rpgPlayerMap.get(p).close();
            rpgPlayerMap.remove(p);
        }
    }

    /**
     * Force closure of all RPGPlayers for all online players
     */
    public void removePlayers() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            removePlayer(p);
        }
    }

    /**
     * Update all RPGPlayers
     */
    public void updateAll() {
        for (Player p : rpgPlayerMap.keySet()) {
            rpgPlayerMap.get(p).updatePlayerInfo();
        }
    }

    public void manaRegen() {
        for (Player p : rpgPlayerMap.keySet()) {
            rpgPlayerMap.get(p).getPlayerClass().updateMana();
        }
    }

}
