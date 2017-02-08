package com.manjason.randomplayer.lang;

import com.manjason.randomplayer.RandomPlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Lang {

    private static RandomPlayer plugin = null;
    private static File f;
    private static FileConfiguration lang;


    static {
        plugin = RandomPlayer.getInstance();
        f = new File(plugin.getDataFolder(), "lang_" + plugin.config.getString("language") + ".yml");
        lang = YamlConfiguration.loadConfiguration(f);
    }

    public static void reload() {
        f = new File(plugin.getDataFolder(), "lang_" + plugin.config.getString("language") + ".yml");
        lang = YamlConfiguration.loadConfiguration(f);
    }

    public static String get(int id) {
        return lang.getString("lang_" + id);
    }
}
