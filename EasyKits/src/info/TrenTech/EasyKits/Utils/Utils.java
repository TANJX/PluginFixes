package info.TrenTech.EasyKits.Utils;

import info.TrenTech.EasyKits.EasyKits;
import info.TrenTech.EasyKits.EasyKitsMod;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

public class Utils {

    private static EasyKitsMod plugin;

    public static void setPlugin(EasyKitsMod plugin) {
        Utils.plugin = plugin;
    }

    public static EasyKitsMod getplugin() {
        return Utils.plugin;
    }

    public static EasyKits getPluginContainer() {
        return Utils.plugin.getPluginContainer();
    }

    public static FileConfiguration getConfig() {
        return Utils.plugin.getConfig();
    }

    public static Logger getLogger() {
        return Utils.plugin.getLogger();
    }

    public static int getTimeInSeconds(String time) {
        String[] times = time.split(" ");
        int seconds = 0;
        for (String t : times) {
            if (t.matches("(\\d+)[s]$")) {
                seconds = Integer.parseInt(t.replace("s", "")) + seconds;
            } else if (t.matches("(\\d+)[m]$")) {
                seconds = (Integer.parseInt(t.replace("m", "")) * 60) + seconds;
            } else if (t.matches("(\\d+)[h]$")) {
                seconds = (Integer.parseInt(t.replace("h", "")) * 3600) + seconds;
            } else if (t.matches("(\\d+)[d]$")) {
                seconds = (Integer.parseInt(t.replace("d", "")) * 86400) + seconds;
            } else if (t.matches("(\\d+)[w]$")) {
                seconds = (Integer.parseInt(t.replace("w", "")) * 604800) + seconds;
            }
        }
        return seconds;
    }

    public static String getReadableTime(int timeInSec) {
        int weeks = timeInSec / 604800;
        int wRemainder = timeInSec % 604800;
        int days = wRemainder / 86400;
        int dRemainder = wRemainder % 86400;
        int hours = dRemainder / 3600;
        int hRemainder = dRemainder % 3600;
        int minutes = hRemainder / 60;
        int seconds = hRemainder % 60;
        String time = null;
        if (weeks > 0) {
            String wks = " Weeks";
            if (weeks == 1) {
                wks = " Week";
            }
            time = weeks + wks;
        }
        if (days > 0 || (days == 0 && weeks > 0)) {
            String dys = " Days";
            if (days == 1) {
                dys = " Day";
            }
            if (time != null) {
                time = time + ", " + days + dys;
            } else {
                time = days + dys;
            }
        }
        if ((hours > 0) || (hours == 0 && days > 0)) {
            String hrs = " Hours";
            if (hours == 1) {
                hrs = " Hour";
            }
            if (time != null) {
                time = time + ", " + hours + hrs;
            } else {
                time = hours + hrs;
            }
        }
        if ((minutes > 0) || (minutes == 0 && days > 0) || (minutes == 0 && hours > 0)) {
            String min = " Minutes";
            if (minutes == 1) {
                min = " Minute";
            }
            if (time != null) {
                time = time + ", " + minutes + min;
            } else {
                time = minutes + min;
            }
        }
        if (seconds > 0) {
            String sec = " Seconds";
            if (seconds == 1) {
                sec = " Second";
            }
            if (time != null) {
                time = time + " and " + seconds + sec;
            } else {
                time = seconds + sec;
            }
        }
        return time;
    }

    public static UUID getUUID(String playerName) {
        UUID uuid = null;
        HashMap<UUID, String> players = EasyKits.players;
        Set<Map.Entry<UUID, String>> keys = players.entrySet();
        for (Entry<UUID, String> key : keys) {
            if (key.getValue().equalsIgnoreCase(playerName)) {
                uuid = key.getKey();
                break;
            }
        }
        return uuid;
    }

}
