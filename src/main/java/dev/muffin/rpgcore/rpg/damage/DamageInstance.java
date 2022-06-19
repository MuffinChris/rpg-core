package dev.muffin.rpgcore.rpg.damage;

public class DamageInstance {

    private final PhysicalDamageInstance physicalDamageInstance;
    private final MagicDamageInstance magicDamageInstance;


    public DamageInstance(PhysicalDamageInstance physicalDamageInstance, MagicDamageInstance magicDamageInstance) {
        this.physicalDamageInstance = physicalDamageInstance;
        this.magicDamageInstance = magicDamageInstance;
    }

    public PhysicalDamageInstance getPhysicalDamageInstance() {
        return physicalDamageInstance;
    }
    public MagicDamageInstance getMagicDamageInstance() {
        return magicDamageInstance;
    }

}
