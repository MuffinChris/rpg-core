package dev.muffin.rpgcore.rpg.scaling;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Objects;

import static dev.muffin.rpgcore.rpg.rpgutils.RPGConstants.HEALTH_SCALE;

public class EnvironmentalDamageListener implements Listener {

    // 1/20 is 1:1 vanilla. 1/30 is slightly more generous
    private static final double FALL_DAMAGE = 0.75;

    @EventHandler (priority = EventPriority.LOW)
    public void onEnvironmentalDamage (EntityDamageEvent e) {
        if (e.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) e.getEntity();
            double hp = Objects.requireNonNull(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getBaseValue();

            if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                e.setDamage((e.getDamage() / HEALTH_SCALE) * (hp * FALL_DAMAGE));
            }

        }
    }

}
