package dev.muffin.rpgcore.rpg.scaling;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Objects;

import static dev.muffin.rpgcore.rpg.utils.RPGConstants.HEALTH_SCALE;

public class EnvironmentalDamageListener implements Listener {

    @EventHandler (priority = EventPriority.LOW)
    public void onEnvironmentalDamage (EntityDamageEvent e) {
        if (e.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) e.getEntity();
            double hp = Objects.requireNonNull(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getBaseValue();

            if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                e.setDamage((e.getDamage() / HEALTH_SCALE) * (hp * 0.75));
            } else if (e.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
                if (e.getEntity() instanceof Player) {
                    e.setDamage((e.getDamage() / HEALTH_SCALE) * (hp * 0.5));
                } else {
                    e.setDamage(hp * 0.2 + e.getDamage() * 6.0);
                }
            } else if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
                e.setDamage((e.getDamage() / HEALTH_SCALE) * (hp * 0.5));
            } else if (e.getCause() == EntityDamageEvent.DamageCause.CONTACT) {
                e.setDamage(hp * 0.05);
            } else if (e.getCause() == EntityDamageEvent.DamageCause.DROWNING) {
                e.setDamage((e.getDamage() / HEALTH_SCALE) * (hp * 0.5));
            } else if (e.getCause() == EntityDamageEvent.DamageCause.DRAGON_BREATH) {
                e.setDamage(100 + hp * 0.02);
            } else if (e.getCause() == EntityDamageEvent.DamageCause.DRYOUT) {
                e.setDamage((hp * 0.05));
            } else if (e.getCause() == EntityDamageEvent.DamageCause.CRAMMING) {
                e.setDamage(hp * 0.05);
            } else if (e.getCause() == EntityDamageEvent.DamageCause.FALLING_BLOCK) {
                e.setDamage(hp * 0.1);
            } else if (e.getCause() == EntityDamageEvent.DamageCause.FIRE) {
                e.setDamage(Math.max(hp * 0.025, 15));
            } else if (e.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) {
                e.setDamage(Math.max(hp * 0.025, 15));
            } else if (e.getCause() == EntityDamageEvent.DamageCause.FLY_INTO_WALL) {
                e.setDamage((e.getDamage() / HEALTH_SCALE) * (hp * 0.5));
            } else if (e.getCause() == EntityDamageEvent.DamageCause.FREEZE) {
                e.setDamage(hp * 0.05);
            } else if (e.getCause() == EntityDamageEvent.DamageCause.HOT_FLOOR) {
                e.setDamage(hp * 0.05);
            } else if (e.getCause() == EntityDamageEvent.DamageCause.LAVA) {
                e.setDamage(Math.max(hp * 0.075, 25));
            } else if (e.getCause() == EntityDamageEvent.DamageCause.LIGHTNING) {
                e.setDamage(hp * 0.25);
            } else if (e.getCause() == EntityDamageEvent.DamageCause.MELTING) {
                e.setDamage(hp * 0.1);
            } else if (e.getCause() == EntityDamageEvent.DamageCause.STARVATION) {
                e.setDamage(hp * 0.05);
            } else if (e.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION) {
                e.setDamage(hp * 0.075);
            } else if (e.getCause() == EntityDamageEvent.DamageCause.THORNS) {
                e.setDamage(hp * 0.005 + 3);
            } else if (e.getCause() == EntityDamageEvent.DamageCause.VOID) {
                e.setDamage(hp * 0.2);
            } else if (e.getCause() == EntityDamageEvent.DamageCause.WITHER) {
                e.setDamage(hp * 0.07);
            }
        }
    }

}
