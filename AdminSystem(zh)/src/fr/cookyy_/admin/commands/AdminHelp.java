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
                sender.sendMessage(ChatColor.RED + "�������ִ�е�����");
                return false;
            }
            if (!sender.hasPermission("adminfr.adminhelp")) {
                sender.sendMessage(ChatColor.RED + "��û��Ȩ��");
                return false;
            }
            Player p = (Player) sender;
            p.sendMessage("��6����:");
            p.sendMessage("");
            p.sendMessage("��e/admin ��f- ��b�򿪲˵�");
            p.sendMessage("��e/adminclear ��f- ��b��ձ���");
            p.sendMessage("��e/adminclear <�����> ��f- ��b�����ұ���");
            p.sendMessage("");
            p.sendMessage("��cȨ��:");
            p.sendMessage("��e/admin ��f- ��badminfr.admin");
            p.sendMessage("��e/adminhelp ��f- ��badminfr.adminhelp");
            p.sendMessage("��e/adminclear ��f- ��badminfr.clearinventory");
            p.sendMessage("��e/adminclear ��a<�����> ��f- ��badminfr.clearinventory");
            p.sendMessage("");
            p.sendMessage("��6�汾: 2.0");
            p.sendMessage("��6ISOTOPE Studio����");
            return true;
        }
        return false;
    }
}
