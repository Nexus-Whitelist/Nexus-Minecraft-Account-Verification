package com.nexuswhitelist.verification;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class Utils {
    private static FileConfiguration cfg;
    private static Plugin plugin;
    private static String token;

    public static void readConfig(FileConfiguration cfg, Plugin plugin) {
        Utils.cfg = cfg;
        Utils.plugin = plugin;
        token = cfg.getString("token");
    }

    public static String getToken() {
        return Utils.token;
    }

    public static void sendMsg(CommandSender player, String msg) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }
}
