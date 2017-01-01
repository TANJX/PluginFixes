package com.minecraftdimensions.bungeesuiteteleports.tasks;

import com.minecraftdimensions.bungeesuiteteleports.BungeeSuiteTeleports;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;

public class PluginMessageTask extends BukkitRunnable {

    private final ByteArrayOutputStream bytes;

    public PluginMessageTask(ByteArrayOutputStream bytes) {
        this.bytes = bytes;
    }

    public PluginMessageTask(ByteArrayOutputStream b, boolean empty) {
        this.bytes = b;
    }

    @SuppressWarnings("unchecked")
    public void run() {
        ArrayList<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        if (players.size() == 0) {
            return;
        }
        Player p = players.get(0);
        if (p == null) {
            return;
        }
        p.sendPluginMessage(BungeeSuiteTeleports.instance, BungeeSuiteTeleports.OUTGOING_PLUGIN_CHANNEL, bytes.toByteArray());
    }


}