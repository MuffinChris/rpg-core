package dev.muffin.rpgcore.rpg.utils;

import dev.muffin.rpgcore.rpg.utils.constants.RPGConstants;
import dev.muffin.rpgcore.utilities.DecimalFormats;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.util.Ticks;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import static dev.muffin.rpgcore.rpg.utils.constants.RPGConstants.MAX_LEVEL;

public class RPGLevelInfo {

    private int level;
    private double exp;
    private int skillpoints;

    public RPGLevelInfo(int level, double exp, int skillpoints) {
        this.level = level;
        this.exp = exp;
        this.skillpoints = skillpoints;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getExp() {
        return exp;
    }

    public void setExp(double exp) {
        this.exp = exp;
    }

    public int getSkillpoints() {
        return skillpoints;
    }

    public void setSkillpoints(int skillpoints) {
        this.skillpoints = skillpoints;
    }

    public void incrementSkillpoints() {
        skillpoints++;
    }

    /**
     * Add exp to a player
     * @param exp the exp
     */
    public void addExp(Player player, double exp) {
        setExp(getExp() + Math.abs(exp));
        Component expMessage = Component.text().content("    [+" + DecimalFormats.oneDecimals.format(exp) + " XP]").color(NamedTextColor.GRAY).build();
        player.sendMessage(expMessage);
        checkLevelUp(player);
    }

    /**
     * Check if a player should level up
     */
    public void checkLevelUp(Player player) {
        int startLevel = getLevel();
        int nextLevel = getLevel();
        while (getExp() >= RPGConstants.LEVEL_EXP_MAP.get(nextLevel) && nextLevel < 100) {
            setExp(Math.max(0, getExp() - RPGConstants.LEVEL_EXP_MAP.get(nextLevel)));
            nextLevel+=1;
            incrementSkillpoints();
        }
        if (nextLevel > startLevel) {
            setLevel(nextLevel);
            levelUpRewards(player, startLevel, nextLevel);
        }

        if (getLevel() == MAX_LEVEL && getExp() > RPGConstants.LEVEL_EXP_MAP.get(getLevel())) {
            setExp(RPGConstants.LEVEL_EXP_MAP.get(getLevel()));
        }
    }

    /**
     * Celebrate the player leveling up!
     */
    public void levelUpRewards(Player player, int startLevel, int nextLevel) {
        player.getWorld().playSound(player, Sound.UI_TOAST_CHALLENGE_COMPLETE, 1.0F, 1.0F);

        Component maintitle = Component.text("LEVEL UP", NamedTextColor.YELLOW, TextDecoration.BOLD);
        Component subtitle = Component.text((startLevel), NamedTextColor.GOLD)
                .append(Component.text(" -> ", NamedTextColor.GRAY))
                .append(Component.text((nextLevel), NamedTextColor.GOLD));
        Title title = Title.title(maintitle, subtitle, Title.Times.times(Ticks.duration(30), Ticks.duration(70), Ticks.duration(40)));

        player.showTitle(title);
    }
}
