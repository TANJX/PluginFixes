package info.TrenTech.EasyKits.Commands;

import info.TrenTech.EasyKits.Kit.Kit;
import info.TrenTech.EasyKits.Kit.KitUser;
import info.TrenTech.EasyKits.SQL.SQLKits;
import info.TrenTech.EasyKits.Utils.Notifications;
import info.TrenTech.EasyKits.Utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CMDList {

    public static void execute(CommandSender sender) {
        if (!sender.hasPermission("EasyKits.cmd.list")) {
            Notifications notify = new Notifications("Permission-Denied", null, sender.getName(), 0, null, 0);
            sender.sendMessage(notify.getMessage());
            return;
        }

        List<Kit> list = SQLKits.getKitList();
        sender.sendMessage(ChatColor.UNDERLINE + "" + ChatColor.DARK_GREEN + "Kits:\n");
        for (Kit kit : list) {
            if (sender.hasPermission("EasyKits.kits." + kit.getName()) && !kit.getName().equalsIgnoreCase(Utils.getConfig().getString("Config.First-Join-Kit")) || sender instanceof ConsoleCommandSender) {
                String kitMsg = ChatColor.YELLOW + "- " + kit.getName();
                if (sender instanceof Player) {
                    Player player = (Player) sender;

                    KitUser kitUser = new KitUser(player, kit);
                    if (!player.hasPermission("EasyKits.bypass.limit")) {
                        if (((kitUser.getCurrentLimit() == 0) && (kit.getLimit() > 0))) {
                            kitMsg = ChatColor.STRIKETHROUGH + "" + ChatColor.DARK_RED + "- " + kit.getName();
                        }
                    }

                    if (!player.hasPermission("EasyKits.bypass.cooldown")) {
                        if ((kitUser.getCooldownTimeRemaining() != null)) {
                            kitMsg = ChatColor.STRIKETHROUGH + "" + ChatColor.DARK_RED + "- " + kit.getName();
                        }
                    }

                    double price = kit.getPrice();
                    if (price > 0) {
                        if (!player.hasPermission("EasyKits.bypass.price")) {
                            kitMsg = kitMsg + ":";
                            double balance = Utils.getPluginContainer().economy.getBalance(player);
                            if (balance < price) {
                                kitMsg = kitMsg + ChatColor.DARK_RED + " $" + price;
                            } else {
                                kitMsg = kitMsg + ChatColor.DARK_GREEN + " $" + price;
                            }
                        }
                    }
                }
                sender.sendMessage(kitMsg);
            }
        }
    }

}
