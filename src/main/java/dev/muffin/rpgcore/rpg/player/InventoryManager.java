package dev.muffin.rpgcore.rpg.player;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InventoryManager {

    private final List<ItemStack> playerBottomInventory;
    private final Player player;
    private boolean isOpen;

    public InventoryManager(Player player) {
        playerBottomInventory = new ArrayList<>();
        this.player = player;
        isOpen = false;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setIsOpen(boolean open) {
        isOpen = open;
    }

    public void saveBottomInventory() {
        setIsOpen(true);
        playerBottomInventory.clear();
        for (int i = 9; i < 36; i++) {
            playerBottomInventory.add(player.getInventory().getContents()[i]);
        }
    }

    public void restoreFromBottomInventory() {
        setIsOpen(false);
        if (!playerBottomInventory.isEmpty()) {
            for (int i = 9; i < 36; i++) {
                player.getInventory().setItem(i, null);
            }

            int slot = 9;
            for (ItemStack i : playerBottomInventory) {
                if (i != null) {
                    player.getInventory().setItem(slot, i);
                }
                slot += 1;
            }
        }
        playerBottomInventory.clear();
    }

    public void clearPlayerBottomInventory() {
        for (int i = 9; i < 36; i++) {
            player.getInventory().setItem(i, null);
        }
    }

}
