package info.TrenTech.EasyKits.Commands;

import info.TrenTech.EasyKits.SQL.SQLUtils;
import info.TrenTech.EasyKits.Utils.Notifications;
import info.TrenTech.EasyKits.Utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CMDReload {

    public static void execute(CommandSender sender) {
        if (!sender.hasPermission("EasyKits.cmd.reload")) {
            Notifications notify = new Notifications("Permission-Denied", null, sender.getName(), 0, null, 0);
            sender.sendMessage(notify.getMessage());
            return;
        }

        Utils.getPluginContainer().reloadConfig();
        Utils.getPluginContainer().saveConfig();

        SQLUtils.dispose();
        try {
            SQLUtils.connect();
        } catch (Exception e) {
            Utils.getLogger().severe(String.format("[%s] - Unable to connect to database!", new Object[]{Utils.getPluginContainer().getDescription().getName()}));
        }
        Utils.getPluginContainer().setPlugin();
        sender.sendMessage(ChatColor.DARK_GREEN + "EasyKits Reloaded!");
    }
}
