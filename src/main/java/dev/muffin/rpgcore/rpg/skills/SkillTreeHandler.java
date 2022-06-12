package dev.muffin.rpgcore.rpg.skills;

import dev.muffin.rpgcore.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.jetbrains.annotations.NotNull;

public class SkillTreeHandler implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p) {
            Main.getInstance().getRPGPlayer(p).getPlayerClass().getSkillTree().showWarriorInventory();
            p.playSound(p.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1.0F, 1.0F);
        } else {
            sender.sendMessage(Component.text("Invalid Sender Type", NamedTextColor.RED));
            return true;
        }
        return false;
    }

    @EventHandler
    public void cancelClick(InventoryClickEvent e) {
        if (Main.getInstance().getRPGPlayer((Player) e.getView().getPlayer()).getPlayerClass().getSkillTree().getInventories().contains(e.getInventory())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void cancelDrag(InventoryDragEvent e) {
        if (Main.getInstance().getRPGPlayer((Player) e.getView().getPlayer()).getPlayerClass().getSkillTree().getInventories().contains(e.getInventory())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (Main.getInstance().getRPGPlayer((Player) e.getPlayer()) != null
                && Main.getInstance().getRPGPlayer((Player) e.getPlayer()).getPlayerClass().getSkillTree().getInventories().contains(e.getInventory())) {
            Main.getInstance().getRPGPlayer((Player) e.getPlayer()).getPlayerClass().getSkillTree().restoreFromBottomInventory();
        }
    }

}
