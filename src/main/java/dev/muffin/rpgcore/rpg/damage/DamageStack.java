package dev.muffin.rpgcore.rpg.damage;

import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class DamageStack {
    private final List<DamageInstance> damageInstances;

    public DamageStack() {
        this.damageInstances = new ArrayList<>();
    }

    public void bufferDamage(DamageInstance damageInstance) {
        damageInstances.add(damageInstance);
    }
    public List<DamageInstance> getDamageInstancesForTarget(Entity e) {
        List<DamageInstance> damageInstancesForTarget = new ArrayList<>();
        for (DamageInstance damageInstance : damageInstances) {
            if (damageInstance.target() == e) {
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
