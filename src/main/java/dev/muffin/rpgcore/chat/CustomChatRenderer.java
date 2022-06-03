package dev.muffin.rpgcore.chat;

import io.papermc.paper.chat.ChatRenderer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CustomChatRenderer implements ChatRenderer {
    @Override
    public @NotNull Component render(@NotNull Player source, @NotNull Component sourceDisplayName, @NotNull Component message, @NotNull Audience viewer) {

        return Component.text().content(source.getName())
                .color(NamedTextColor.WHITE)
                .append(Component.text().content(": ")
                .color(NamedTextColor.DARK_GRAY))
                .append(message
                .color(NamedTextColor.GRAY))
                .build();
    }
}
