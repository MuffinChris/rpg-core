package dev.muffin.rpgcore.rpg.damage;

import org.bukkit.entity.LivingEntity;

public class DamageInstance {

    private final LivingEntity target;
    private final PhysicalDamageInstance physicalDamageInstance;
    private final MagicDamageInstance magicDamageInstance;
    private final boolean knockback;

    public DamageInstance(LivingEntity target, PhysicalDamageInstance physicalDamageInstance, MagicDamageInstance magicDamageInstance, boolean knockback) {
        this.target = target;
        this.physicalDamageInstance = physicalDamageInstance;
        this.magicDamageInstance = magicDamageInstance;
        this.knockback = knockback;
    }

    public DamageInstance(LivingEntity target, PhysicalDamageInstance physicalDamageInstance, boolean knockback) {
        this.target = target;
        this.physicalDamageInstance = physicalDamageInstance;
        this.knockback = knockback;
        this.magicDamageInstance = new MagicDamageInstance(0, 0, 0, 0, 0, 0, 0);
    }

    public DamageInstance(LivingEntity target, MagicDamageInstance magicDamageInstance, boolean knockback) {
        this.target = target;
        this.magicDamageInstance = magicDamageInstance;
        this.knockback = knockback;
        this.physicalDamageInstance = new PhysicalDamageInstance(0, 0, 0, 0);
    }

    public double getTotalDamage() {
        return physicalDamageInstance.getTotalDamage() + magicDamageInstance.getTotalDamage();
    }

    public LivingEntity getTarget() {
        return target;
    }

    public boolean isKnockback() {
        return knockback;
    }

    public PhysicalDamageInstance getPhysicalDamageInstance() {
        return physicalDamageInstance;
    }

    public MagicDamageInstance getMagicDamageInstance() {
        return magicDamageInstance;
    }
}
