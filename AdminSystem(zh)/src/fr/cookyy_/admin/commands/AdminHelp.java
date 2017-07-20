package fr.cookyy_.admin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminHelp implements org.bukkit.event.Listener, CommandExecutor {
    public AdminHelp() {
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] arg3) {
        if (cmd.getName().equalsIgnoreCase("adminhelp")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "这是玩家执行的命令");
                return false;
            }
            if (!sender.hasPermission("adminfr.adminhelp")) {
                sender.sendMessage(ChatColor.RED + "你没有权限");
                return false;
            }
            Player p = (Player) sender;
            p.sendMessage("§6命令:");
            p.sendMessage("");
            p.sendMessage("§e/admin §f- §b打开菜单");
            p.sendMessage("§e/adminclear §f- §b清空背包");
            p.sendMessage("§e/adminclear <玩家名> §f- §b清空玩家背包");
            p.sendMessage("");
            p.sendMessage("§c权限:");
            p.sendMessage("§e/admin §f- §badminfr.admin");
            p.sendMessage("§e/adminhelp §f- §badminfr.adminhelp");
            p.sendMessage("§e/adminclear §f- §badminfr.clearinventory");
            p.sendMessage("§e/adminclear §a<玩家名> §f- §badminfr.clearinventory");
            p.sendMessage("");
            p.sendMessage("§6版本: 2.0");
            p.sendMessage("§6ISOTOPE Studio汉化");
            return true;
        }
        return false;
    }
}
