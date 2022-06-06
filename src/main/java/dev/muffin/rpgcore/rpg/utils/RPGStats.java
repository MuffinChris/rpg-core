package dev.muffin.rpgcore.rpg.utils;

public class RPGStats {
    private double mana;
    private double armor;
    private double magicResist;

    public RPGStats(double mana, double armor, double magicResist) {
        this.mana = mana;
        this.armor = armor;
        this.magicResist = magicResist;
    }

    public double getMana() {
        return mana;
    }

    public void setMana(double mana) {
        this.mana = mana;
    }

    public double getArmor() {
        return armor;
    }

    public void setArmor(double armor) {
        this.armor = armor;
    }

    public double getMagicResist() {
        return magicResist;
    }

    public void setMagicResist(double magicResist) {
        this.magicResist = magicResist;
    }
}
