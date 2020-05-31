package com.nexuswhitelist.verification;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

class Utils {
    private static FileConfiguration cfg;
    private static Plugin plugin;
    private static String token;

    static void readConfig(FileConfiguration cfg, Plugin plugin) {
        Utils.cfg = cfg;
        Utils.plugin = plugin;
        Utils.token = cfg.getString("token");

    }

    static String getToken() {
        return Utils.token;
    }

    static void sendMsg(CommandSender player, String msg) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }
}
