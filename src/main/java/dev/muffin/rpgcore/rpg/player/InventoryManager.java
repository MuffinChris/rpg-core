package dev.muffin.rpgcore.rpg.player;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InventoryManager {

    private final List<ItemStack> playerBottomInventory;
    private final Player player;

    public InventoryManager(Player player) {
        playerBottomInventory = new ArrayList<>();
        this.player = player;
    }

    public void saveBottomInventory() {
        playerBottomInventory.clear();
        for (int i = 9; i < 36; i++) {
            playerBottomInventory.add(player.getInventory().getContents()[i]);
        }
    }

    public void restoreFromBottomInventory() {
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
