package dev.muffin.rpgcore.rpg.skills;

import dev.muffin.rpgcore.Main;
import dev.muffin.rpgcore.chat.utils.ComponentConverter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

import static dev.muffin.rpgcore.utilities.GUIItems.generateItem;

public class SkillTree {

    private Inventory warriorInventory;
    private final List<Inventory> inventories;
    private final Player player;
    private final List<ItemStack> playerBottomInventory;

    public SkillTree(Player player) {
        this.player = player;
        inventories = new ArrayList<>();
        playerBottomInventory = new ArrayList<>();
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

    public void showWarriorInventory() {
        saveBottomInventory();
        clearPlayerBottomInventory();
        player.openInventory(getWarriorInventory());
    }

    public void clearPlayerBottomInventory() {
        for (int i = 9; i < 36; i++) {
            player.getInventory().setItem(i, null);
        }
    }

    public Inventory getWarriorInventory() {
        warriorInventory = generateWarriorInventory();
        if (!inventories.contains(warriorInventory)) {
            inventories.add(warriorInventory);
        }
        return warriorInventory;
    }

    public List<Inventory> getInventories() {
        return inventories;
    }

    private Inventory generateWarriorInventory() {
        warriorInventory = Bukkit.createInventory(null, 54, Component.text(""));

        warriorInventory.setItem(4, getWarriorDescriptionItem());

        warriorInventory.setItem(13, generateDownArrow());

        warriorInventory.setItem(22, generateSkillItem(Main.getInstance().getClassHandler().getWarrior().getSkillList().get(0), player));

        warriorInventory.setItem(31, generateDownArrow());

        warriorInventory.setItem(40, generateSkillItem(Main.getInstance().getClassHandler().getWarrior().getSkillList().get(1), player));

        player.getInventory().setItem(13, generateDownArrow());

        return warriorInventory;
    }

    public ItemStack generateDownArrow() {
        ItemStack item = new ItemStack(Material.EMERALD, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(Component.text(""));
        itemMeta.setCustomModelData(100000);
        item.setItemMeta(itemMeta);
        return item;
    }

    public ItemStack generateSkillItem(Skill skill, Player p) {
        ItemStack item = generateItem(Material.EMERALD,
                Component.text("Skill: " + skill.getSkillName(), NamedTextColor.YELLOW),
                skill.getSkillDescription(p));
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(skill.getTexture());
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getWarriorDescriptionItem() {
        List<String> lore = new ArrayList<>();
        lore.add("&7Warriors are strong and fierce fighters");
        lore.add("&7They can handle any melee fight,");
        lore.add("&7but struggle with ranged combat.");
        return generateItem(Material.IRON_AXE, Component.text("The Path of the Warrior"),ComponentConverter.getComponentListFromStringList(lore));
    }

}
