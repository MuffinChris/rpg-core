package dev.muffin.rpgcore.rpg.skills;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static dev.muffin.rpgcore.rpg.utils.ArchetypeConstants.WARRIOR_SKILLS;

public class SkillTree implements CommandExecutor, Listener {

    private Inventory warriorSkillTreeGUI;
    private Map<Integer, String> warriorTree;
    private Map<String, Integer> warriorTreeReq;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            ((Player) sender).openInventory(warriorSkillTreeGUI);
        } else {
            sender.sendMessage(Component.text("Invalid sender.", NamedTextColor.RED));
            return true;
        }
        return false;
    }

    @EventHandler
    public void onGUIDrag (final InventoryDragEvent e) {
        if (e.getInventory().equals(warriorSkillTreeGUI)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onGUIClick (final InventoryClickEvent e) {
        if (e.getInventory().equals(warriorSkillTreeGUI)) {
            e.setCancelled(true);
        }
    }

    private void generateWarriorTree() {
        warriorTree = new HashMap<>();
        warriorTreeReq = new HashMap<>();

        warriorTree.put(0, "Cleave");
        warriorTree.put(1, "Shatterstrike");

        warriorTreeReq.put("Cleave", -1);
        warriorTreeReq.put("Shatterstrike", 0);

        warriorSkillTreeGUI = Bukkit.createInventory(null, 54, Component.text("Warrior Skill Tree"));

        warriorSkillTreeGUI.setItem(4, generateItem(Material.IRON_AXE,
                Component.text("The Path of the Warrior"),
                Component.text("""

                        Warriors are strong and fierce fighters.
                        They can handle any melee fight,
                        but struggle with ranged opponents.""", NamedTextColor.GRAY)));

        warriorSkillTreeGUI.setItem(13, generateItem(Material.EMERALD, Component.text("down arrow texture")));

        warriorSkillTreeGUI.setItem(22, generateItem(Material.DIAMOND_AXE,
                Component.text("Skill: Cleave", NamedTextColor.YELLOW),
                WARRIOR_SKILLS.get(0).getStats()));

        warriorSkillTreeGUI.setItem(31, generateItem(Material.EMERALD, Component.text("down arrow texture")));

        warriorSkillTreeGUI.setItem(40, generateItem(Material.COARSE_DIRT,
                Component.text("Skill: Shatterstrike", NamedTextColor.YELLOW),
                WARRIOR_SKILLS.get(1).getStats()));

    }

    public ItemStack generateItem(Material material, Component displayName, Component ... loreStrings) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(displayName);
        List<Component> lore = Arrays.asList(loreStrings);
        itemMeta.lore(lore);
        item.setItemMeta(itemMeta);

        return item;
    }

    public SkillTree() {
        generateWarriorTree();
    }


}
