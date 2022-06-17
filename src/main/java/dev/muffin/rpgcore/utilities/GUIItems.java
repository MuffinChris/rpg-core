package dev.muffin.rpgcore.utilities;

import dev.muffin.rpgcore.chat.utils.ComponentConverter;
import dev.muffin.rpgcore.rpg.skills.AugmentedSkill;
import dev.muffin.rpgcore.rpg.skills.Skill;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class GUIItems {

    public static ItemStack generateItem(Material material, Component displayName, List<Component> lore) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(displayName.decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE));
        itemMeta.lore(lore);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(itemMeta);

        return item;
    }

    public static ItemStack generateItem(Material material, Component displayName, List<Component> lore, int texture) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(displayName.decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE));
        itemMeta.setCustomModelData(texture);
        itemMeta.lore(lore);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(itemMeta);

        return item;
    }

    public static ItemStack generateCustomIcon(String name, int texture) {
        ItemStack item = new ItemStack(Material.EMERALD, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(ComponentConverter.getComponentFromString(name).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE));
        itemMeta.setCustomModelData(texture);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack generateSkillItem(Skill skill, Player p, boolean unlockable) {
        Component itemName;
        if (skill instanceof AugmentedSkill augmentedSkill) {
        //    itemName = Component.text(augmentedSkill.getToModify().getSkillName() +  " Augment: " + skill.getSkillName(), NamedTextColor.GOLD);
            itemName = Component.text("Skill: " + skill.getSkillName(), NamedTextColor.GOLD);
        } else {
            itemName = Component.text("Skill: " + skill.getSkillName(), NamedTextColor.YELLOW);
        }

        ItemStack item = generateItem(Material.EMERALD,
                itemName,
                skill.getSkillDescription(p));
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(skill.getTexture(p, unlockable));
        item.setItemMeta(meta);
        return item;
    }

}
