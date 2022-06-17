package dev.muffin.rpgcore.rpg.skills.abstracts;

import dev.muffin.rpgcore.Main;
import dev.muffin.rpgcore.chat.utils.ComponentConverter;
import dev.muffin.rpgcore.rpg.utils.constants.RPGSymbols;
import dev.muffin.rpgcore.utilities.DecimalFormats;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public abstract class Skill extends Unlockable {
    private final double cooldown;

    private final double manaCost;

    // technically useless if using skillpoints
    private final int levelRequirement;
    private final int unlockedTexture;
    private final int unlockableTexture;
    private final int lockedTexture;

    public Skill(String skillName, double cooldown, double manaCost, int levelRequirement, int skillpointCost, int unlockedTexture, int unlockableTexture, int lockedTexture) {
        super(skillName, skillpointCost);
        this.cooldown = cooldown;
        this.manaCost = manaCost;
        this.levelRequirement = levelRequirement;
        this.unlockedTexture = unlockedTexture;
        this.unlockableTexture = unlockableTexture;
        this.lockedTexture = lockedTexture;
    }

    public String getSkillName() {
        return getName();
    }

    public double getCooldown() {
        return cooldown;
    }

    public double getManaCost() {
        return manaCost;
    }

    public int getLevelRequirement() {
        return levelRequirement;
    }

    public int getUnlockedTexture() {
        return unlockedTexture;
    }

    public int getUnlockableTexture() {
        return unlockableTexture;
    }

    public int getLockedTexture() {
        return lockedTexture;
    }

    public int getTexture(Player caster, boolean unlockable) {
        if (Main.getInstance().getRPGPlayer(caster).getSkillList().getUnlockedSkills().contains(this)) {
            return unlockedTexture;
        } else if (unlockable) {
            return unlockableTexture;
        } else {
            return lockedTexture;
        }
    }

    public abstract void castSkill(Player caster);

    public List<Component> getSkillDescription(Player caster) {
        List<String> description = getDescription(caster);

        if (this instanceof PassiveSkill) {
            description.add("");
        } else {
            description.add("");
            description.add("&7Mana Cost: &b" + DecimalFormats.noDecimals.format(getManaCost()) + " " + RPGSymbols.MANA_SYMBOL.content());
            description.add("&7Cooldown: &f" + DecimalFormats.oneDecimalsZero.format(getCooldown()) + "s");
            description.add("");
        }

        if (Arrays.asList(Main.getInstance().getRPGPlayer(caster).getSkillList().getEquippedSkills()).contains(this)) {
            description.add("&eSkill Equipped &7(&fSlot " + (Arrays.asList(Main.getInstance().getRPGPlayer(caster).getSkillList().getEquippedSkills()).indexOf(this) + 1) + "&7)");
        } else if (Main.getInstance().getRPGPlayer(caster).getSkillList().getUnlockedSkills().contains(this)) {
            description.add("&aSkill Unlocked");
        } else {
            description.add("&7Skillpoint Cost: &e" + getSkillpointCost());
        }
        return ComponentConverter.getComponentListFromStringList(description);
    }

}
