package dev.muffin.rpgcore.chat.utils;

import dev.muffin.rpgcore.Main;
import dev.muffin.rpgcore.rpg.archetypes.Archetype;
import io.papermc.paper.chat.ChatRenderer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CustomChatRenderer implements ChatRenderer {

    @Override
    public @NotNull Component render(@NotNull Player source, @NotNull Component sourceDisplayName, @NotNull Component message, @NotNull Audience viewer) {

        int level = Main.getInstance().getRPGPlayer(source).getPlayerClass().getRpgInfo().getLevel();
        Archetype type = Main.getInstance().getRPGPlayer(source).getPlayerClass().getArchetype();
        HoverEvent<Component> hoverEventLevel = HoverEvent.showText(Component.text().content("Level: ").color(NamedTextColor.YELLOW)
                .append(Component.text().content(String.valueOf(level)).color(NamedTextColor.WHITE))
                .append(Component.text().content("\nClass: ").color(NamedTextColor.YELLOW))
                .append(Component.text().content(type.getName()).color(NamedTextColor.WHITE)).build());

        return Component.text().content(source.getName())
                .color(NamedTextColor.WHITE).hoverEvent(hoverEventLevel)
                .append(Component.text().content(": ")
                .color(NamedTextColor.DARK_GRAY))
                .append(message
                .color(NamedTextColor.GRAY))
                .build();
    }
}
