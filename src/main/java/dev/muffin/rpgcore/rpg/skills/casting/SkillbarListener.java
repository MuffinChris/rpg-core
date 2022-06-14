package dev.muffin.rpgcore.rpg.skills.casting;

import dev.muffin.rpgcore.Main;
import dev.muffin.rpgcore.rpg.player.RPGPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class SkillbarListener implements Listener {

    @EventHandler
    public void toggleSkillBar(PlayerSwapHandItemsEvent e) {
        if (!e.getPlayer().isSneaking()) {
            e.setCancelled(true);
            if (!Main.getInstance().getRPGPlayer(e.getPlayer()).getSkillbar().isActive()) {
                Main.getInstance().getRPGPlayer(e.getPlayer()).enableSkillbar();
                e.getPlayer().getInventory().setHeldItemSlot(0);
            } else {
                Main.getInstance().getRPGPlayer(e.getPlayer()).disableSkillbar();
            }
        }
    }

    @EventHandler
    public void castSkillFromBar(PlayerItemHeldEvent e) {
        RPGPlayer rpgPlayer = Main.getInstance().getRPGPlayer(e.getPlayer());
        if (rpgPlayer.getSkillbar().isActive()) {
            e.setCancelled(true);

            int slot = e.getNewSlot();
            if (slot <= rpgPlayer.getPlayerClass().getCastableSkills().size() && slot != 0) {
                CastResponse castResponse = rpgPlayer.castSkill(rpgPlayer.getPlayerClass().getCastableSkills().get(slot - 1));
                switch (castResponse) {
                    case NO_MANA -> e.getPlayer().sendMessage(Component.text("Not enough mana to cast", NamedTextColor.RED));
                }
            }
        }
    }

}
