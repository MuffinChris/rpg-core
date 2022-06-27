package dev.muffin.rpgcore.rpg.damage;

import dev.muffin.rpgcore.Main;
import dev.muffin.rpgcore.rpg.damage.holograms.Hologram;
import dev.muffin.rpgcore.rpg.damage.holograms.HologramType;
import dev.muffin.rpgcore.rpg.utils.constants.RPGSymbols;
import dev.muffin.rpgcore.utilities.DecimalFormats;
import dev.muffin.rpgcore.utilities.PluginLogger;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DamageStack {
    private final List<DamageInstance> damageInstances;

    public DamageStack() {
        this.damageInstances = new ArrayList<>();
    }

    public void bufferDamage(DamageInstance damageInstance) {
        damageInstances.add(damageInstance);
    }

    public double processDamageForTarget(LivingEntity target, LivingEntity source) {
        double totalDamage = 0;

        List<DamageInstance> damageInstances = getDamageInstancesForTarget(target);
        for (int i = damageInstances.size() - 1; i >= 0; i--) {
            totalDamage+=processDamageInstance(damageInstances.get(i), target, source);
            this.damageInstances.remove(damageInstances.get(i));
        }
        return totalDamage;
    }

    private double processDamageInstance(DamageInstance damageInstance, LivingEntity target, LivingEntity source) {
        PluginLogger.getLogger().info("Damage Instance processed for " + target.getType() + " from source " + source.getType() + " for damage " + damageInstance.getTotalDamage());
        // handle resistances
        Main.getInstance().getHologramManager().addHologram(new Hologram(getHologramText(damageInstance), target, HologramType.DAMAGE, 40));
        return damageInstance.getTotalDamage();
    }

    private String getHologramText(DamageInstance damageInstance) {
        StringBuilder hologramText = new StringBuilder();

        if (damageInstance.getPhysicalDamageInstance().physical() > 0) {
            hologramText.append("&c").append(RPGSymbols.PHYSICAL_DAMAGE.content()).append(DecimalFormats.oneDecimalsZero.format(damageInstance.getPhysicalDamageInstance().physical())).append(" ");
        }
        if (damageInstance.getPhysicalDamageInstance().impact() > 0) {
            hologramText.append("&c").append(RPGSymbols.IMPACT_DAMAGE.content()).append(DecimalFormats.oneDecimalsZero.format(damageInstance.getPhysicalDamageInstance().impact())).append(" ");
        }
        if (damageInstance.getPhysicalDamageInstance().puncture() > 0) {
            hologramText.append("&c").append(RPGSymbols.PUNCTURE_DAMAGE.content()).append(DecimalFormats.oneDecimalsZero.format(damageInstance.getPhysicalDamageInstance().puncture())).append(" ");
        }
        if (damageInstance.getPhysicalDamageInstance().slash() > 0) {
            hologramText.append("&c").append(RPGSymbols.SLASH_DAMAGE.content()).append(DecimalFormats.oneDecimalsZero.format(damageInstance.getPhysicalDamageInstance().slash())).append(" ");
        }

        if (damageInstance.getMagicDamageInstance().magic() > 0) {
            hologramText.append("&b").append(RPGSymbols.ABILITY_DAMAGE.content()).append(DecimalFormats.oneDecimalsZero.format(damageInstance.getMagicDamageInstance().magic())).append(" ");
        }
        if (damageInstance.getMagicDamageInstance().air() > 0) {
            hologramText.append("&f").append(RPGSymbols.AIR_DAMAGE.content()).append(DecimalFormats.oneDecimalsZero.format(damageInstance.getMagicDamageInstance().air())).append(" ");
        }
        if (damageInstance.getMagicDamageInstance().earth() > 0) {
            hologramText.append("&2").append(RPGSymbols.EARTH_DAMAGE.content()).append(DecimalFormats.oneDecimalsZero.format(damageInstance.getMagicDamageInstance().earth())).append(" ");
        }
        if (damageInstance.getMagicDamageInstance().electric() > 0) {
            hologramText.append("&e").append(RPGSymbols.ELECTRIC_DAMAGE.content()).append(DecimalFormats.oneDecimalsZero.format(damageInstance.getMagicDamageInstance().electric())).append(" ");
        }
        if (damageInstance.getMagicDamageInstance().fire() > 0) {
            hologramText.append("&c").append(RPGSymbols.FIRE_DAMAGE.content()).append(DecimalFormats.oneDecimalsZero.format(damageInstance.getMagicDamageInstance().fire())).append(" ");
        }
        if (damageInstance.getMagicDamageInstance().ice() > 0) {
            hologramText.append("&b").append(RPGSymbols.ICE_DAMAGE.content()).append(DecimalFormats.oneDecimalsZero.format(damageInstance.getMagicDamageInstance().ice())).append(" ");
        }
        if (damageInstance.getMagicDamageInstance().water() > 0) {
            hologramText.append("&3").append(RPGSymbols.WATER_DAMAGE.content()).append(DecimalFormats.oneDecimalsZero.format(damageInstance.getMagicDamageInstance().water())).append(" ");
        }

        return hologramText.substring(0, hologramText.length() - 1);
    }

    public List<DamageInstance> getDamageInstancesForTarget(LivingEntity target) {
        List<DamageInstance> damageInstancesForTarget = new ArrayList<>();
        for (DamageInstance damageInstance : damageInstances) {
            if (damageInstance.getTarget() == target) {
                damageInstancesForTarget.add(damageInstance);
            }
        }
        return damageInstancesForTarget;
    }

    /*
    On damage, create a damage instance with the target.
    If the target matches along with the person doing the damage, that is a damage event. Should damage be compared to check validity?
    Dont know, that is old rift mechanism. Can perhaps instead combine damage instances. Maybe do some testing.
    Combining safest! Or not even combining but just displaying all individually, from the list!
     */

}
