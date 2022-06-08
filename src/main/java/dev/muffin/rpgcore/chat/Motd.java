package dev.muffin.rpgcore.chat;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class Motd implements Listener {

    @EventHandler
    public void motd(ServerListPingEvent e) {
        e.motd(Component.text("emuffin.dev rpg server", NamedTextColor.GOLD));
    }

}
