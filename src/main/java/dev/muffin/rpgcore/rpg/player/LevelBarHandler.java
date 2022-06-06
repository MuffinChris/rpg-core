package dev.muffin.rpgcore.rpg.player;

import com.destroystokyo.paper.event.player.PlayerPickupExperienceEvent;
import dev.muffin.rpgcore.Main;
import dev.muffin.rpgcore.rpg.utils.RPGConstants;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class LevelBarHandler implements Listener {

    private Main plugin;

    public LevelBarHandler(Main plugin) {
        this.plugin = plugin;

        new BukkitRunnable() {
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    updateLevelBar(p);
                }
            }
        }.runTaskTimer(plugin, 1L, 1L);
    }

    public void updateLevelBar(Player p) {
        RPGPlayer rpgPlayer = plugin.getRPGPlayer(p);
        p.setLevel(rpgPlayer.getPlayerClass().getLevel());
        double percent = rpgPlayer.getPlayerClass().getExp() / RPGConstants.LEVEL_EXP_MAP.get(rpgPlayer.getPlayerClass().getLevel());
        p.setExp(Math.min((float) percent, 0.999999999f));
    }

    @EventHandler
    public void onExpChange(PlayerExpChangeEvent e) {
        e.setAmount(0);
    }

    @EventHandler
    public void onExpPickup(PlayerPickupExperienceEvent e) {
        plugin.getRPGPlayer(e.getPlayer()).getPlayerClass().addExp(e.getExperienceOrb().getExperience() * 5.0);
        e.getExperienceOrb().setExperience(0);
        e.getExperienceOrb().remove();
    }

}
