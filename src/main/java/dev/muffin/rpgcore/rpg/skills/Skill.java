package dev.muffin.rpgcore.rpg.skills;

import dev.muffin.rpgcore.rpg.utils.RPGSymbols;
import dev.muffin.rpgcore.utilities.DecimalFormats;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;

public abstract class Skill {
    private final String skillName;
    private final String description;
    private final double cooldown;

    private final double manaCost;

    private final int levelRequirement;
    private final int skillpointCost;
    private final int texture;

    public Skill(String skillName, String description, double cooldown, double manaCost, int levelRequirement, int skillpointCost, int texture) {
        this.skillName = skillName;
        this.description = description;
        this.cooldown = cooldown;
        this.manaCost = manaCost;
        this.levelRequirement = levelRequirement;
        this.skillpointCost = skillpointCost;
        this.texture = texture;
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

    public int getTexture() {
        return texture;
    }

    public abstract void castSkill(Player caster);

    public Component[] getStats() {
        Component[] stats = new Component[5];
        stats[0] = Component.text("Mana Cost: ", NamedTextColor.GRAY)
                .append(Component.text(getManaCost(), NamedTextColor.AQUA))
                .append(RPGSymbols.MANA_SYMBOL).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE);
        stats[1] = Component.text("Cooldown: ", NamedTextColor.GRAY)
                .append(Component.text(DecimalFormats.oneDecimalsZero.format(getCooldown()) + "s", NamedTextColor.WHITE))
                .decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE);
        stats[2] = Component.text("Skillpoint Cost: ", NamedTextColor.GRAY)
                .append(Component.text(skillpointCost, NamedTextColor.GOLD))
                .decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE);
        stats[3] = Component.text("");
        stats[4] = Component.text(getDescription(), NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE);
        return stats;
    }

}
