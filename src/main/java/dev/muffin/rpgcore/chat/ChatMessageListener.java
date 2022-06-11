package dev.muffin.rpgcore.chat;

import dev.muffin.rpgcore.chat.utils.CustomChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChatMessageListener implements Listener {

    private CustomChatRenderer customChatRenderer;

    public ChatMessageListener(CustomChatRenderer customChatRenderer) {
        this.customChatRenderer = customChatRenderer;
    }

    @EventHandler
    public void onChat (AsyncChatEvent e) {
        e.renderer(customChatRenderer);
    }

}
