package dev.muffin.rpgcore.rpg.damage;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {

    @EventHandler
    public void regularDamageEvent (EntityDamageByEntityEvent e) {
        // add to a damage instance
    }

}
