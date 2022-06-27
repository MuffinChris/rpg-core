package dev.muffin.rpgcore.rpg.damage;

public record MagicDamageInstance(double magic, double air, double earth, double electric, double fire, double ice, double water) {
    public double getTotalDamage() {
        return magic + air + earth + electric + fire + ice + water;
    }
}
