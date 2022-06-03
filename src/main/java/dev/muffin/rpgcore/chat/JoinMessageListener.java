package dev.muffin.rpgcore.chat;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinMessageListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Component joinMessage = Component
                .text().content("    ▶  ").color(NamedTextColor.GREEN)
                .append(Component.text().content(e.getPlayer().getName()).color(NamedTextColor.GRAY))
                .build();

        e.joinMessage(joinMessage);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Component quitMessage = Component
                .text().content("    ◀  ").color(NamedTextColor.RED)
                .append(Component.text().content(e.getPlayer().getName()).color(NamedTextColor.GRAY))
                .build();

        e.quitMessage(quitMessage);
    }

}
