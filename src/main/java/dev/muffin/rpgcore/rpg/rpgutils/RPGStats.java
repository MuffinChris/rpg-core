package dev.muffin.rpgcore.rpg.rpgutils;

public class RPGStats {
    private int mana;
    private double armor;
    private double magicResist;

    public RPGStats(int mana, double armor, double magicResist) {
        this.mana = mana;
        this.armor = armor;
        this.magicResist = magicResist;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
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
