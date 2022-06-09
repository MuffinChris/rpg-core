package dev.muffin.rpgcore.rpg.skills;

import dev.muffin.rpgcore.Main;
import dev.muffin.rpgcore.rpg.player.RPGPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
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


    Current Issue: clicking unnamed inventory will trigger check and fail because title is fake
            To fix, make SkillTree GUI per player. On listener check if the player clicking inventory is equal to a skill tree inventory. big refactor.

    private Map<Integer, String> warriorTree;
    private Map<String, Integer> warriorTreeReq;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            ((Player) sender).openInventory(generateWarriorInventory((Player) sender));
        } else {
            sender.sendMessage(Component.text("Invalid sender.", NamedTextColor.RED));
            return true;
        }
        return false;
    }

    @EventHandler
    public void onGUIDrag (final InventoryDragEvent e) {
        if (((TextComponent) e.getView().title()).content().equals("Warrior Skill Tree")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onGUIClick (final InventoryClickEvent e) {
        if (((TextComponent) e.getView().title()).content().equals("Warrior Skill Tree")) {
            e.setCancelled(true);

            if (e.getRawSlot() == 22) {
                RPGPlayer rpgPlayer = Main.getInstance().getRPGPlayer((Player) e.getView().getPlayer());
                if (!rpgPlayer.getPlayerClass().getSkillsOwned().contains(WARRIOR_SKILLS.get(0))) {
                    if (rpgPlayer.getPlayerClass().getSkillpoints() >= WARRIOR_SKILLS.get(0).getSkillpointCost()) {
                        rpgPlayer.getPlayerClass().setSkillpoints(rpgPlayer.getPlayerClass().getSkillpoints() - WARRIOR_SKILLS.get(0).getSkillpointCost());
                        rpgPlayer.getPlayerClass().getSkillsOwned().add(WARRIOR_SKILLS.get(0));
                        e.getView().getPlayer().sendMessage(Component.text("Unlocked Cleave"));
                    }
                }
            }
        }
    }

    private void generateWarriorTree() {
        warriorTree = new HashMap<>();
        warriorTreeReq = new HashMap<>();

        warriorTree.put(0, "Cleave");
        warriorTree.put(1, "Shatterstrike");

        warriorTreeReq.put("Cleave", -1);
        warriorTreeReq.put("Shatterstrike", 0);
    }

    private Inventory generateWarriorInventory(Player p) {
        Inventory warriorSkillTreeGUI = Bukkit.createInventory(null, 54, Component.text("Warrior Skill Tree", NamedTextColor.YELLOW));

        warriorSkillTreeGUI.setItem(4, generateItem(Material.IRON_AXE,
                Component.text("The Path of the Warrior"),
                Component.text(""),
                Component.text("Warriors are strong and fierce fighters.", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE),
                Component.text("They can handle any melee fight,", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE),
                Component.text("but struggle with ranged opponents.", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE),
                Component.text("Skillpoints: ", NamedTextColor.GRAY)
                        .append(Component.text(Main.getInstance().getRPGPlayer(p).getPlayerClass().getSkillpoints(), NamedTextColor.YELLOW))
                        .decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE)
                ));

        warriorSkillTreeGUI.setItem(13, generateDownArrow());

        warriorSkillTreeGUI.setItem(22, generateCleaveSkill(p));

        warriorSkillTreeGUI.setItem(31, generateDownArrow());

        warriorSkillTreeGUI.setItem(40, generateShatterstrikeSkill(p));
        // might be smarter to assign all GUI data within a skill
        return warriorSkillTreeGUI;
    }

    public ItemStack generateItem(Material material, Component displayName, Component ... loreStrings) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(displayName.decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE));
        List<Component> lore = Arrays.asList(loreStrings);
        itemMeta.lore(lore);

        item.setItemMeta(itemMeta);

        return item;
    }

    public ItemStack generateDownArrow() {
        ItemStack item = new ItemStack(Material.EMERALD, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(Component.text(""));
        itemMeta.setCustomModelData(100000);
        item.setItemMeta(itemMeta);
        return item;
    }

    public ItemStack generateCleaveSkill(Player p) {

        ItemStack item = generateItem(Material.EMERALD,
                Component.text("Skill: Cleave", NamedTextColor.YELLOW),
                WARRIOR_SKILLS.get(0).getStats());
        ItemMeta meta = item.getItemMeta();

        Component unlocked = Component.text("&aAbility Unlocked");
        boolean unlockedBool = (Main.getInstance().getRPGPlayer(p).getPlayerClass().getSkillsOwned().contains(WARRIOR_SKILLS.get(0)));
        if (unlockedBool) {
            meta.lore().add(unlocked);
        }

        meta.setCustomModelData(WARRIOR_SKILLS.get(0).getTexture());
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack generateShatterstrikeSkill(Player p) {
        ItemStack item = generateItem(Material.EMERALD,
                Component.text("Skill: Shatterstrike", NamedTextColor.YELLOW),
                WARRIOR_SKILLS.get(1).getStats());
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(WARRIOR_SKILLS.get(1).getTexture());
        item.setItemMeta(meta);
        return item;
    }

    public SkillTree() {
        generateWarriorTree();
    }


}
