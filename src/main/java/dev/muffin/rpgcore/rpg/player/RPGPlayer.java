package dev.muffin.rpgcore.rpg.player;

import dev.muffin.rpgcore.Main;
import dev.muffin.rpgcore.rpg.skills.abstracts.Skill;
import dev.muffin.rpgcore.rpg.skills.SkillList;
import dev.muffin.rpgcore.rpg.skills.skilltree.SkillTree;
import dev.muffin.rpgcore.rpg.skills.skillgui.SkillsGUI;
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
import static dev.muffin.rpgcore.rpg.utils.constants.RPGConstants.NUM_USABLE_SKILLS;

/**
 * RPGPlayer is attached to each player. Mediator for all RPG classes
 */
public class RPGPlayer {

    private final UUID playerUUID;
    private final PlayerClass playerClass;
    private final Skillbar skillbar;
    private final SkillCaster skillCaster;
    private final SkillTree skillTree;
    private final SkillsGUI skillsGUI;
    private final SkillList skillList;
    private final InventoryManager inventoryManager;

    public RPGPlayer(Player p) {
        PluginLogger.getLogger().info("Creating RPGPlayer for " + p.getName() + ".");
        playerUUID = p.getUniqueId();
        playerClass = new PlayerClass(p, Main.getInstance().getClassHandler().getWarrior(),
                new RPGLevelInfo(BASE_LEVEL, 0, 1));
        skillbar = new Skillbar(p);
        skillCaster = new SkillCaster(p);
        inventoryManager = new InventoryManager(p);
        skillTree = new SkillTree(p);
        skillsGUI = new SkillsGUI(p);
        skillList = new SkillList(p, new Skill[NUM_USABLE_SKILLS], new ArrayList<>());
    }

    public UUID getUUID() {
        return playerUUID;
    }
    public Player getPlayer() {
        return Bukkit.getPlayer(playerUUID);
    }
    public PlayerClass getPlayerClass() {
        return playerClass;
    }
    public Skillbar getSkillbar() {
        return skillbar;
    }
    public SkillCaster getSkillCaster() {
        return skillCaster;
    }
    public SkillTree getSkillTree() {
        return skillTree;
    }
    public SkillsGUI getSkillsGUI() {
        return skillsGUI;
    }
    public SkillList getSkillList() {
        return skillList;
    }
    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    /**
     * Update any gameplay related stats for the player
     */
    public void updatePlayerInfo() {
        playerClass.updateStats();
    }

    /**
     * Safe-close of RPGPlayer (save data, etc)
     */
    public void close() {
        PluginLogger.getLogger().info("Closing RPGPlayer for " + getPlayer().getName());
        skillList.getPassiveManager().close();

        PluginLogger.getLogger().info("Restoring bottom Inventory if applicable for " + getPlayer().getName());
        inventoryManager.restoreFromBottomInventory();
    }

    //
    // Mediated Functions
    //

    // GUI Related
    public void preloadFullGUI() {
        inventoryManager.saveBottomInventory();
        inventoryManager.clearPlayerBottomInventory();
    }

    // Skilltree Related

    public void showWarriorInventory() {
        if (getInventoryManager().isOpen()) {
            getPlayer().closeInventory();
        }
        preloadFullGUI();
        getSkillTree().openWarriorInventory(this);
    }

    public void unlockSkill(int skillSlot) {
        getSkillTree().unlockSkill(skillSlot, this);
    }

    // Skills GUI

    public void showSkillsGUI() {
        if (getInventoryManager().isOpen()) {
            getPlayer().closeInventory();
        }
        getSkillsGUI().openSkillsGui(skillList.getEquippedSkills());
    }

    public void showSelectableSkillsGUI(int slot) {
        getSkillsGUI().setSkillSelectGui(skillList.getUnlockedSkills(), slot);
    }

    public void equipSkill(int slot) {
        getSkillList().equipSkill(getPlayer(), getSkillsGUI().getSlotToEquip(), slot);
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
        skillbar.updateSkillbar(getCastableSkills(), skillCaster.getCooldownManager(), getPlayerClass().getCurrentStats().getMana());
    }

    // Skill Related

    public CastResponse castSkill(Skill skill) {
        CastResponse castResponse = getSkillCaster().cast(skill, playerClass);
        return castResponse;
    }

    /**
     * Get a list of castable skills
     * @return list of castable skills
     */
    public Skill[] getCastableSkills() {
        return skillList.getEquippedSkills();
    }

}
