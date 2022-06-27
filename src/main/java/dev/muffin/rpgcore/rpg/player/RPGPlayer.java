package dev.muffin.rpgcore.rpg.player;

import dev.muffin.rpgcore.Main;
import dev.muffin.rpgcore.rpg.damage.DamageInstance;
import dev.muffin.rpgcore.rpg.damage.DamageStack;
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
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.ArrayList;
import java.util.Objects;
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
    private final DamageStack damageStack;
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
        damageStack = new DamageStack();
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
    public DamageStack getDamageStack() {
        return damageStack;
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
        getSkillsGUI().openSkillsGui(skillList.getEquippedSkills(), this);
    }

    public void showSelectableSkillsGUI(int slot) {
        getSkillsGUI().setSkillSelectGui(skillList.getUnlockedSkills(), slot, this);
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
        CastResponse castResponse = getSkillCaster().cast(skill, this);
        return castResponse;
    }

    /**
     * Get a list of castable skills
     * @return list of castable skills
     */
    public Skill[] getCastableSkills() {
        return skillList.getEquippedSkills();
    }

    // Damage Related

    /**
     * Handle basic attack to a target
     * Needs to be called after event to be buffered properly
     * Difference is it does not need to damage again
     * @param damageInstance the damage Instance
     */
    public void bufferBasicAttack(DamageInstance damageInstance) {
        bufferDamageInstance(damageInstance);
    }

    /**
     * Buffer a regular damage instance
     * Will run its own damage method
     * @param damageInstance the damage instance
     */
    public void bufferDamageInstance(DamageInstance damageInstance) {
        damageStack.bufferDamage(damageInstance);
        doDamage(damageInstance.getTotalDamage(), damageInstance.getTarget(), damageInstance.isKnockback());
    }

    public void doDamage(double damage, LivingEntity target, boolean knockback) {
        double knockbackResistance = Objects.requireNonNull(target.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE)).getBaseValue();
        if (!knockback) {
            Objects.requireNonNull(target.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE)).setBaseValue(1.0);
        }
        target.setNoDamageTicks(0);
        target.damage(damage, getPlayer());
        Objects.requireNonNull(target.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE)).setBaseValue(knockbackResistance);
    }

}
