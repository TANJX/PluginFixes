package net.doodcraft.oshcon.bukkit.enderpads;

import net.doodcraft.oshcon.bukkit.enderpads.api.EnderPad;
import net.doodcraft.oshcon.bukkit.enderpads.config.Settings;
import net.doodcraft.oshcon.bukkit.enderpads.util.Compatibility;
import net.doodcraft.oshcon.bukkit.enderpads.util.StaticMethods;
import org.bukkit.Bukkit;
import org.bukkit.block.BlockFace;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class EnderPadsPlugin extends JavaPlugin {
    public static Plugin plugin;
    public static String version;
    public static Random random;
    public static BlockFace[] faces = {BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};
    public static Map<String, Long> playerCooldowns = new HashMap();
    public static Map<String, EnderPad> enderPads = new HashMap();

    public EnderPadsPlugin() {
    }

    public void onEnable() {
        long start = System.currentTimeMillis();
        version = Bukkit.getBukkitVersion().split("-")[0];
        plugin = this;
        random = new Random();
        Settings.setupDefaults();
        Compatibility.checkHooks();
        registerListeners();
        setExecutors();
        if (!Compatibility.isSupported(version, "1.7.10", "1.12")) {
            StaticMethods.log("&cThis version of Minecraft has not been tested with EnderPads. Avoid using this in production. Support will not be given.");
        }
        long finish = System.currentTimeMillis();
        StaticMethods.log("&aEnderPads v" + plugin.getDescription().getVersion() + " is now loaded. &e(" + (finish - start) + "ms)");
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            public void run() {
            }
        }, 1L);
    }

    public void registerListeners() {
        registerEvents(plugin, new Listener[]{new net.doodcraft.oshcon.bukkit.enderpads.listeners.PlayerListener()});
        registerEvents(plugin, new Listener[]{new net.doodcraft.oshcon.bukkit.enderpads.listeners.EntityListener()});
        registerEvents(plugin, new Listener[]{new net.doodcraft.oshcon.bukkit.enderpads.listeners.BlockListener()});
        if (Compatibility.isSupported(version, "1.8", "2.0")) {
            registerEvents(plugin, new Listener[]{new net.doodcraft.oshcon.bukkit.enderpads.listeners.BlockExplodeListener()});
        }
    }

    public static void registerEvents(Plugin plugin, Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }

    public void setExecutors() {
        getCommand("enderpads").setExecutor(new EnderPadsCommand());
    }
}
