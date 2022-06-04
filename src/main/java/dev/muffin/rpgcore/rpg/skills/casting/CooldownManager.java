package dev.muffin.rpgcore.rpg.skills.casting;

import dev.muffin.rpgcore.rpg.skills.Skill;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CooldownManager {

    private Map<Skill, Long> cooldowns;

    public CooldownManager() {
        cooldowns = new HashMap<>();
    }

    /**
     * Put a skill on cooldown based on current time
     * @param s the skill
     */
    public void putOnCooldown(Skill s) {
        cooldowns.put(s, System.currentTimeMillis());
    }

    /**
     * Check if a skill is on cooldown
     * @param s the skill
     * @return true if on cooldown
     */
    public boolean isOnCooldown(Skill s) {
        if (cooldowns.containsKey(s)) {
            long diff = System.currentTimeMillis() - cooldowns.get(s);
            return (s.getCooldown() > diff * 0.001);
        }
        return false;
    }

    /**
     * Get cooldown of a skill
     * @param s the skill
     * @return cooldown in seconds
     */
    public double getCooldown(Skill s) {
        if (cooldowns.containsKey(s)) {
            long diff = System.currentTimeMillis() - cooldowns.get(s);
            return s.getCooldown() - (diff * 0.001);
        }
        return -1;
    }

    /**
     * Remove all cooldowns that are up
     */
    public void updateCooldowns() {
        List<Skill> remove = new ArrayList<>();
        for (Skill s : cooldowns.keySet()) {
            long diff = System.currentTimeMillis() - cooldowns.get(s);
            if (s.getCooldown() <= diff * 0.001) {
                remove.add(s);
            }
        }
        for (Skill s : remove) {
            cooldowns.remove(s);
        }
    }

}
