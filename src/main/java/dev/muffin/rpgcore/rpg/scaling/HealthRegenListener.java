package dev.muffin.rpgcore.rpg.scaling;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class HealthRegenListener implements Listener {

    @EventHandler
    public void onHealthRegen(EntityRegainHealthEvent e) {
        if (e.getEntity() instanceof Player p) {
            if (e.getRegainReason() == EntityRegainHealthEvent.RegainReason.EATING) {

            }
        }
    }

}
