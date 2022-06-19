package dev.muffin.rpgcore.rpg.skills.casting;

import dev.muffin.rpgcore.Main;
import dev.muffin.rpgcore.rpg.player.RPGPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CastingRunnables {

    public CastingRunnables() {

        new BukkitRunnable() {
            public void run() {
                for (RPGPlayer rpgPlayer : Main.getInstance().getAllRPGPlayers()) {
                    rpgPlayer.getSkillCaster().getCooldownManager().updateCooldowns();
                    rpgPlayer.updateSkillbar();
                }
            }
        }.runTaskTimer(Main.getInstance(), 1L, 1L);
    }

}
