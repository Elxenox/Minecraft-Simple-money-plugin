package fr.xenox.simpleEconomy;

import org.bukkit.plugin.java.JavaPlugin;

public final class SimpleEconomy extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("SimpleEconomy is starting...");

        // Core logic


        getLogger().info("SimpleEconomy plugin has been successfully enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("SimpleEconomy plugin has been successfully disabled!");
    }
}
