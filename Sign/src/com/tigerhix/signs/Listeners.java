//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tigerhix.signs;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class Listeners implements Listener {
    public static Main plugin;

    public Listeners(Main plugin) {
        Listeners.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onSignChange(SignChangeEvent evt) {
        Player p = evt.getPlayer();
        String[] lines = evt.getLines();

        for(int i = 0; i < lines.length; ++i) {
            evt.setLine(i, ChatColor.translateAlternateColorCodes('&', lines[i]));
        }

    }
}
