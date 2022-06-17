package dev.muffin.rpgcore.rpg.skills.casting;

import dev.muffin.rpgcore.Main;
import dev.muffin.rpgcore.rpg.player.RPGPlayer;
import dev.muffin.rpgcore.rpg.utils.constants.RPGConstants;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
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
            int slot = e.getNewSlot();
            if (slot <= RPGConstants.NUM_USABLE_SKILLS && slot != 0) {
                CastResponse castResponse = rpgPlayer.castSkill(rpgPlayer.getCastableSkills()[slot - 1]);

                boolean toCancel = true;

                switch (castResponse) {
                    case NO_MANA -> e.getPlayer().sendMessage(Component.text("Not enough mana to cast", NamedTextColor.RED));
                    case NOT_EQUIPPED -> toCancel = false;
                }
                e.setCancelled(toCancel);
            }
        }
    }

}
