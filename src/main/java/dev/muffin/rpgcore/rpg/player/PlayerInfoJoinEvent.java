package dev.muffin.rpgcore.rpg.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerInfoJoinEvent implements Listener {

    private PlayerInfoHandler playerInfoHandler;
    public PlayerInfoJoinEvent(PlayerInfoHandler playerInfoHandler) {
        this.playerInfoHandler = playerInfoHandler;
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent e) {
        playerInfoHandler.createPlayer(e.getPlayer());
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onQuit(PlayerQuitEvent e) {
        playerInfoHandler.removePlayer(e.getPlayer());
    }

}
