package dev.muffin.rpgcore.rpg.player;

import dev.muffin.rpgcore.Main;
import dev.muffin.rpgcore.rpg.skills.Skill;
import dev.muffin.rpgcore.rpg.skills.casting.CastResponse;
import dev.muffin.rpgcore.rpg.skills.casting.SkillCaster;
import dev.muffin.rpgcore.rpg.skills.casting.Skillbar;
import dev.muffin.rpgcore.rpg.utils.RPGLevelInfo;
import dev.muffin.rpgcore.utilities.PluginLogger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

import static dev.muffin.rpgcore.rpg.utils.constants.RPGConstants.BASE_LEVEL;

/**
 * RPGPlayer is attached to each player. Stores all rpg-core related information
 */
public class RPGPlayer {

    private final UUID playerUUID;
    private final PlayerClass playerClass;
    private final Skillbar skillbar;
    private final SkillCaster skillCaster;

    private final InventoryManager inventoryManager;

    public RPGPlayer(Player p) {
        PluginLogger.getLogger().info("Creating RPGPlayer for " + p.getName() + ".");
        playerUUID = p.getUniqueId();
        playerClass = new PlayerClass(p, Main.getInstance().getClassHandler().getWarrior(),
                new RPGLevelInfo(BASE_LEVEL, 0, 1), new ArrayList<>());
        skillbar = new Skillbar(p);
        skillCaster = new SkillCaster(p);
        inventoryManager = new InventoryManager(p);
    }

    /**
     * Get UUID of owner of the RPGPlayer
     * @return UUID of owner
     */
    public UUID getUUID() {
        return playerUUID;
    }

    /**
     * Get Player of the RPGPlayer
     * @return the player
     */
    public Player getPlayer() {
        return Bukkit.getPlayer(playerUUID);
    }

    /**
     * Get the playerClass object of the RPGPlayer
     * @return the playerClass
     */
    public PlayerClass getPlayerClass() {
        return playerClass;
    }

    /**
     * Get player's skillbar
     * @return skillbar
     */
    public Skillbar getSkillbar() {
        return skillbar;
    }

    /**
     * Get skill caster
     * @return skill caster
     */
    public SkillCaster getSkillCaster() {
        return skillCaster;
    }

    /**
     * Update any gameplay related stats for the player
     */
    public void updatePlayerInfo() {
        playerClass.updateStats();
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    /**
     * Safe-close of RPGPlayer (save data, etc)
     */
    public void close() {
        inventoryManager.restoreFromBottomInventory();
        //TODO: close
    }

    // Mediated Functions

    // GUI Related
    public void preloadFullGUI() {
        inventoryManager.saveBottomInventory();
        inventoryManager.clearPlayerBottomInventory();
    }

    public void showWarriorInventory() {
        preloadFullGUI();
        getPlayer().openInventory(getPlayerClass().getSkillTree().getWarriorInventory());
    }

    // Skillbar Related

    public void enableSkillbar() {
        skillbar.enable();
        updateSkillbar();
    }

    public void disableSkillbar() {
        skillbar.disable();
        updateSkillbar();
    }

    public void updateSkillbar() {
        skillbar.updateSkillbar(getPlayerClass().getCastableSkills(), skillCaster.getCooldownManager(), getPlayerClass().getStats().getMana());
    }

    // Skill Related

    public CastResponse castSkill(Skill skill) {
        CastResponse castResponse = getSkillCaster().cast(skill, playerClass);
        return castResponse;
    }

}
