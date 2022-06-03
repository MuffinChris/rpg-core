package dev.muffin.rpgcore.chat;

public class ChatColor {

    public static String color(String str) {
        return org.bukkit.ChatColor.translateAlternateColorCodes('&', str);
    }

}
