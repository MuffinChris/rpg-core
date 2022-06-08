package dev.muffin.rpgcore.rpg.skills.casting;

import dev.muffin.rpgcore.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CastingRunnables {

    public CastingRunnables() {

        new BukkitRunnable() {
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    Main.getInstance().getRPGPlayer(p).getSkillCaster().getCooldownManager().updateCooldowns();
                    Main.getInstance().getRPGPlayer(p).updateSkillbar();
                }
            }
        }.runTaskTimer(Main.getInstance(), 1L, 1L);
    }

}
