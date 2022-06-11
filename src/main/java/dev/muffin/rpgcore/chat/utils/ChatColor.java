package dev.muffin.rpgcore.chat.utils;

public class ChatColor {

    public static String color(String str) {
        return org.bukkit.ChatColor.translateAlternateColorCodes('&', str);
    }

}
