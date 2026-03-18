package org.simplepc.simplePromoCodes;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.ArrayList;

public class RedeemCode implements CommandExecutor {
    private final JavaPlugin plugin;
    public RedeemCode(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    private final String prefix = "§8[§9SPC§8] §r";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cOnly players can use this command!");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(prefix + "§cUsage: /code <code>");
            return true;
        }

        String promo = args[0];

        if (plugin.getConfig().getConfigurationSection("promos." + promo) == null) {
            player.sendMessage(prefix + "§cPromocode " + promo + " doesn't exist!");
            return true;
        }

        if (!plugin.getConfig().getBoolean("promos." + promo + ".valid", false)) {
            player.sendMessage(prefix + "§cPromocode " + promo + " is invalid!");
            return true;
        }

        List<String> used = plugin.getConfig().getStringList("promos." + promo + ".used");
        if (used.contains(player.getName())) {
            player.sendMessage(prefix + "§cYou have already used this promocode!");
            return true;
        }

        String reward = plugin.getConfig().getString("promos." + promo + ".reward");
        if (reward != null) {
            reward = reward.replace("@p", player.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), reward);
        }

        used.add(player.getName());
        plugin.getConfig().set("promos." + promo + ".used", used);
        plugin.saveConfig();

        player.sendMessage(prefix + "§aPromocode activated successfully!");
        return true;
    }
}