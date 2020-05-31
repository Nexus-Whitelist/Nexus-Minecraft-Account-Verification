package com.nexuswhitelist.verification;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Verification extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Utils.readConfig(getConfig(),this);
        Objects.requireNonNull(this.getCommand("link")).setExecutor(new Commands());
        getLogger().info("Minecraft Account Verification Enabled");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
