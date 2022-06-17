package dev.muffin.rpgcore.rpg.skills.warrior;

import dev.muffin.rpgcore.rpg.skills.abstracts.AugmentedSkill;
import dev.muffin.rpgcore.rpg.skills.abstracts.Skill;
import dev.muffin.rpgcore.rpg.utils.constants.RPGSymbols;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static dev.muffin.rpgcore.rpg.skills.warrior.WarriorSkillTextures.*;

public class BrutalSlam extends AugmentedSkill {
    public BrutalSlam(Skill toModify) {
        super(toModify,"Brutal Slam", 8.0, 35, 1, 3,
                CLEAVE_TEXTURE_UNLOCKED, CLEAVE_TEXTURE_UNLOCKABLE, CLEAVE_TEXTURE_LOCKED);
    }

    @Override
    public List<String> getDescription(Player caster) {
        List<String> description = new ArrayList<>();
        description.add("&7Slam your weapon into nearby opponents.");
        description.add("&7Deals &c100% ATK &7as &c" + RPGSymbols.IMPACT_DAMAGE.content() + " Impact &7damage.");
        description.add("&7Knocks back hit opponents.");
        return description;
    }

    @Override
    public void castSkill(Player caster) {
        caster.getWorld().playSound(caster, Sound.BLOCK_ANVIL_PLACE, 1.2F, 0.8F);
        for (Entity e : caster.getNearbyEntities(caster.getLocation().getX(), caster.getLocation().getY(), caster.getLocation().getZ())) {
            if (e instanceof LivingEntity && e != caster && e.getLocation().distance(caster.getLocation()) <= 3) {
                ((LivingEntity) e).damage(30);
                caster.getWorld().playSound(e, Sound.ENTITY_IRON_GOLEM_ATTACK, 1.0F, 1.0F);
            }
        }
    }
}
