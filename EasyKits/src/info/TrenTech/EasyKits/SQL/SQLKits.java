/*
 * 
 */
package info.TrenTech.EasyKits.SQL;

import info.TrenTech.EasyKits.Kit.Kit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static info.TrenTech.EasyKits.EasyKits.kitFile;

public abstract class SQLKits extends SQLUtils {

    public static boolean kitExist(String kitName) {
        for (String key : kitFile.getKeys(false)) {
            if (key.equalsIgnoreCase(kitName)) {
                return true;
            }
        }
        return false;
    }

    public static void createKit(String kitName, ItemStack[] inv, ItemStack[] armor, double price, String cooldown, int limit, String creator) {
        kitName = kitName.toLowerCase();
        ConfigurationSection config = kitFile.createSection(kitName);
        for (int i = 0; i < armor.length; i++) {
            if (armor[i] != null) {
                config.set("armor." + i, armor[i]);
            }
        }
        for (int i = 0; i < inv.length; i++) {
            if (inv[i] != null) {
                config.set("inv." + i, inv[i]);
            }
        }
        config.set("price", price);
        config.set("cooldown", cooldown);
        config.set("limit", limit);
        config.set("creator", creator);
        kitFile.save();
    }

    public static void setKitCooldown(String kitName, String cooldown) {
        kitName = kitName.toLowerCase();
        kitFile.set(kitName + ".cooldown", cooldown);
        kitFile.save();
    }

    public static void setKitLimit(String kitName, int limit) {
        kitName = kitName.toLowerCase();
        kitFile.set(kitName + ".limit", limit);
        kitFile.save();
    }

    public static void setKitPrice(String kitName, double price) {
        kitName = kitName.toLowerCase();
        kitFile.set(kitName + ".price", price);
        kitFile.save();
    }

    public static void setInventory(String kitName, ItemStack[] inv) {
        kitName = kitName.toLowerCase();
        kitFile.set(kitName + ".inv", null);
        for (int i = 0; i < inv.length; i++) {
            if (inv[i] != null) {
                kitFile.set(kitName + ".inv." + i, inv[i]);
            }
        }
        kitFile.save();
    }

    public static void setArmor(String kitName, ItemStack[] armor) {
        kitName = kitName.toLowerCase();
        kitFile.set(kitName + ".armor", null);
        for (int i = 0; i < armor.length; i++) {
            if (armor[i] != null) {
                kitFile.set(kitName + ".armor." + i, armor[i]);
            }
        }
        kitFile.save();
    }

    public static ItemStack[] getKitInventory(String kitName) {
        kitName = kitName.toLowerCase();
        ConfigurationSection config = kitFile.getConfigurationSection(kitName + ".inv");
        if (config != null) {
            int max = 0;
            for (String s : config.getKeys(false)) {
                if (Integer.parseInt(s) > max) max = Integer.parseInt(s);
            }
            ItemStack[] inv = new ItemStack[max + 1];
            for (int i = 0; i <= max; i++) {
                if (config.getKeys(false).contains("" + i))
                    inv[i] = config.getItemStack(String.valueOf(i));
            }
            return inv;
        }
        return null;
    }

    public static ItemStack[] getKitArmor(String kitName) {
        kitName = kitName.toLowerCase();
        ConfigurationSection config = kitFile.getConfigurationSection(kitName + ".armor");
        if (config != null) {
            ItemStack[] inv = new ItemStack[4];
            for (int i = 0; i <= 4; i++) {
                if (config.getKeys(false).contains("" + i))
                    inv[i] = config.getItemStack(String.valueOf(i));
            }
            return inv;
        }
        return null;
    }

    public static double getKitPrice(String kitName) {
        kitName = kitName.toLowerCase();
        return kitFile.getDouble(kitName + ".price", 0);
    }

    public static String getKitCooldown(String kitName) {
        kitName = kitName.toLowerCase();
        return kitFile.getString(kitName + ".cooldown", "0");
    }

    public static int getKitLimit(String kitName) {
        kitName = kitName.toLowerCase();
        return kitFile.getInt(kitName + ".limit", 0);
    }

    public static List<Kit> getKitList() {
        List<Kit> kitList = new ArrayList<>();
        for (String s : kitFile.getKeys(false)) {
            kitList.add(new Kit(s));
        }
        return kitList;
    }

    public static void deleteKit(String kitName) {
        kitName = kitName.toLowerCase();
        kitFile.set(kitName, null);
        kitFile.save();
    }

}