package dev.muffin.rpgcore.rpg.skills.warrior;

import dev.muffin.rpgcore.Main;
import dev.muffin.rpgcore.rpg.player.RPGPlayer;
import dev.muffin.rpgcore.rpg.skills.abstracts.PassiveSkill;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Resistance extends PassiveSkill {
    private final List<Player> passives;

    public Resistance() {
        super("Resistance", -1, 1, 0, 0, 0);
        passives = new ArrayList<>();

        new BukkitRunnable() {
            public void run() {
                PotionEffect resistance = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 80, 1);
                for (Player p : passives) {
                    p.addPotionEffect(resistance);
                }
            }
        }.runTaskTimer(Main.getInstance(), 10L, 10L);
    }

    @Override
    public void activate(Player caster) {
        passives.add(caster);
    }

    @Override
    public void deactivate(Player caster) {
        passives.remove(caster);
    }

    @Override
    public void castSkill(RPGPlayer rpgPlayer) {
    }

    @Override
    public List<String> getDescription(RPGPlayer rpgPlayer) {
        List<String> description = new ArrayList<>();
        description.add("&7Permanently gain Resistance 1.");
        return description;
    }
}
