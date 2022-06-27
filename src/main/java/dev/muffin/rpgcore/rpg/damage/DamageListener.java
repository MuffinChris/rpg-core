package dev.muffin.rpgcore.rpg.damage;

import dev.muffin.rpgcore.Main;
import dev.muffin.rpgcore.rpg.player.RPGPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {
    @EventHandler (priority = EventPriority.HIGHEST)
    public void handleDamageEvent (EntityDamageByEntityEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (e.getDamager() instanceof Player damager && e.getEntity() instanceof LivingEntity target) {
            RPGPlayer rpgPlayer = Main.getInstance().getRPGPlayer(damager);
            if (rpgPlayer == null) {
                return;
            }

            double damage = rpgPlayer.getDamageStack().processDamageForTarget(target, damager);

            e.setDamage(damage);
        }
    }

    @EventHandler (priority = EventPriority.LOW)
    public void normalDamageEvent (EntityDamageByEntityEvent e) {
        if (!(e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK
                || e.getCause() == EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK)) {
            return;
        }

        if (e.isCancelled()) {
            return;
        }

        if (e.getDamager() instanceof Player damager && e.getEntity() instanceof LivingEntity target) {
            RPGPlayer rpgPlayer = Main.getInstance().getRPGPlayer(damager);
            if (rpgPlayer == null) {
                return;
            }

            // Block repeated damage events off this event. Unlikely to be wrongly triggered? needs testing
            // Can test by calling back to back damage events.
            if (!rpgPlayer.getDamageStack().getDamageInstancesForTarget(target).isEmpty()) {
                return;
            }

            rpgPlayer.bufferBasicAttack(new DamageInstance(target,
                    new PhysicalDamageInstance(e.getDamage(), 0, 0, 0),
                    new MagicDamageInstance(0, 0, 0, 0, 0, 0, 0),
                    true));
        }
    }

}
