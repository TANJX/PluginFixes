//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.gmail.mikeundead;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseHandler {
    private PvPTitles pvpTitles;
    private int 声望;
    private Map<Integer, String> rankList = new HashMap();
    private Map<Integer, Integer> reqFame = new HashMap();
    public ChatColor PrefixColor;
    private String tag;
    private boolean chat;

    public DatabaseHandler(PvPTitles pvpTitles) {
        this.pvpTitles = pvpTitles;
        this.SaveConfig();
    }

    public int PlayerFame() {
        return this.声望;
    }

    public Map<Integer, String> RankList() {
        return this.rankList;
    }

    public Map<Integer, Integer> reqFame() {
        return this.reqFame;
    }

    public String getTag() {
        return this.tag;
    }

    public boolean getChat() {
        return this.chat;
    }

    public void SavePlayerFame(String playername, int fame) {
        File file = new File(this.pvpTitles.getDataFolder() + File.separator + "players" + File.separator + playername + ".yml");
        if(!file.exists()) {
            this.pvpTitles.getDataFolder().mkdir();

            try {
                file.createNewFile();
            } catch (Exception var7) {
                var7.printStackTrace();
            }
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set("fame", Integer.valueOf(fame));

        try {
            config.save(file);
        } catch (Exception var6) {
            var6.printStackTrace();
        }

    }

    public void LoadPlayerFame(String playername) {
        File file = new File(this.pvpTitles.getDataFolder() + File.separator + "players" + File.separator + playername + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        this.声望 = config.getInt("fame");
    }

    public void FirstRun(String playername) {
        File file = new File(this.pvpTitles.getDataFolder() + File.separator + "players" + File.separator + playername + ".yml");
        if(!file.exists()) {
            this.pvpTitles.getDataFolder().mkdirs();
            YamlConfiguration config = new YamlConfiguration();
            config.set("fame", Integer.valueOf(0));

            try {
                config.save(file);
            } catch (IOException var5) {
                var5.printStackTrace();
            }
        }

    }

    public void SaveConfig() {
        File file = new File(this.pvpTitles.getDataFolder(), "config.yml");
        if(!file.exists()) {
            this.pvpTitles.getDataFolder().mkdirs();
            YamlConfiguration config = new YamlConfiguration();
            String[] ranks = new String[]{"一等兵", "二等兵", "上等兵", "下士", "中士", "上士", "少尉", "中尉", "上尉", "少校", "中将", "上将", "少将校", "中将校", "大将校", "总统"};
            Integer[] reqfame = new Integer[]{Integer.valueOf(0), Integer.valueOf(25), Integer.valueOf(75), Integer.valueOf(180), Integer.valueOf(360), Integer.valueOf(600), Integer.valueOf(1000), Integer.valueOf(1680), Integer.valueOf(2800), Integer.valueOf(4665), Integer.valueOf(7750), Integer.valueOf(12960), Integer.valueOf(21600), Integer.valueOf('負'), Integer.valueOf('\uea60'), Integer.valueOf(100000)};
            config.set("Tag", "§a声望");
            config.set("PrefixColor", "green");
            config.set("Chat", Boolean.valueOf(true));
            config.set("RankNames", Arrays.asList(ranks));
            config.set("ReqFame", Arrays.asList(reqfame));

            try {
                config.save(file);
            } catch (IOException var6) {
                var6.printStackTrace();
            }
        }

    }

    public void LoadConfig() {
        File file = new File(this.pvpTitles.getDataFolder(), "config.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        List<String> configList = config.getStringList("RankNames");

        for(int derp = 0; derp < configList.size(); ++derp) {
            this.rankList.put(Integer.valueOf(derp), configList.get(derp));
        }

        List<Integer> var6 = config.getIntegerList("ReqFame");

        for(int i = 0; i < var6.size(); ++i) {
            this.reqFame.put(i, var6.get(i));
        }

        this.GetPrefixColor(config.getString("PrefixColor"));
        this.tag = config.getString("Tag");
        this.chat = config.getBoolean("Chat");
        if(configList.size() != var6.size()) {
            this.pvpTitles.log.info("WARNING - RankNames and ReqFame are not equal in their numbers.");
            this.pvpTitles.log.info("WARNING - RankNames and ReqFame are not equal in their numbers.");
            this.pvpTitles.log.info("WARNING - RankNames and ReqFame are not equal in their numbers.");
        }

    }

    private void GetPrefixColor(String color) {
        this.PrefixColor = ChatColor.valueOf(color.toUpperCase());
    }
}
