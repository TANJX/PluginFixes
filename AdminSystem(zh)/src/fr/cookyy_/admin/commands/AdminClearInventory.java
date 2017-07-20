package fr.cookyy_.admin.commands;

import fr.cookyy_.admin.AdminSystemMain;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminClearInventory
        implements CommandExecutor {
    public AdminClearInventory() {
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("adminclear")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "这是玩家执行的命令");
                return false;
            }
            Player p = (Player) sender;
            if (!p.hasPermission("adminfr.clear")) {
                p.sendMessage(AdminSystemMain.prefix + "§l你没有权限");
                return true;
            }
            if (args.length == 0) {
                p.sendMessage(AdminSystemMain.prefix + "§a背包已清空!");
                p.getInventory().clear();
                return true;
            }
            if (args.length == 1) {
                Player t = Bukkit.getPlayer(args[0]);
                if (t == null) {
                    p.sendMessage("§c玩家不在线");
                    return true;
                }
                p.sendMessage(AdminSystemMain.prefix + "§c你的背包被" + "§6" + t.getName() + "§c清空了");
                t.getInventory().clear();
                t.sendMessage(AdminSystemMain.prefix + "§7玩家" + p.getName() + " §7的背包已被清空");
                return true;
            }
            p.sendMessage("§c用法: /clear <玩家名>");
            return false;
        }
        return false;
    }
}
