package net.doodcraft.oshcon.bukkit.enderpads.config;

import net.doodcraft.oshcon.bukkit.enderpads.EnderPadsPlugin;
import net.doodcraft.oshcon.bukkit.enderpads.api.EnderPadAPI;
import net.doodcraft.oshcon.bukkit.enderpads.util.StaticMethods;

import java.io.File;
import java.util.List;

public class Settings {
    public static Boolean colorfulLogging;
    public static Boolean debug;
    public static Boolean logUse;
    public static int defaultMax;
    public static String centerMaterial;
    public static List<String> blackListedBlocks;
    public static Boolean safeTeleport;
    public static int playerCooldown;
    public static Boolean lightningCreate;
    public static Boolean lightningDestroy;
    public static Boolean lightningUse;
    public static String pluginPrefix;
    public static String noPermission;
    public static String atMaximum;
    public static String cooldownMessage;

    public Settings() {
    }

    public static void setupDefaults() {
        colorfulLogging = Boolean.valueOf(true);
        debug = Boolean.valueOf(false);
        logUse = Boolean.valueOf(true);
        defaultMax = 2;
        centerMaterial = "OBSIDIAN~0";
        blackListedBlocks = new java.util.ArrayList();
        blackListedBlocks.add("GRASS");
        blackListedBlocks.add("DIRT");
        safeTeleport = Boolean.valueOf(true);
        playerCooldown = 6;
        lightningCreate = Boolean.valueOf(true);
        lightningDestroy = Boolean.valueOf(true);
        lightningUse = Boolean.valueOf(false);

        pluginPrefix = "&8[&5传送板&8]&r";
        noPermission = "&c你没有权限.";
        atMaximum = "&cYou cannot build any more EnderPads.";
        cooldownMessage = "&cThis EnderPad is not ready! &e[<remaining>ms]";


        Configuration config = new Configuration(EnderPadsPlugin.plugin.getDataFolder() + File.separator + "config.yml");
        Configuration locale = new Configuration(EnderPadsPlugin.plugin.getDataFolder() + File.separator + "locale.yml");
        config.add("General.ColorfulLogging", colorfulLogging);
        config.add("General.DebugMessages", debug);
        config.add("LogUse", logUse);
        config.add("Cooldown", Integer.valueOf(playerCooldown));
        config.add("SkipBlockedPads", safeTeleport);
        config.add("DefaultMax", Integer.valueOf(defaultMax));
        config.add("CenterMaterial", centerMaterial);
        config.add("Blacklist.Materials", blackListedBlocks);
        config.add("Effects.Lightning.OnCreate", lightningCreate);
        config.add("Effects.Lightning.OnDestroy", lightningDestroy);
        config.add("Effects.Lightning.OnUse", lightningUse);
        locale.add("General.PluginPrefix", pluginPrefix);
        locale.add("General.NoPermission", noPermission);
        locale.add("AtMaximum", atMaximum);
        locale.add("Cooldown", cooldownMessage);
        config.save();
        locale.save();
        setNewConfigValues(config);
        setNewLocaleValues(locale);
    }

    public static void setNewConfigValues(Configuration config) {
        colorfulLogging = Boolean.valueOf(config.getBoolean("General.ColorfulLogging"));
        debug = Boolean.valueOf(config.getBoolean("General.DebugMessages"));
        logUse = Boolean.valueOf(config.getBoolean("LogUse"));
        playerCooldown = config.getInteger("Cooldown");
        safeTeleport = Boolean.valueOf(config.getBoolean("SkipBlockedPads"));
        defaultMax = config.getInteger("DefaultMax");
        centerMaterial = config.getString("CenterMaterial");
        blackListedBlocks = config.getStringList("Blacklist.Materials");
        lightningCreate = Boolean.valueOf(config.getBoolean("Effects.Lightning.OnCreate"));
        lightningDestroy = Boolean.valueOf(config.getBoolean("Effects.Lightning.OnDestroy"));
        lightningUse = Boolean.valueOf(config.getBoolean("Effects.Lightning.OnUse"));
    }

    public static void setNewLocaleValues(Configuration locale) {
        pluginPrefix = locale.getString("General.PluginPrefix");
        noPermission = locale.getString("General.NoPermission");
        atMaximum = locale.getString("AtMaximum");
        cooldownMessage = locale.getString("Cooldown");
    }

    public static boolean reload() {
        ConfigurationReloadEvent event = new ConfigurationReloadEvent(EnderPadsPlugin.plugin);
        org.bukkit.Bukkit.getPluginManager().callEvent(event);

        if (!event.isCancelled()) {
            try {
                Configuration config = new Configuration(EnderPadsPlugin.plugin.getDataFolder() + File.separator + "config.yml");
                Configuration locale = new Configuration(EnderPadsPlugin.plugin.getDataFolder() + File.separator + "locale.yml");
                setNewConfigValues(config);
                setNewLocaleValues(locale);
                EnderPadAPI.verifyAllTelepads();
                return false;
            } catch (Exception ex) {
                ex.printStackTrace();
                return true;
            }
        }
        StaticMethods.debug("ConfigurationReloadEvent was cancelled.");
        return false;
    }
}
