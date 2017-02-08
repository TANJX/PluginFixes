//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Toin.cc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.text.NumberFormat;

class MainUtil {
    public MainUtil() {
    }

    public static void info(String msg) {
        info(Bukkit.getConsoleSender(), msg);
    }

    public static void info(CommandSender sender, String msg) {
        if(sender != null) {
            sender.sendMessage(colorCode(formatMsg(msg)));
        }

    }

    private static String colorCode(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static String colorCut(String msg) {
        return ChatColor.stripColor(msg);
    }

    private static String formatMsg(String msg) {
        return "§c[§6宝石系统§c]§a " + msg;
    }

    public static String notUseScientificNotation(Number number) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        return nf.format(number);
    }
}
