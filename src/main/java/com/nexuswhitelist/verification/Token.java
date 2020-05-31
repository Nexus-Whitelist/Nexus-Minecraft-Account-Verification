package com.nexuswhitelist.verification;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Token implements CommandExecutor, Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        String token = getToken(player);
        if (token != null) {
            Utils.sendMsg(player, "Your Minecraft token is: " + token);
            Utils.sendMsg(player, "Please use this token on the Nexus Whitelist!");
        } else {
            Utils.sendMsg(player, "Something went wrong. Try doing /link");
        }
        e.setJoinMessage(null);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            String token = getToken(player);
            if (token != null) {
                Utils.sendMsg(player, "Your Minecraft token is: " + token);
                Utils.sendMsg(player, "Please use this token on the Nexus Whitelist!");
                return true;
            } else {
                Utils.sendMsg(player, "Something went wrong. Try again. If this issue keeps occurring, email nexuswhitelist@gmail.com");
                return true;
            }
        } else {
            Utils.sendMsg(sender, "This command must be done by a player!");
            return false;
        }
    }

    private String getToken(Player player) {
        try {
            URL url = new URL("https://api.nexuswhitelist.com/token/minecraft/generate?minecraftId=" + player.getUniqueId().toString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.addRequestProperty("AUTHORIZATION", "Bearer " + Utils.getToken());
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-GB; rv:1.9.2.13) Gecko/20101203 Firefox/3.6.13 (.NET CLR 3.5.30729)");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();
            System.out.println(content.toString());
            JSONObject myResponse = new JSONObject(content.toString());
            return myResponse.getString("minecraftToken");
        } catch (IOException | JSONException error) {
            error.printStackTrace();
            return null;
        }
    }
}