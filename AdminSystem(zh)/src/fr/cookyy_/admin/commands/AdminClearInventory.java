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
                sender.sendMessage(ChatColor.RED + "�������ִ�е�����");
                return false;
            }
            Player p = (Player) sender;
            if (!p.hasPermission("adminfr.clear")) {
                p.sendMessage(AdminSystemMain.prefix + "��l��û��Ȩ��");
                return true;
            }
            if (args.length == 0) {
                p.sendMessage(AdminSystemMain.prefix + "��a���������!");
                p.getInventory().clear();
                return true;
            }
            if (args.length == 1) {
                Player t = Bukkit.getPlayer(args[0]);
                if (t == null) {
                    p.sendMessage("��c��Ҳ�����");
                    return true;
                }
                p.sendMessage(AdminSystemMain.prefix + "��c��ı�����" + "��6" + t.getName() + "��c�����");
                t.getInventory().clear();
                t.sendMessage(AdminSystemMain.prefix + "��7���" + p.getName() + " ��7�ı����ѱ����");
                return true;
            }
            p.sendMessage("��c�÷�: /clear <�����>");
            return false;
        }
        return false;
    }
}
