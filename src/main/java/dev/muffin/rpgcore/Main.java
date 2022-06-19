package dev.muffin.rpgcore;

import dev.muffin.rpgcore.chat.utils.CustomChatRenderer;
import dev.muffin.rpgcore.chat.ChatMessageListener;
import dev.muffin.rpgcore.chat.JoinMessageListener;
import dev.muffin.rpgcore.chat.Motd;
import dev.muffin.rpgcore.rpg.classes.ClassHandler;
import dev.muffin.rpgcore.rpg.player.*;
import dev.muffin.rpgcore.rpg.player.commands.GiveExpCommand;
import dev.muffin.rpgcore.rpg.player.handlers.PlayerHealthDisplayer;
import dev.muffin.rpgcore.rpg.player.handlers.PlayerInfoJoinEvent;
import dev.muffin.rpgcore.rpg.player.handlers.LevelBarHandler;
import dev.muffin.rpgcore.rpg.player.handlers.PlayerInfoHandler;
import dev.muffin.rpgcore.rpg.player.handlers.PlayerRespawnHandler;
import dev.muffin.rpgcore.rpg.scaling.EnvironmentalDamageListener;
import dev.muffin.rpgcore.rpg.scaling.HealthRegenListener;
import dev.muffin.rpgcore.rpg.skills.skilltree.SkillTreeHandler;
import dev.muffin.rpgcore.rpg.skills.skillgui.SkillsGUIHandler;
import dev.muffin.rpgcore.rpg.skills.casting.CastingRunnables;
import dev.muffin.rpgcore.rpg.skills.casting.SkillbarListener;
import dev.muffin.rpgcore.utilities.PluginLogger;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Main extends JavaPlugin {


    private static Main main;
    public static Main getInstance() {
        return main;
    }

    private PlayerInfoHandler playerInfoHandler;
    private PlayerHealthDisplayer playerHealthDisplayer;
    private ClassHandler classHandler;

    @Override
    public void onEnable() {
        getLogger().info("rpgcore loading...");
        getLogger().info("setting main singleton");
        main = this;

        getLogger().info("setting logger singleton");
        PluginLogger.setLogger(getLogger());

        getLogger().info("registering chat events");
        getServer().getPluginManager().registerEvents(new JoinMessageListener(), this);
        getServer().getPluginManager().registerEvents(new ChatMessageListener(new CustomChatRenderer()), this);
        getServer().getPluginManager().registerEvents(new Motd(), this);

        getLogger().info("registering rpg player events");
        playerInfoHandler = new PlayerInfoHandler(this);
        getServer().getPluginManager().registerEvents(new PlayerInfoJoinEvent(playerInfoHandler), this);
        playerHealthDisplayer = new PlayerHealthDisplayer();
        getServer().getPluginManager().registerEvents(playerHealthDisplayer, this);
        getServer().getPluginManager().registerEvents(new PlayerRespawnHandler(), this);
        getServer().getPluginManager().registerEvents(new LevelBarHandler(), this);

        getLogger().info("registering rpg damage events");
        getServer().getPluginManager().registerEvents(new EnvironmentalDamageListener(), this);
        getServer().getPluginManager().registerEvents(new HealthRegenListener(), this);

        getLogger().info("registering rpg class events");
        classHandler = new ClassHandler();

        getLogger().info("registering rpg skill events");
        getServer().getPluginManager().registerEvents(new SkillbarListener(), this);
        new CastingRunnables();
        SkillTreeHandler skillTreeHandler = new SkillTreeHandler();
        getServer().getPluginManager().registerEvents(skillTreeHandler, this);
        SkillsGUIHandler skillsGUIHandler = new SkillsGUIHandler();
        getServer().getPluginManager().registerEvents(skillsGUIHandler, this);

        getLogger().info("registering commands");
        this.getCommand("giveexp").setExecutor(new GiveExpCommand());
        this.getCommand("skilltree").setExecutor(skillTreeHandler);
        this.getCommand("skills").setExecutor(skillsGUIHandler);
    }

    @Override
    public void onDisable() {
        getLogger().info("rpgcore unloading...");

        getLogger().info("closing all RPGPlayers");
        playerInfoHandler.removePlayers();
    }

    // Helper Methods
    public RPGPlayer getRPGPlayer(Player p) {
        if (!playerInfoHandler.getRpgPlayerMap().containsKey(p)) {
            return null;
        }
        return playerInfoHandler.getRpgPlayerMap().get(p);
    }

    public PlayerHealthDisplayer getPlayerHealthDisplayer() {
        return playerHealthDisplayer;
    }

    public ClassHandler getClassHandler() {
        return classHandler;
    }

    public List<RPGPlayer> getAllRPGPlayers() {
        List<RPGPlayer> rpgPlayers = new ArrayList<>();
        for (Player p : playerInfoHandler.getRpgPlayerMap().keySet()) {
            rpgPlayers.add(playerInfoHandler.getRpgPlayerMap().get(p));
        }
        return rpgPlayers;
    }
}
