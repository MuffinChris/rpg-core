package dev.muffin.rpgcore.rpg.skills.casting;

import dev.muffin.rpgcore.chat.utils.ChatColor;
import dev.muffin.rpgcore.rpg.skills.Skill;
import dev.muffin.rpgcore.utilities.DecimalFormats;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class Skillbar {

    private UUID uuid;
    private BossBar skillbar;
    private BossBar spacer;
    private boolean skillbarActive;

    public Skillbar(Player p) {
        this.uuid = p.getUniqueId();

        spacer = Bukkit.createBossBar("", BarColor.YELLOW, BarStyle.SOLID);
        spacer.addPlayer(p);
        spacer.setVisible(true);

        skillbar = Bukkit.createBossBar("", BarColor.YELLOW, BarStyle.SOLID);
        skillbar.addPlayer(p);
        skillbar.setVisible(true);

        skillbarActive = false;
    }

    public void updateSkillbar(List<Skill> castableSkills, CooldownManager cooldownManager, double currMana) {
        if (skillbarActive) {
            Player p = Bukkit.getPlayer(uuid);
            String output = "";
            int slot = 2;

            for (Skill skill : castableSkills) {
                String skillName = skill.getSkillName();
                if (cooldownManager.isOnCooldown(skill)) {
                    output += "&7" + skillName + " &8<&f" + DecimalFormats.oneDecimalsZero.format(cooldownManager.getCooldown(skill)) + "s&8> || ";
                } else if (skill.getManaCost() > currMana){
                    output += "&7" + skillName + " &8<&b" + DecimalFormats.noDecimals.format(skill.getManaCost()) + "M&8> || ";
                } else {
                    output += "&e" + skillName + " &8<&6" + slot + "&8> || ";
                }
                slot++;
            }

            if (!output.isEmpty()) {
                output = output.substring(0, output.length() - 4);
            } else {
                output = "&7No Skills Equipped (&e/skills&7)";
            }

            skillbar.setTitle(ChatColor.color(output));
        } else {
            skillbar.setTitle("");
        }
    }

    public void enable() {
        skillbarActive = true;
    }

    public void disable() {
        skillbarActive = false;
    }

    public boolean isActive() {
        return skillbarActive;
    }

}
