package com.nexuswhitelist.verification;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;


public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            try {
                URL url = new URL("https://api.nexuswhitelist.com/minecraft/generate");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                Map<String, String> parameters = new HashMap<>();
                parameters.put("minecraftId", player.getUniqueId().toString());
                con.setRequestProperty("Authorization", "Bearer " + Utils.getToken());
                con.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(con.getOutputStream());
                out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
                out.flush();
                out.close();
                con.setConnectTimeout(5000);
                con.setReadTimeout(5000);
                con.setInstanceFollowRedirects(false);
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
                Utils.sendMsg(player, "Your Minecraft token is: " + myResponse.getString("minecraftToken"));
                Utils.sendMsg(player, "Please use this token on the Nexus Whitelist!");
                return true;
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            Utils.sendMsg(sender, "This command must be done by a player!");
            return false;
        }
    }
}