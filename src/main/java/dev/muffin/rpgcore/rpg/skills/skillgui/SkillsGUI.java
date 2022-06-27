package dev.muffin.rpgcore.rpg.skills.skillgui;

import dev.muffin.rpgcore.chat.utils.ComponentConverter;
import dev.muffin.rpgcore.rpg.player.RPGPlayer;
import dev.muffin.rpgcore.rpg.skills.abstracts.PassiveSkill;
import dev.muffin.rpgcore.rpg.skills.abstracts.Skill;
import dev.muffin.rpgcore.utilities.GUIItems;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static dev.muffin.rpgcore.rpg.skills.skillgui.SkillsGUIConstants.*;

public class SkillsGUI {

    private final Inventory skillsGui;
    private final List<Inventory> inventories;
    private final Player player;
    private boolean selecting;
    private int slotToEquip;

    public SkillsGUI(Player player) {
        this.player = player;
        inventories = new ArrayList<>();
        skillsGui = Bukkit.createInventory(null, 27, Component.text("Skill Selection Menu"));
        inventories.add(skillsGui);
        selecting = false;
        slotToEquip = -1;
    }

    public List<Inventory> getInventories() {
        return inventories;
    }

    public boolean isSelecting() {
        return selecting;
    }

    public void close() {
        selecting = false;
        slotToEquip = -1;
    }

    public int getSlotToEquip() {
        return slotToEquip;
    }

    public void openSkillsGui(Skill[] skillList, RPGPlayer rpgPlayer) {
        player.openInventory(skillsGui);
        setSkillsGui(skillList, rpgPlayer);
    }

    public void setSkillsGui(Skill[] skillList, RPGPlayer rpgPlayer) {
        Inventory currentInventory = player.getOpenInventory().getTopInventory();

        currentInventory.clear();

        currentInventory.setItem(SKILLTREE_SLOT, generateSkillTreeItem());
        currentInventory.setItem(SKILL_ONE_SLOT, getSkill(skillList, 1, rpgPlayer));
        currentInventory.setItem(SKILL_TWO_SLOT, getSkill(skillList, 2, rpgPlayer));
        currentInventory.setItem(SKILL_THREE_SLOT, getSkill(skillList, 3, rpgPlayer));
        currentInventory.setItem(SKILL_FOUR_SLOT, getSkill(skillList, 4, rpgPlayer));
        currentInventory.setItem(SKILL_FIVE_SLOT, getSkill(skillList, 5, rpgPlayer));
    }

    public void setSkillSelectGui(List<Skill> unlockedSkills, int slot, RPGPlayer rpgPlayer) {
        Inventory currentInventory = player.getOpenInventory().getTopInventory();

        currentInventory.clear();

        selecting = true;
        slotToEquip = slot;

        int slotCounter = 0;
        for (Skill skill : unlockedSkills) {
            currentInventory.setItem(slotCounter, generateSlotSelectSkillItem(skill, slot, rpgPlayer));
            slotCounter+=1;
        }

        currentInventory.setItem(UNEQUIP_SLOT, generateUnequipSkillItem());
    }

    public ItemStack generateSlotSelectSkillItem(Skill skill, int slot, RPGPlayer rpgPlayer) {
        ItemStack item = GUIItems.generateSkillItem(skill, rpgPlayer, true);
        ItemMeta meta = item.getItemMeta();
        if (skill instanceof PassiveSkill) {
            meta.lore().add(Component.text(""));
            meta.lore().add(Component.text("Not Equippable (Passive)", NamedTextColor.DARK_GRAY));
        } else {
            meta.lore().add(Component.text(""));
            meta.lore().add(Component.text("Click to equip to slot ", NamedTextColor.GRAY)
                    .append(Component.text(slot, NamedTextColor.WHITE)));
        }
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getSkill(Skill[] skillList, int slot, RPGPlayer rpgPlayer) {
        if (skillList[slot - 1] != null) {
            return GUIItems.generateSkillItem(skillList[slot - 1], rpgPlayer, true);
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

    public ItemStack generateUnequipSkillItem() {
        List<String> lore = new ArrayList<>();
        lore.add("&7Click to unequip skill.");
        return GUIItems.generateItem(Material.EMERALD, Component.text("Unequip Skill"), ComponentConverter.getComponentListFromStringList(lore), UNEQUIP_SKILL_TEXTURE);
    }

    public ItemStack generateSkillTreeItem() {
        List<String> lore = new ArrayList<>();
        lore.add("&7Click to unlock new skills.");
        return GUIItems.generateItem(Material.EMERALD, Component.text("Open Skill Tree"), ComponentConverter.getComponentListFromStringList(lore), SKILLTREE_TEXTURE);
    }

}
