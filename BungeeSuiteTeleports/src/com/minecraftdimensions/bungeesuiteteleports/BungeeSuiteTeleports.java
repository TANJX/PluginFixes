package com.minecraftdimensions.bungeesuiteteleports;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.minecraftdimensions.bungeesuiteteleports.commands.BackCommand;
import com.minecraftdimensions.bungeesuiteteleports.commands.TPACommand;
import com.minecraftdimensions.bungeesuiteteleports.commands.TPAHereCommand;
import com.minecraftdimensions.bungeesuiteteleports.commands.TPAcceptCommand;
import com.minecraftdimensions.bungeesuiteteleports.commands.TPAllCommand;
import com.minecraftdimensions.bungeesuiteteleports.commands.TPCommand;
import com.minecraftdimensions.bungeesuiteteleports.commands.TPDenyCommand;
import com.minecraftdimensions.bungeesuiteteleports.commands.TPHereCommand;
import com.minecraftdimensions.bungeesuiteteleports.commands.ToggleCommand;
import com.minecraftdimensions.bungeesuiteteleports.listeners.TeleportsListener;
import com.minecraftdimensions.bungeesuiteteleports.listeners.TeleportsMessageListener;

public class BungeeSuiteTeleports extends JavaPlugin {


	public static String OUTGOING_PLUGIN_CHANNEL = "BSTeleports";
	static String INCOMING_PLUGIN_CHANNEL = "BungeeSuiteTP";
	public static BungeeSuiteTeleports instance;

	@Override
	public void onEnable() {
		instance=this;
		registerListeners();
		registerChannels();
		registerCommands();
	}
	
	private void registerCommands() {
		getCommand("tp").setExecutor(new TPCommand());
		getCommand("tphere").setExecutor(new TPHereCommand());
		getCommand("tpall").setExecutor(new TPAllCommand());
		getCommand("tpa").setExecutor(new TPACommand());
		getCommand("tpahere").setExecutor(new TPAHereCommand());
		getCommand("tpaccept").setExecutor(new TPAcceptCommand());
		getCommand("tpdeny").setExecutor(new TPDenyCommand());
		getCommand("back").setExecutor(new BackCommand());
		getCommand("tptoggle").setExecutor(new ToggleCommand());
	}

	private void registerChannels() {
		Bukkit.getMessenger().registerIncomingPluginChannel(this,
				INCOMING_PLUGIN_CHANNEL, new TeleportsMessageListener());
		Bukkit.getMessenger().registerOutgoingPluginChannel(this,
				OUTGOING_PLUGIN_CHANNEL);
	}

	private void registerListeners() {
		getServer().getPluginManager().registerEvents(
				new TeleportsListener(), this);
	}


}
