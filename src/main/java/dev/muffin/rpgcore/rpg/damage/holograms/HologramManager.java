package dev.muffin.rpgcore.rpg.damage.holograms;

import dev.muffin.rpgcore.Main;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class HologramManager implements Listener {

    @EventHandler
    public void cancelInteract(PlayerArmorStandManipulateEvent e) {
        for (Hologram hologram : hologramList) {
            if (hologram.getArmorStand() == e.getRightClicked()) {
                e.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void damageStand(EntityDamageEvent e) {
        if (e.getEntity() instanceof ArmorStand stand) {
            for (Hologram hologram : hologramList) {
                if (hologram.getArmorStand() == stand) {
                    e.setCancelled(true);
                    return;
                }
            }
        }
    }

    private final List<Hologram> hologramList;

    public HologramManager() {
        hologramList = new ArrayList<>();

        new BukkitRunnable() {
            public void run() {
                List<Hologram> remove = new ArrayList<>();

                for (Hologram hologram : hologramList) {
                    if (hologram.tickHologram()) {
                        remove.add(hologram);
                    }
                }

                hologramList.removeAll(remove);
            }
        }.runTaskTimer(Main.getInstance(), 1L, 1L);
    }

    public void addHologram(Hologram hologram) {
        hologramList.add(hologram);
    }

}
