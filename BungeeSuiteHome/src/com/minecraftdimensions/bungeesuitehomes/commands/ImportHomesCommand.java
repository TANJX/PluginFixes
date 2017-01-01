package com.minecraftdimensions.bungeesuitehomes.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.minecraftdimensions.bungeesuitehomes.managers.HomesManager;

public class ImportHomesCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		HomesManager.importHomes(sender);
		return true;
	}

}
