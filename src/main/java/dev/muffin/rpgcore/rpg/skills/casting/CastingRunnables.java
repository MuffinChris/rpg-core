package dev.muffin.rpgcore.rpg.skills.casting;

import dev.muffin.rpgcore.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CastingRunnables {

    public CastingRunnables(Main plugin) {

        new BukkitRunnable() {
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    plugin.getRPGPlayer(p).getSkillCaster().getCooldownManager().updateCooldowns();
                    plugin.getRPGPlayer(p).updateSkillbar();
                }
            }
        }.runTaskTimer(plugin, 1L, 1L);
    }

}
