package dev.muffin.rpgcore.rpg.damage;

import org.bukkit.entity.Entity;

public record DamageInstance(Entity target, PhysicalDamageInstance physicalDamageInstance, MagicDamageInstance magicDamageInstance) {

}
