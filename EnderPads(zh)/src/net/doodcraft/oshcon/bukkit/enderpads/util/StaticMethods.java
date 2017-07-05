//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package net.doodcraft.oshcon.bukkit.enderpads.util;

import net.doodcraft.oshcon.bukkit.enderpads.EnderPadsPlugin;
import net.doodcraft.oshcon.bukkit.enderpads.config.Settings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Level;
import java.util.logging.Logger;

public class StaticMethods {
    public StaticMethods() {
    }

    public static boolean isVanished(Player player) {
        if (player == null) {
            return false;
        } else {

            if (Compatibility.isHooked("Essentials")) {
                try {
                    if (Compatibility.essentials.getVanishedPlayers().contains(player.getName())) {
                        return true;
                    }
                } catch (NoSuchMethodError var2) {
                    log("&cThere was an error using Essentials for Vanish. It may not exist?");
                    log(var2.getLocalizedMessage());
                    log("&cCheck your Essentials version.");
                    return false;
                }
            }
            return false;
        }
    }

    public static Boolean hasPermission(Player player, String node, Boolean sendError) {
        if (player.isOp()) {
            return Boolean.valueOf(true);
        } else if (player.hasPermission(EnderPadsPlugin.plugin.getName().toLowerCase() + ".*")) {
            return Boolean.valueOf(true);
        } else if (player.hasPermission(node)) {
            return Boolean.valueOf(true);
        } else {
            if (sendError.booleanValue()) {
                player.sendMessage(addColor(Settings.pluginPrefix + " &r" + Settings.noPermission));
            }

            return Boolean.valueOf(false);
        }
    }

    public static void log(String message) {
        try {
            message = Settings.pluginPrefix + " &r" + message;
            sendConsole(message);
        } catch (Exception var3) {
            Logger logger = Bukkit.getLogger();
            logger.log(Level.INFO, removeColor("[传送板] " + message));
        }

    }

    public static void debug(String message) {
        try {
            if (Settings.debug.booleanValue()) {
                message = "&8[&dDEBUG&8] &e" + message;
                log(message);
            }
        } catch (Exception var3) {
            Logger logger = Bukkit.getLogger();
            logger.log(Level.INFO, removeColor("[传送板] [DEBUG] " + message));
        }

    }

    private static void sendConsole(String message) {
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

        try {
            if (Settings.colorfulLogging.booleanValue()) {
                console.sendMessage(addColor(message));
            } else {
                console.sendMessage(removeColor(addColor(message)));
            }
        } catch (Exception var3) {
            console.sendMessage(removeColor(addColor(message)));
        }

    }

    public static String addColor(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    private static String removeColor(String message) {
        message = addColor(message);
        return ChatColor.stripColor(message);
    }
}
