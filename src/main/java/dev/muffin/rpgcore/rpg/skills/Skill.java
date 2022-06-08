package dev.muffin.rpgcore.rpg.skills;

import dev.muffin.rpgcore.rpg.utils.RPGSymbols;
import dev.muffin.rpgcore.utilities.DecimalFormats;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

public abstract class Skill {
    private final String skillName;
    private final String description;
    private final double cooldown;

    private final double manaCost;

    private final int levelRequirement;
    private final int skillpointCost;

    public Skill(String skillName, String description, double cooldown, double manaCost, int levelRequirement, int skillpointCost) {
        this.skillName = skillName;
        this.description = description;
        this.cooldown = cooldown;
        this.manaCost = manaCost;
        this.levelRequirement = levelRequirement;
        this.skillpointCost = skillpointCost;
    }

    public String getSkillName() {
        return skillName;
    }

    public String getDescription() {
        return description;
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

    public int getSkillpointCost() {
        return skillpointCost;
    }

    public abstract void castSkill(Player caster);

    public Component getStats() {
        return Component.text("Mana Cost: ", NamedTextColor.GRAY)
                .append(Component.text(getManaCost() + " ", NamedTextColor.AQUA))
                .append(RPGSymbols.MANA_SYMBOL)
                .append(Component.text("Cooldown: ", NamedTextColor.GRAY))
                .append(Component.text(DecimalFormats.oneDecimalsZero.format(getCooldown()) + "s", NamedTextColor.WHITE))
                .append(Component.text(getDescription()));
    }

}
