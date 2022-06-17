package dev.muffin.rpgcore.rpg.skills;

import dev.muffin.rpgcore.rpg.skills.abstracts.AugmentedPassiveSkill;
import dev.muffin.rpgcore.rpg.skills.abstracts.AugmentedSkill;
import dev.muffin.rpgcore.rpg.skills.abstracts.PassiveSkill;
import dev.muffin.rpgcore.rpg.skills.abstracts.Skill;
import dev.muffin.rpgcore.rpg.skills.skillgui.SkillsGUIConstants;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class SkillList {

    private final Skill[] skillList;
    private final List<Skill> unlockedSkills;
    private final PassiveManager passiveManager;

    public SkillList(Player p, Skill[] skillList, List<Skill> unlockedSkills) {
        this.skillList = skillList;
        this.unlockedSkills = unlockedSkills;
        passiveManager = new PassiveManager(p);
    }

    public PassiveManager getPassiveManager() {
        return passiveManager;
    }

    public Skill[] getEquippedSkills() {
        return skillList;
    }

    public List<Skill> getUnlockedSkills() {
        return unlockedSkills;
    }

    public void sortSkillList() {
        unlockedSkills.sort((s2, s1) -> Double.compare(s2.getCooldown(), s1.getCooldown()));
    }


    /**
     * Add a skill. If it's a modified skill, replace the skill it replaces. Relies on SkillTree proper design
     * @param skill the skill
     */
    public void addSkill(Skill skill) {
        if (skill instanceof AugmentedPassiveSkill augmentedPassiveSkill) {
            unlockedSkills.set(unlockedSkills.indexOf(augmentedPassiveSkill.getToModify()), augmentedPassiveSkill);
            passiveManager.removePassive(augmentedPassiveSkill.getToModify());
            passiveManager.addPassive(augmentedPassiveSkill);
        } else if (skill instanceof PassiveSkill passiveSkill) {
            unlockedSkills.add(passiveSkill);
            passiveManager.addPassive(passiveSkill);
        } else if (skill instanceof AugmentedSkill augmentedSkill) {
            unlockedSkills.set(unlockedSkills.indexOf(augmentedSkill.getToModify()), augmentedSkill);
            if (Arrays.asList(skillList).contains(augmentedSkill.getToModify())) {
                for (int z = 0; z < skillList.length; z++) {
                    if (skillList[z] == augmentedSkill.getToModify()) {
                        skillList[z] = augmentedSkill;
                    }
                }
            }
        } else {
            unlockedSkills.add(skill);
        }

        sortSkillList();
        //unlockedSkills.add(skill);
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
        if (unlockedSkills.get(slot) instanceof PassiveSkill) {
            player.sendMessage(Component.text("Can't equip a Passive", NamedTextColor.RED));
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
