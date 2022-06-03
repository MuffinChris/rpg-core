package dev.muffin.rpgcore.rpg.rpgutils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;

public class RPGSymbols {

    // Generic Symbols
    public static final TextComponent HEART_SYMBOL = Component.text().content("❤").color(NamedTextColor.RED).build();
    public static final TextComponent MANA_SYMBOL = Component.text().content("✦").color(NamedTextColor.AQUA).build();

    // Generic Damage Symbols (rarely used)
    public static final TextComponent PHYSICAL_DAMAGE = HEART_SYMBOL;
    public static final TextComponent ABILITY_DAMAGE = MANA_SYMBOL;
    public static final TextComponent TRUE_DAMAGE = Component.text().content("♦").color(NamedTextColor.LIGHT_PURPLE).build();

    // Elemental Damage Symbols
    public static final TextComponent AIR_DAMAGE = Component.text().content("✸").color(NamedTextColor.WHITE).build();
    public static final TextComponent EARTH_DAMAGE = Component.text().content("❈").color(NamedTextColor.DARK_GREEN).build();
    public static final TextComponent ELECTRIC_DAMAGE = Component.text().content("⚡").color(NamedTextColor.YELLOW).build();
    public static final TextComponent FIRE_DAMAGE = Component.text().content("✴").color(NamedTextColor.RED).build();
    public static final TextComponent ICE_DAMAGE = Component.text().content("❆").color(NamedTextColor.AQUA).build();
    public static final TextComponent WATER_DAMAGE = Component.text().content("✾").color(NamedTextColor.DARK_AQUA).build();

    // Physical Damage Symbols
    public static final TextComponent SLASH_DAMAGE = Component.text().content("⒜").color(NamedTextColor.RED).build();
    public static final TextComponent PUNCTURE_DAMAGE = Component.text().content("⒝").color(NamedTextColor.RED).build();
    public static final TextComponent IMPACT_DAMAGE = Component.text().content("⒞").color(NamedTextColor.RED).build();

    // Info Symbols
    public static final TextComponent ATTACK_DAMAGE = Component.text().content("⒠").color(NamedTextColor.RED).build();
    public static final TextComponent ABILITY_POWER = Component.text().content("⒟").color(NamedTextColor.AQUA).build();
    public static final TextComponent MANA_REGEN = Component.text().content("⚙").color(NamedTextColor.AQUA).build();

}
