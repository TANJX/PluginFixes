package net.doodcraft.oshcon.bukkit.enderpads.util;

import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;

public class Compatibility {
    public static HashMap<String, Plugin> hooked;
    public static ArrayList<String> warned;
    public static com.earth2me.essentials.Essentials essentials;

    public static void checkHooks() {
        hooked = new HashMap();
        if (hookPlugin("SuperVanish", "5.8.4", "5.9.5")) {
            StaticMethods.log("&bHooked into SuperVanish v" + getPlugin("SuperVanish").getDescription().getVersion() + "!");
        }
        if (hookPlugin("PremiumVanish", "1.9.0", "1.9.5")) {
            StaticMethods.log("&bHooked into PremiumVanish v" + getPlugin("PremiumVanish").getDescription().getVersion() + "!");
        }
        if (hookPlugin("Essentials", "2.0.1", "2.0.1")) {
            essentials = (com.earth2me.essentials.Essentials) getPlugin("Essentials");
            StaticMethods.log("&bHooked into Essentials v" + getPlugin("Essentials").getDescription().getVersion() + "!");
        }
    }

    public static boolean isHooked(String name) {
        if (hooked.get(name) != null) {
            if ((getPlugin(name) != null) && (getPlugin(name).isEnabled())) {
                return true;
            }
            if (!warned.contains(name)) {
                warned.add(name);
                StaticMethods.log("&c" + name + " is hooked, but not enabled anymore.");
            }
            return false;
        }

        return false;
    }

    public static Plugin getPlugin(String name) {
        return (Plugin) hooked.get(name);
    }

    public static boolean hookPlugin(String name, String min, String max) {
        Plugin hook = org.bukkit.Bukkit.getPluginManager().getPlugin(name);
        if (hook != null) {
            String rawVersion = hook.getDescription().getVersion();
            String[] versionPart = rawVersion.split("\\-");
            String version = versionPart[0];
            if (isSupported(version, min, max)) {
                if (!hooked.containsKey(name)) {
                    hooked.put(name, hook);
                    return true;
                }
                return false;
            }

            StaticMethods.log("&c" + name + " v" + version + " is unknown or unsupported.");
            StaticMethods.log("&cAttempting to hook anyway. There may be errors.");
            try {
                if (!hooked.containsKey(name)) {
                    hooked.put(name, hook);
                    return true;
                }
                return false;
            } catch (Exception ex) {
                StaticMethods.log("&cCould not hook into " + name + " v" + version);
                StaticMethods.log("&cThe following stack trace may reveal why:");
                StaticMethods.log(" ");
                ex.printStackTrace();
                StaticMethods.log(" ");
                return false;
            }
        }

        return false;
    }

    public static boolean isSupported(String version, String min, String max) {
        try {
            return (compareVersions(version, min).intValue() >= 0) && (compareVersions(version, max).intValue() <= 0);
        } catch (Exception ignored) {
        }
        return false;
    }

    public static Integer compareVersions(String version, String compareTo) {
        String[] versionString = version.split("\\.");
        String[] compareToString = compareTo.split("\\.");
        int i = 0;
        while ((i < versionString.length) && (i < compareToString.length) && (versionString[i].equals(compareToString[i]))) {
            i++;
        }
        if ((i < versionString.length) && (i < compareToString.length)) {
            int diff = Integer.valueOf(versionString[i]).compareTo(Integer.valueOf(compareToString[i]));
            return Integer.valueOf(Integer.signum(diff));
        }
        return Integer.valueOf(Integer.signum(versionString.length - compareToString.length));
    }
}
