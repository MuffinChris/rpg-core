package dev.muffin.rpgcore.rpg.damage;

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
        return damageInstance.getTotalDamage();
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
