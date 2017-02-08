package com.manjason.randomplayer.util;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;

public class Util {

    public static void sendMsg(CommandSender cs, String msg) {
        cs.sendMessage(msg);
    }

    public static String convertColor(String str) {
        return str.replace("&", "§");
    }

    public static void sendFullMsg(String msg) {

       for (Player p : Bukkit.getOnlinePlayers()) {
          p.sendMessage(msg);
       }

    }

    public static ItemStack getItemStackByString(String str) {
        ItemStack item = null;
        String id = str.split("-")[0];
        int num = Integer.parseInt(str.split("-")[1]);
        if (id.split(":").length > 1) {
            item = new ItemStack(Integer.parseInt(id.split(":")[0]), num, Short.parseShort(id.split(":")[1]));
        } else {
            item = new ItemStack(Integer.parseInt(id), num);
        }

        return item;
    }
}
