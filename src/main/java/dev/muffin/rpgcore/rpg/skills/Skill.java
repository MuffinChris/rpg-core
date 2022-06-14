package dev.muffin.rpgcore.rpg.skills;

import dev.muffin.rpgcore.Main;
import dev.muffin.rpgcore.chat.utils.ComponentConverter;
import dev.muffin.rpgcore.rpg.utils.constants.RPGSymbols;
import dev.muffin.rpgcore.utilities.DecimalFormats;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public abstract class Skill {
    private final String skillName;
    private final double cooldown;

    private final double manaCost;

    // technically useless if using skillpoints
    private final int levelRequirement;
    private final int skillpointCost;
    private final int texture;

    public Skill(String skillName, double cooldown, double manaCost, int levelRequirement, int skillpointCost, int texture) {
        this.skillName = skillName;
        this.cooldown = cooldown;
        this.manaCost = manaCost;
        this.levelRequirement = levelRequirement;
        this.skillpointCost = skillpointCost;
        this.texture = texture;
    }

    public String getSkillName() {
        return skillName;
    }

    public abstract List<String> getDescription(Player caster);

    public double getCooldown() {
        return cooldown;
    }

    public double getManaCost() {
        return manaCost;
    }

    public int getLevelRequirement() {
        return levelRequirement;
    }

    public int getSkillpointCost() {
        return skillpointCost;
    }

    public int getTexture() {
        return texture;
    }

    public abstract void castSkill(Player caster);

    public List<Component> getSkillDescription(Player caster) {
        List<String> description = getDescription(caster);
        description.add("");
        description.add("&7Mana Cost: &b" + DecimalFormats.noDecimals.format(getManaCost()) + " " + RPGSymbols.MANA_SYMBOL.content());
        description.add("&7Cooldown: &f" + DecimalFormats.oneDecimalsZero.format(getCooldown()) + "s");
        description.add("");

        if (Arrays.asList(Main.getInstance().getRPGPlayer(caster).getPlayerClass().getSkillList()).contains(this)) {
            description.add("&eSkill Equipped &7(&fSlot " + (Arrays.asList(Main.getInstance().getRPGPlayer(caster).getPlayerClass().getSkillList()).indexOf(this) + 1) + "&7)");
        } else if (Main.getInstance().getRPGPlayer(caster).getPlayerClass().getUnlockedSkills().contains(this)) {
            description.add("&aSkill Unlocked");
        } else {
            description.add("&7Skillpoint Cost: &e" + getSkillpointCost());
        }
        return ComponentConverter.getComponentListFromStringList(description);
    }

}
