package dev.muffin.rpgcore.rpg.player;

import dev.muffin.rpgcore.rpg.archetypes.Archetype;
import dev.muffin.rpgcore.rpg.classes.RPGClass;
import dev.muffin.rpgcore.rpg.skills.SkillTree;
import dev.muffin.rpgcore.rpg.utils.constants.RPGConstants;
import dev.muffin.rpgcore.rpg.utils.RPGInfo;
import dev.muffin.rpgcore.rpg.utils.RPGStats;
import dev.muffin.rpgcore.rpg.skills.Skill;
import dev.muffin.rpgcore.utilities.DecimalFormats;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.util.Ticks;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static dev.muffin.rpgcore.rpg.utils.constants.RPGConstants.HEALTH_SCALE;
import static dev.muffin.rpgcore.rpg.utils.constants.RPGConstants.MAX_LEVEL;

/**
 * A PlayerClass stores a player's RPG info related to classes and archetypes
 */
public class PlayerClass {

    // Stats
    private RPGStats stats;

    private RPGInfo rpgInfo;


    // Class Info
    private Archetype archetype;
    private RPGClass rpgClass;
    private SkillTree skillTree;

    private final Player player;

    public PlayerClass(Player player, Archetype archetype, RPGInfo rpgInfo) {
        this.player = player;
        this.archetype = archetype;
        this.rpgInfo = rpgInfo;
        stats = new RPGStats(0, 0, 0);

        player.setHealthScale(HEALTH_SCALE);
        updateStats();
    }

    public RPGStats getStats() {
        return stats;
    }

    public RPGInfo getRpgInfo() {
        return rpgInfo;
    }

    /**
     * Get a player's maximum HP based on class, archetype, items, etc
     * @return max hp
     */
    public double getMaxHP() {
        return archetype.getStats().hp + archetype.getStats().hpPerLevel * rpgInfo.getLevel();
    }

    /**
     * Get a player's maximum Mana based on class, archetype, items, etc
     * @return max mana
     */
    public double getMaxMana() {
        return archetype.getStats().mana + archetype.getStats().manaPerLevel * rpgInfo.getLevel();
    }

    /**
     * Get a player's mana regen based on class, archetype, items, etc
     * @return mana regen
     */
    public double getManaRegen() {
        return archetype.getStats().manaRegen + archetype.getStats().manaRegenPerLevel * rpgInfo.getLevel();
    }

    public Archetype getArchetype() {
        return archetype;
    }

    /**
     * Add exp to a player
     * @param exp the exp
     */
    public void addExp(double exp) {
        rpgInfo.setExp(rpgInfo.getExp() + Math.abs(exp));
        Component expMessage = Component.text().content("    [+" + DecimalFormats.oneDecimals.format(exp) + " XP]").color(NamedTextColor.GRAY).build();
        player.sendMessage(expMessage);
        checkLevelUp();
    }

    /**
     * Check if a player should level up
     */
    public void checkLevelUp() {
        int startLevel = rpgInfo.getLevel();
        int nextLevel = rpgInfo.getLevel();
        while (rpgInfo.getExp() >= RPGConstants.LEVEL_EXP_MAP.get(nextLevel) && nextLevel < 100) {
            rpgInfo.setExp(Math.max(0, rpgInfo.getExp() - RPGConstants.LEVEL_EXP_MAP.get(nextLevel)));
            nextLevel+=1;

            if (nextLevel % 5 == 0) {
                rpgInfo.setSkillpoints(rpgInfo.getSkillpoints() + 1);
            }
        }
        if (nextLevel > startLevel) {
            rpgInfo.setLevel(nextLevel);
            levelUpRewards(startLevel, nextLevel);
        }

        if (rpgInfo.getLevel() == MAX_LEVEL && rpgInfo.getExp() > RPGConstants.LEVEL_EXP_MAP.get(rpgInfo.getLevel())) {
            rpgInfo.setExp(RPGConstants.LEVEL_EXP_MAP.get(rpgInfo.getLevel()));
        }
    }

    /**
     * Celebrate the player leveling up!
     */
    public void levelUpRewards(int startLevel, int nextLevel) {
        player.getWorld().playSound(player, Sound.UI_TOAST_CHALLENGE_COMPLETE, 1.0F, 1.0F);

        Component maintitle = Component.text("LEVEL UP", NamedTextColor.YELLOW, TextDecoration.BOLD);
        Component subtitle = Component.text((startLevel), NamedTextColor.GOLD)
                .append(Component.text(" -> ", NamedTextColor.GRAY))
                .append(Component.text((nextLevel), NamedTextColor.GOLD));
        Title title = Title.title(maintitle, subtitle, Title.Times.times(Ticks.duration(30), Ticks.duration(70), Ticks.duration(40)));

        player.showTitle(title);

        //fullHeal();
    }

    /**
     * Healer a player to their max health and restore their mana
     */
    public void fullHeal() {
        if (!player.isDead()) {
            AttributeInstance hp = Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH));
            player.setHealth(hp.getBaseValue());
            stats.setMana(getMaxMana());
        }
    }

    /**
     * Update attributes of a player based on their RPG stats
     */
    public void updateStats() {
        if (!player.isDead()) {
            AttributeInstance hp = Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH));
            if (getMaxHP() != hp.getBaseValue()) {
                double previousHPPercent = player.getHealth() / hp.getBaseValue();
                hp.setBaseValue(getMaxHP());
                player.setHealth(Math.min(previousHPPercent * getMaxHP(), getMaxHP()));
            }


        }
    }

    public void updateMana() {
        if (!player.isDead()) {
            stats.setMana(Math.min(stats.getMana() + getManaRegen(), getMaxMana()));
        }
    }

    /**
     * Get a list of castable skills
     * @return list of castable skills
     */
    public List<Skill> getCastableSkills() {
        List<Skill> castableSkills = new ArrayList<>();
        for (Skill s : rpgInfo.getSkillsList()) {
            // if s is in the skillbar set of 4 usables?
            castableSkills.add(s);
        }
        return castableSkills;
    }
}
