package dev.muffin.rpgcore.rpg.skills;

import dev.muffin.rpgcore.chat.utils.ComponentConverter;
import dev.muffin.rpgcore.rpg.player.PlayerClass;
import dev.muffin.rpgcore.utilities.GUIItems;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static dev.muffin.rpgcore.rpg.skills.SkillsGUIConstants.UNEQUIPPED_TEXTURE;

public class SkillsGUI {

    private final Inventory skillsGui;
    private final List<Inventory> inventories;
    private final Player player;

    public SkillsGUI(Player player) {
        this.player = player;
        inventories = new ArrayList<>();
        skillsGui = Bukkit.createInventory(null, 27, Component.text("Skill Selection Menu"));
        inventories.add(skillsGui);
    }

    public List<Inventory> getInventories() {
        return inventories;
    }

    public void openSkillsGui(PlayerClass playerClass) {
        player.openInventory(skillsGui);
        setSkillsGui(playerClass);
    }

    public void setSkillsGui(PlayerClass playerClass) {
        Inventory currentInventory = player.getOpenInventory().getTopInventory();

        currentInventory.setItem(11, getSkill(playerClass, 1));
        currentInventory.setItem(12, getSkill(playerClass, 2));
        currentInventory.setItem(13, getSkill(playerClass, 3));
        currentInventory.setItem(14, getSkill(playerClass, 4));
        currentInventory.setItem(15, getSkill(playerClass, 5));
    }

    public void setSkillSelectGui(PlayerClass playerClass, int slot) {
        Inventory currentInventory = player.getOpenInventory().getTopInventory();

        int slotCounter = 0;
        for (Skill skill : playerClass.getUnlockedSkills()) {
            currentInventory.setItem(slotCounter, GUIItems.generateSkillItem(skill, player));
            slotCounter+=1;
        }

        ... need to clear inv first too lol
    }

    public ItemStack getSkill(PlayerClass playerClass, int slot) {
        if (playerClass.getSkillList()[slot - 1] != null) {
            return GUIItems.generateSkillItem(playerClass.getSkillList()[slot - 1], player);
        } else {
            return getUnequipped(slot);
        }
    }

    public ItemStack getUnequipped(int slot) {
        List<String> lore = new ArrayList<>();
        lore.add("&7No skill assigned.");
        lore.add("&7Click to assign a skill.");
        return GUIItems.generateItem(Material.EMERALD, Component.text("Skill Slot " + slot), ComponentConverter.getComponentListFromStringList(lore), UNEQUIPPED_TEXTURE);
    }

}
