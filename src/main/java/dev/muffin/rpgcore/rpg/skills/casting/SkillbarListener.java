package dev.muffin.rpgcore.rpg.skills.casting;

import dev.muffin.rpgcore.Main;
import dev.muffin.rpgcore.rpg.player.RPGPlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class SkillbarListener implements Listener {

    private Main plugin;

    public SkillbarListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void toggleSkillBar(PlayerSwapHandItemsEvent e) {
        if (!e.getPlayer().isSneaking()) {
            e.setCancelled(true);
            if (!plugin.getRPGPlayer(e.getPlayer()).getSkillbar().isActive()) {
                plugin.getRPGPlayer(e.getPlayer()).enableSkillbar();
                e.getPlayer().getInventory().setHeldItemSlot(0);
            } else {
                plugin.getRPGPlayer(e.getPlayer()).disableSkillbar();
            }
        }
    }

    @EventHandler
    public void castSkillFromBar(PlayerItemHeldEvent e) {
        RPGPlayer rpgPlayer = plugin.getRPGPlayer(e.getPlayer());
        if (rpgPlayer.getSkillbar().isActive()) {
            e.setCancelled(true);

            int slot = e.getNewSlot();
            if (slot <= rpgPlayer.getPlayerClass().getCastableSkills().size() && slot != 0) {
                CastResponse castResponse = rpgPlayer.getSkillCaster().cast(rpgPlayer.getPlayerClass().getCastableSkills().get(slot - 1));
            }
        }
    }

}
