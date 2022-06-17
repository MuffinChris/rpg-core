package dev.muffin.rpgcore.rpg.skills;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class SkillList {

    private final Skill[] skillList;
    private final List<Skill> unlockedSkills;

    public SkillList(Skill[] skillList, List<Skill> unlockedSkills) {
        this.skillList = skillList;
        this.unlockedSkills = unlockedSkills;
    }

    public Skill[] getEquippedSkills() {
        return skillList;
    }

    public List<Skill> getUnlockedSkills() {
        return unlockedSkills;
    }


    /**
     * Add a skill. If it's a modified skill, replace the skill it replaces. Relies on SkillTree proper design
     * @param skill the skill
     */
    public void addSkill(Skill skill) {
        if (skill instanceof AugmentedSkill modifiedSkill) {
            for (int i = unlockedSkills.size() - 1; i >= 0; i--) {
                if (unlockedSkills.get(i).equals(modifiedSkill.getToModify())) {
                    unlockedSkills.set(i, modifiedSkill);
                    if (Arrays.asList(skillList).contains(modifiedSkill.getToModify())) {
                        for (int z = 0; z < skillList.length; z++) {
                            if (skillList[z] == modifiedSkill.getToModify()) {
                                skillList[z] = modifiedSkill;
                            }
                        }
                    }
                    break;
                }
            }
        } else {
            unlockedSkills.add(skill);
        }
        //unlockedSkills.add(skill);
    }

    public void removeSkill(Skill skill) {
        unlockedSkills.remove(skill);
    }

    public void equipSkill(Player player, int slotToEquip, int slot) {
        if (slot == SkillsGUIConstants.UNEQUIP_SLOT) {
            skillList[slotToEquip - 1] = null;
            player.sendMessage(Component.text("Unequipped skill in slot " + slotToEquip + ".", NamedTextColor.GREEN));
            player.playSound(player.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1.0F, 1.0F);
            return;
        } else if (slot >= unlockedSkills.size()) {
            return;
        }
        if (Arrays.asList(skillList).contains(unlockedSkills.get(slot))) {
            skillList[Arrays.asList(skillList).indexOf(unlockedSkills.get(slot))] = null;
        }
        skillList[slotToEquip - 1] = unlockedSkills.get(slot);
        player.sendMessage(Component.text("Equipped " + unlockedSkills.get(slot).getSkillName() + " in slot " + slotToEquip, NamedTextColor.GREEN));
        player.playSound(player.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1.0F, 1.0F);
    }

}
