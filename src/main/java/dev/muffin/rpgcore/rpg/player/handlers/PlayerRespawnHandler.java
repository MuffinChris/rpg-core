package dev.muffin.rpgcore.rpg.player.handlers;

import dev.muffin.rpgcore.Main;
import dev.muffin.rpgcore.rpg.player.RPGPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnHandler implements Listener {

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        RPGPlayer rpgPlayer = Main.getInstance().getRPGPlayer(e.getPlayer());
        rpgPlayer.getPlayerClass().getCurrentStats().setMana(0);
    }

}
