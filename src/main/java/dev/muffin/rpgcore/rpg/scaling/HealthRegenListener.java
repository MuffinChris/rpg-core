package dev.muffin.rpgcore.rpg.scaling;

import dev.muffin.rpgcore.Main;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import java.util.Objects;

import static dev.muffin.rpgcore.rpg.utils.constants.RPGConstants.HEALTH_SCALE;

public class HealthRegenListener implements Listener {

    @EventHandler
    public void onHealthRegen(EntityRegainHealthEvent e) {
        if (e.getEntity() instanceof Player p) {
            if (e.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED) {
                e.setAmount(0.5 * (e.getAmount() / HEALTH_SCALE) * Objects.requireNonNull(p.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getBaseValue());
            }

            Main.getInstance().getPlayerHealthDisplayer().updateHealthBar(p);
        }
    }

}
