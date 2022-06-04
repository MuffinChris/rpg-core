package dev.muffin.rpgcore;

import dev.muffin.rpgcore.chat.CustomChatRenderer;
import dev.muffin.rpgcore.chat.ChatMessageListener;
import dev.muffin.rpgcore.chat.JoinMessageListener;
import dev.muffin.rpgcore.rpg.scaling.EnvironmentalDamageListener;
import dev.muffin.rpgcore.rpg.player.PlayerHealthDisplayer;
import dev.muffin.rpgcore.rpg.player.PlayerInfoHandler;
import dev.muffin.rpgcore.rpg.player.PlayerInfoJoinEvent;
import dev.muffin.rpgcore.rpg.player.RPGPlayer;
import dev.muffin.rpgcore.rpg.scaling.HealthRegenListener;
import dev.muffin.rpgcore.rpg.skills.casting.CastingRunnables;
import dev.muffin.rpgcore.rpg.skills.casting.SkillbarListener;
import dev.muffin.rpgcore.utilities.PluginLogger;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private PlayerInfoHandler playerInfoHandler;
    private PlayerHealthDisplayer playerHealthDisplayer;

    @Override
    public void onEnable() {
        getLogger().info("rpgcore loading...");

        getLogger().info("setting logger singleton");
        PluginLogger.setLogger(getLogger());

        getLogger().info("registering chat events");
        getServer().getPluginManager().registerEvents(new JoinMessageListener(), this);
        getServer().getPluginManager().registerEvents(new ChatMessageListener(new CustomChatRenderer()), this);

        getLogger().info("registering rpg player events");
        playerInfoHandler = new PlayerInfoHandler(this);
        getServer().getPluginManager().registerEvents(new PlayerInfoJoinEvent(playerInfoHandler), this);
        playerHealthDisplayer = new PlayerHealthDisplayer(this);
        getServer().getPluginManager().registerEvents(playerHealthDisplayer, this);

        getLogger().info("registering rpg damage events");
        getServer().getPluginManager().registerEvents(new EnvironmentalDamageListener(), this);
        getServer().getPluginManager().registerEvents(new HealthRegenListener(this), this);

        getLogger().info("registering rpg skill events");
        getServer().getPluginManager().registerEvents(new SkillbarListener(this), this);
        new CastingRunnables(this);
    }

    @Override
    public void onDisable() {
        getLogger().info("rpgcore unloading...");

        getLogger().info("closing all RPGPlayers");
        playerInfoHandler.removePlayers();
    }

    // Helper Methods
    public RPGPlayer getRPGPlayer(Player p) {
        return playerInfoHandler.getRpgPlayerMap().get(p);
    }

    public PlayerHealthDisplayer getPlayerHealthDisplayer() {
        return playerHealthDisplayer;
    }
}
