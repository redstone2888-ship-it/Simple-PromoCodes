package org.simplepc.simplePromoCodes;

import org.bukkit.plugin.java.JavaPlugin;

public final class SimplePromoCodes extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getLogger().info("Simple PromoCodes has been enabled!");

        getCommand("simplepc").setExecutor(new MainCommand(this));
        getCommand("code").setExecutor(new RedeemCode(this));
    }

    @Override
    public void onDisable() {
        saveConfig();
        getLogger().info("Simple PromoCodes has been disabled!");
    }
}
