package com.nexuswhitelist.verification;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Verification extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Utils.readConfig(getConfig(),this);
        Token token = new Token();
        Objects.requireNonNull(this.getCommand("link")).setExecutor(token);
        Bukkit.getServer().getPluginManager().registerEvents(new StopPlayer(), this);
        Bukkit.getServer().getPluginManager().registerEvents(token, this);
        getLogger().info("Minecraft Account Verification Enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("Minecraft Account Verification Disabled");
    }
}
