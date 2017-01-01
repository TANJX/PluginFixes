package com.minecraftdimensions.bungeesuitehomes.tasks;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.minecraftdimensions.bungeesuitehomes.BungeeSuiteHomes;

public class PluginMessageTask extends BukkitRunnable {

	private final ByteArrayOutputStream bytes;

	public PluginMessageTask(ByteArrayOutputStream bytes) {
		this.bytes = bytes;
	}
	
	public void run() {
		ArrayList<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
		players.get(0).sendPluginMessage(
					BungeeSuiteHomes.instance,
					BungeeSuiteHomes.OUTGOING_PLUGIN_CHANNEL,
					bytes.toByteArray());
	}

}