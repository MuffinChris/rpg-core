package dev.muffin.rpgcore.rpg.damage;

public record PhysicalDamageInstance(double physical, double impact, double puncture, double slash) {
    public double getTotalDamage() {
        return physical + impact + puncture + slash;
    }
}
