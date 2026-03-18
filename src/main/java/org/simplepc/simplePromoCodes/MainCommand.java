package org.simplepc.simplePromoCodes;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class MainCommand implements CommandExecutor {
    private final JavaPlugin plugin;
    public MainCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    private final String prefix = "§8[§9SPC§8] §r";

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("simplepc.edit")) {
            sender.sendMessage(prefix + "§2Running §9Simple PromoCodes v1.0");
            sender.sendMessage(prefix + "§2You dont't have permission to edit settings.");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(prefix + "§cIncorrect command. Write '/simplepc help' for help");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload":
                plugin.reloadConfig();
                sender.sendMessage(prefix + "§aConfig reloaded!");
                break;
            case "help":
                sender.sendMessage("§9Simple PromoCodes commands:");
                sender.sendMessage("§9/simplepc help §f- Show help");
                sender.sendMessage("§9/simplepc reload §f- Reload config");
                sender.sendMessage("§9/simplepc activate <name> <true/false> §f- Activate or deactivate promo-code");
                sender.sendMessage("§9/code <code> §f - Redeem code");
                break;
            case "activate":
                if (args.length < 3) {
                    sender.sendMessage(prefix + "§cUsage: /simplepc activate <promo> <true/false>");
                    return true;
                }

                String promo = args[1];
                String active = args[2];

                boolean valid;

                if (active.equalsIgnoreCase("true")) {
                    valid = true;
                } else if (active.equalsIgnoreCase("false")) {
                    valid = false;
                } else {
                    sender.sendMessage(prefix + "§cUsage: /simplepc activate <promo> <true/false>");
                    return true;
                }

                if (plugin.getConfig().getConfigurationSection("promos." + promo) == null) {
                    sender.sendMessage(prefix + "§cPromocode " + promo + " doesn't exist!");
                    return true;
                }

                plugin.getConfig().set("promos." + promo + ".valid", valid);
                plugin.saveConfig();

                sender.sendMessage(prefix + "§aPromocode §c" + promo + " §aset to; §c" + active);
                break;
        }

        return true;
    }
}