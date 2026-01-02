package fr.xenox.simpleEconomy;

import org.bukkit.plugin.java.JavaPlugin;
import fr.xenox.simpleEconomy.managers.EconomyManager;
import fr.xenox.simpleEconomy.managers.DataManager;

public final class SimpleEconomy extends JavaPlugin {

    // Instance unique du plugin -S ingleton
    private static SimpleEconomy instance;

    // Managers
    private EconomyManager economyManager;
    private DataManager dataManager;

    @Override
    public void onEnable() {
        getLogger().info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        getLogger().info("$$ SimpleEconomy is starting...  $$");
        getLogger().info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

        // Core logic
        instance = this;

        economyManager = new EconomyManager();
        getLogger().info("$ - Economie manager initialisé");



        getLogger().info("$$ - SimpleEconomy plugin has been successfully enabled! $$");
    }

    @Override
    public void onDisable() {
        getLogger().info("SimpleEconomy plugin has been successfully disabled!");
    }

    // Double méthode pour accéder a plugin depuis n'importe où
    public static SimpleEconomy getInstance() {
        return instance;
    }

    public EconomyManager getEconomyManager() {
        return economyManager;
    }
}
