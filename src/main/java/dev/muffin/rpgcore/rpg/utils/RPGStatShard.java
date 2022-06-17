package dev.muffin.rpgcore.rpg.utils;

import dev.muffin.rpgcore.rpg.utils.constants.RPGSymbols;
import dev.muffin.rpgcore.utilities.DecimalFormats;

public record RPGStatShard(double hp, double mana, double manaRegen, double armor, double magicResist) {

    public String toString() {
        return "&8<&c" + RPGSymbols.HEART_SYMBOL.content() + DecimalFormats.oneDecimals.format(hp)
                + "&8|&b" + RPGSymbols.MANA_SYMBOL.content() + DecimalFormats.oneDecimals.format(mana)
                + "&8|&b" + RPGSymbols.MANA_REGEN.content() + DecimalFormats.oneDecimals.format(manaRegen)
                + "&8|&6" + RPGSymbols.ARMOR_SYMBOL.content() + DecimalFormats.oneDecimals.format(armor)
                + "&8|&9" + RPGSymbols.MAGIC_RESIST_SYMBOL.content() + DecimalFormats.oneDecimals.format(magicResist)
                + "&8>";
    }

}
