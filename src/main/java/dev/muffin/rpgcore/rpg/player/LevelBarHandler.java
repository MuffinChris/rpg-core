package dev.muffin.rpgcore.rpg.player;

import com.destroystokyo.paper.event.player.PlayerPickupExperienceEvent;
import dev.muffin.rpgcore.Main;
import dev.muffin.rpgcore.rpg.utils.RPGConstants;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class LevelBarHandler implements Listener {

    public LevelBarHandler() {
        new BukkitRunnable() {
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    updateLevelBar(p);
                }
            }
        }.runTaskTimer(Main.getInstance(), 1L, 1L);
    }

    public void updateLevelBar(Player p) {
        RPGPlayer rpgPlayer = Main.getInstance().getRPGPlayer(p);
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
        double modifier = (Math.random() * 0.2 + 1) * 7.0;
        Main.getInstance().getRPGPlayer(e.getPlayer()).getPlayerClass().addExp(Math.round(e.getExperienceOrb().getExperience() * modifier));
        e.getExperienceOrb().setExperience(0);
        e.getExperienceOrb().remove();
    }

    @EventHandler
    public void onExpDrop(PlayerDeathEvent e) {
        e.setShouldDropExperience(false);
    }

}
