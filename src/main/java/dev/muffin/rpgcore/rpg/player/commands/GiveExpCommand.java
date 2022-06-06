package dev.muffin.rpgcore.rpg.player.commands;

import dev.muffin.rpgcore.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GiveExpCommand implements CommandExecutor {

    private Main plugin;

    public GiveExpCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0 || args.length == 1) {
            sender.sendMessage(Component.text("Usage: /giveexp <player> <exp>"));
        } else {
            if (Bukkit.getPlayer(args[0]) != null) {
                Player p = Bukkit.getPlayer(args[0]);
                try {
                    double exp = Double.parseDouble(args[1]);
                    plugin.getRPGPlayer(p).getPlayerClass().addExp(exp);
                    sender.sendMessage(Component.text("Giving player " + p.getName() + " " + exp + " exp.", NamedTextColor.YELLOW));
                    return true;
                } catch (NumberFormatException e) {
                    sender.sendMessage(Component.text("Invalid EXP Argument", NamedTextColor.RED));
                }
            } else {
                sender.sendMessage(Component.text("Invalid Player", NamedTextColor.RED));
            }
        }
        return false;
    }
}
