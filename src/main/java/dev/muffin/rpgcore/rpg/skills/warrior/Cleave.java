package dev.muffin.rpgcore.rpg.skills.warrior;

import dev.muffin.rpgcore.rpg.utils.RPGSymbols;
import dev.muffin.rpgcore.rpg.skills.Skill;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class Cleave extends Skill {

    public Cleave() {
        super("Cleave", "Swing powerfully in an arc.", 7.0, 20, 1, 1);
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
