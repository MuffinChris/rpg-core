package dev.muffin.rpgcore.rpg.utils;

public class RPGClassStats {

    public final double hp;
    public final double mana;
    public final double manaRegen;
    public final double hpPerLevel;
    public final double manaPerLevel;
    public final double manaRegenPerLevel;

    public RPGClassStats(double hp, double mana, double manaRegen, double hpPerLevel, double manaPerLevel, double manaRegenPerLevel) {
        this.hp = hp;
        this.mana = mana;
        this.manaRegen = manaRegen;
        this.hpPerLevel = hpPerLevel;
        this.manaPerLevel = manaPerLevel;
        this.manaRegenPerLevel = manaRegenPerLevel;
    }

}
