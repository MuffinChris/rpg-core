package dev.muffin.rpgcore.rpg.skills.warrior;

import dev.muffin.rpgcore.rpg.skills.abstracts.Skill;
import dev.muffin.rpgcore.rpg.utils.constants.RPGSymbols;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static dev.muffin.rpgcore.rpg.skills.warrior.WarriorSkillTextures.*;

public class Cleave extends Skill {

    public Cleave() {
        super("Cleave", 7.0, 20, 1, 1,
                CLEAVE_TEXTURE_UNLOCKED, CLEAVE_TEXTURE_UNLOCKABLE, CLEAVE_TEXTURE_LOCKED);
    }

    @Override
    public List<String> getDescription(Player caster) {
        List<String> description = new ArrayList<>();
        description.add("&7Swing in a wide arc in front of you.");
        description.add("&7Deals &c60% ATK &7as &c" + RPGSymbols.SLASH_DAMAGE.content() + " Slash &7damage.");
        return description;
    }

    @Override
    public void castSkill(Player caster) {
        caster.getWorld().playSound(caster, Sound.ENTITY_ENDER_DRAGON_FLAP, 1.2F, 0.25F);
        for (Entity e : caster.getNearbyEntities(caster.getLocation().getX(), caster.getLocation().getY(), caster.getLocation().getZ())) {
            if (e instanceof LivingEntity && e != caster && e.getLocation().distance(caster.getLocation()) <= 3) {
                ((LivingEntity) e).damage(25);
                caster.getWorld().playSound(e, Sound.ENTITY_IRON_GOLEM_ATTACK, 1.0F, 1.0F);
            }
        }
    }
}
