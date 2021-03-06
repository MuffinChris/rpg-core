package dev.muffin.rpgcore.rpg.player.handlers;

import dev.muffin.rpgcore.Main;
import dev.muffin.rpgcore.rpg.player.RPGPlayer;
import dev.muffin.rpgcore.rpg.utils.constants.RPGSymbols;
import dev.muffin.rpgcore.utilities.DecimalFormats;
import dev.muffin.rpgcore.utilities.DirectionParser;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.util.Objects;

import static dev.muffin.rpgcore.rpg.utils.constants.RPGSymbols.HEART_SYMBOL;

public class PlayerHealthDisplayer implements Listener {

    private final Scoreboard healthUnderName;

    public PlayerHealthDisplayer() {

        healthUnderName = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
        Objective health = healthUnderName.registerNewObjective("health", Criterias.HEALTH, HEART_SYMBOL);
        health.setDisplaySlot(DisplaySlot.BELOW_NAME);

        new BukkitRunnable() {
            public void run() {
                for (RPGPlayer rpgPlayer : Main.getInstance().getAllRPGPlayers()) {
                    updateHealthBar(rpgPlayer);
                }
            }
        }.runTaskTimer(Main.getInstance(), 1L, 2L);
    }

    @EventHandler
    public void addToHealthUnderName(PlayerJoinEvent e) {
        e.getPlayer().setScoreboard(healthUnderName);
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void updateHealthOnDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player p) {
            updateHealthBar(Objects.requireNonNull(Main.getInstance().getRPGPlayer(p)));
        }
    }

    /**
     * Update a player's action bar with relevant information
     * @param p the player
     */
    public void updateHealthBar(RPGPlayer rpgPlayer) {
        String hp = DecimalFormats.noDecimals.format(rpgPlayer.getPlayer().getHealth());
        String mana = DecimalFormats.noDecimals.format(rpgPlayer.getPlayerClass().getCurrentStats().getMana());

        String x = DecimalFormats.noDecimals.format(rpgPlayer.getPlayer().getLocation().getX());
        String direction = DirectionParser.yawToString(rpgPlayer.getPlayer().getLocation().getYaw());
        String z = DecimalFormats.noDecimals.format(rpgPlayer.getPlayer().getLocation().getZ());

        Component actionBarMessage = Component
                .text().append(HEART_SYMBOL)
                .append(Component.text().content(" " + hp + "    ").color(NamedTextColor.RED))
                .append(Component.text().content(x).color(NamedTextColor.GRAY))
                .append(Component.text().content(" " + direction + " ").color(NamedTextColor.WHITE))
                .append(Component.text().content(z).color(NamedTextColor.GRAY))
                .append(Component.text().content("    "))
                .append(RPGSymbols.MANA_SYMBOL)
                .append(Component.text().content(" " + mana).color(NamedTextColor.AQUA))
                .build();

        rpgPlayer.getPlayer().sendActionBar(actionBarMessage);
    }

}
