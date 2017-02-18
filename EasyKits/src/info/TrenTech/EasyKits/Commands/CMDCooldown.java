package info.TrenTech.EasyKits.Commands;

import info.TrenTech.EasyKits.Kit.Kit;
import info.TrenTech.EasyKits.Utils.Notifications;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CMDCooldown {

    public static void execute(CommandSender sender, String[] args) {

        if (!sender.hasPermission("EasyKits.cmd.cooldown")) {
            Notifications notify = new Notifications("Permission-Denied", null, null, 0, null, 0);
            sender.sendMessage(notify.getMessage());
            return;
        }

        if (args.length >= 3) {
            StringBuilder str = new StringBuilder(args[2]);
            for (String arg : args) {
                if (!(arg.equals(args[0]) || arg.equals(args[1]) || arg.equals(args[2]))) {
                    str.append(" ").append(arg);
                }
            }
            Kit kit = new Kit(args[1]);
            if (!kit.exists()) {
                Notifications notify = new Notifications("Kit-Not-Exist", args[1], sender.getName(), 0, null, 0);
                sender.sendMessage(notify.getMessage());
                return;
            }

            String[] newArgs = str.toString().split(" ");

            if (!isValid(newArgs)) {
                Notifications notify = new Notifications("Invalid-Argument", args[1], sender.getName(), 0, str.toString(), 0);
                sender.sendMessage(notify.getMessage());
                sender.sendMessage(ChatColor.YELLOW + "/kit cooldown <kitname> <cooldown>");
                return;
            }

            kit.setCooldown(str.toString());
            Notifications notify = new Notifications("Set-Cooldown", kit.getName(), sender.getName(), 0, str.toString(), 0);
            sender.sendMessage(notify.getMessage());
        } else {
            sender.sendMessage(ChatColor.YELLOW + "/kit cooldown <kitname> <cooldown>");
        }
    }

    private static boolean isValid(String[] args) {
        int loop = 0;
        for (String arg : args) {
            if (arg.matches("(\\d+)[w]$") && loop == 0) {

            } else if (arg.matches("(\\d+)[d]$") && (loop == 0 || loop == 1)) {

            } else if (arg.matches("(\\d+)[h]$") && (loop == 0 || loop == 1 || loop == 2)) {

            } else if (arg.matches("(\\d+)[m]$") && (loop == 0 || loop == 1 || loop == 2 || loop == 3)) {

            } else if (arg.matches("(\\d+)[s]$") && (loop == 0 || loop == 1 || loop == 2 || loop == 3 || loop == 4)) {

            } else if (arg.equalsIgnoreCase("0") && args.length == 1) {
                return true;
            } else {
                return false;
            }
            loop++;
        }
        return true;
    }

}
