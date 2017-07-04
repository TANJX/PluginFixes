package fr.cookyy_.admin.commands;

import fr.cookyy_.admin.AdminSystemMain;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public class AdminClearInventory
  implements CommandExecutor
{
  public AdminClearInventory() {}
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (cmd.getName().equalsIgnoreCase("adminclear"))
    {
      if (!(sender instanceof Player))
      {
        sender.sendMessage(ChatColor.RED + "You must be a player to perform this command");
        return false;
      }
      Player p = (Player)sender;
      if (!p.hasPermission("adminfr.clear"))
      {
        p.sendMessage(AdminSystemMain.prefix + "§lYou do not have permission to use command");
        return true;
      }
      if (args.length == 0)
      {
        p.sendMessage(AdminSystemMain.prefix + "§aYour inventory has been cleaned !");
        p.getInventory().clear();
        return true;
      }
      if (args.length == 1)
      {
        Player t = Bukkit.getPlayer(args[0]);
        if (t == null)
        {
          p.sendMessage("§cPlayer is offline");
          return true;
        }
        p.sendMessage(AdminSystemMain.prefix + "§cYour inventory was clear by" + "§6" + t.getName());
        t.getInventory().clear();
        t.sendMessage(AdminSystemMain.prefix + "§7Player inventory" + p.getName() + " §7is clear");
        return true;
      }
      p.sendMessage("§cUse: /clear <player>");
      return false;
    }
    return false;
  }
}
