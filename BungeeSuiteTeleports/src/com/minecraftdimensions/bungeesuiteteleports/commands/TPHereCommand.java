package com.minecraftdimensions.bungeesuiteteleports.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.minecraftdimensions.bungeesuiteteleports.managers.TeleportsManager;


public class TPHereCommand implements CommandExecutor {


	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		
		if (args.length == 1) {
			TeleportsManager.teleportToPlayer(sender,args[0], sender.getName() );
			return true;
		}
		return false;
	}

}
