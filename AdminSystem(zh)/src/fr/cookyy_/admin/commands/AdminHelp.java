package fr.cookyy_.admin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminHelp implements org.bukkit.event.Listener, CommandExecutor
{
  public AdminHelp() {}
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] arg3)
  {
    if (cmd.getName().equalsIgnoreCase("adminhelp"))
    {
      if (!(sender instanceof Player))
      {
        sender.sendMessage(ChatColor.RED + "You must be a player to perform this command");
        return false;
      }
      if (!sender.hasPermission("adminfr.adminhelp"))
      {
        sender.sendMessage(ChatColor.RED + "You do not have permission to perform this command");
        return false;
      }
      Player p = (Player)sender;
      p.sendMessage("§6Commandes:");
      p.sendMessage("");
      p.sendMessage("§e/admin §f- §bOpen gui");
      p.sendMessage("§e/adminhelp §f- §bCommande plugin");
      p.sendMessage("§e/adminclear §f- §bClear Inventory");
      p.sendMessage("§e/adminclear <player> §f- §bClear Inventory Player");
      p.sendMessage("");
      p.sendMessage("§cPermission:");
      p.sendMessage("§e/admin §f- §badminfr.admin");
      p.sendMessage("§e/adminhelp §f- §badminfr.adminhelp");
      p.sendMessage("§e/adminclear §f- §badminfr.clearinventory");
      p.sendMessage("§e/adminclear §a<player> §f- §badminfr.clearinventory");
      p.sendMessage("");
      p.sendMessage("§6Version plugin: 2.0");
      return true;
    }
    return false;
  }
}
