package dev.muffin.rpgcore.rpg.damage.holograms;

import dev.muffin.rpgcore.chat.utils.ComponentConverter;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

public class Hologram {

    private ArmorStand armorStand;
    private LivingEntity entityOrigin;
    private final int lifetime;
    private final HologramType hologramType;
    private int currentLifetime;

    public Hologram(String hologramText, LivingEntity entityOrigin, HologramType hologramType, int lifetime) {
        this.entityOrigin = entityOrigin;
        this.hologramType = hologramType;
        this.lifetime = lifetime;

        currentLifetime = 0;

        if (this.hologramType == HologramType.DAMAGE) {
            armorStand = (ArmorStand) entityOrigin.getWorld().spawnEntity(getDamageHologramSpawnLocation(), EntityType.ARMOR_STAND);
            armorStand.setVisible(false);
            armorStand.setGravity(false);
            armorStand.setInvulnerable(true);
            armorStand.setCanPickupItems(false);
            armorStand.setCanMove(false);

            armorStand.setCustomNameVisible(true);
            armorStand.customName(ComponentConverter.getComponentFromString(hologramText));
        }
    }

    public ArmorStand getArmorStand() {
        return armorStand;
    }

    public boolean tickHologram() {
        currentLifetime+=1;
        rise();

        if (currentLifetime >= lifetime) {
            armorStand.remove();
            entityOrigin = null;
            armorStand = null;
            return true;
        }
        return false;
    }

    private void rise() {
        armorStand.teleport(armorStand.getLocation().add(new Vector(0, 0.025, 0)));
    }

    private Location getDamageHologramSpawnLocation() {
        double neg = Math.random();
        double neg2 = Math.random();
        double mod = 0.1;
        double mod2 = 0.1;
        if (neg < 0.5) {
            neg = -0.2;
            mod*=-1;
        } else {
            neg = 0.2;
        }

        if (neg2 < 0.5) {
            neg2 = -0.2;
            mod2*=-1;
        } else {
            neg2 = 0.2;
        }

        return entityOrigin.getEyeLocation().clone().add(
                new Vector(
                        Math.random() * neg + mod,
                        -2,
                        Math.random() * neg2 + mod2
                ));

    }

}
