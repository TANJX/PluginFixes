package net.doodcraft.oshcon.bukkit.enderpads;

import net.doodcraft.oshcon.bukkit.enderpads.config.Settings;
import net.doodcraft.oshcon.bukkit.enderpads.util.StaticMethods;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EnderPadsCommand implements org.bukkit.command.CommandExecutor {
    public EnderPadsCommand() {
    }

    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("enderpads")) {
            if ((sender instanceof Player)) {
                Player player = (Player) sender;
                if (!StaticMethods.hasPermission(player, "enderpads.command.enderpads", true)) {
                    return false;
                }
                if (args.length == 0) {
                    sendValidCommands(sender);
                    return true;
                }
                if (args[0].equalsIgnoreCase("reload")) {
                    if (!StaticMethods.hasPermission(player, "enderpads.command.reload", Boolean.TRUE)) {
                        return false;
                    }
                    boolean error = Settings.reload();
                    sendReloaded(error, sender);
                    return true;
                }
                player.sendMessage(StaticMethods.addColor(Settings.pluginPrefix + "&c错误的命令."));
                sendValidCommands(sender);
                return false;
            }
            if (args.length == 0) {
                sendValidCommands(sender);
                return true;
            }
            if (args[0].equalsIgnoreCase("reload")) {
                boolean error = Settings.reload();
                sendReloaded(error, sender);
                return true;
            }
        }

        return false;
    }

    public static void sendReloaded(boolean error, CommandSender sender) {
        if (!error) {
            if ((sender instanceof Player)) {
                sender.sendMessage(StaticMethods.addColor(Settings.pluginPrefix + " &a插件已重载!"));
                StaticMethods.log("&a插件已重载!");
            } else {
                StaticMethods.log("&a插件已重载!");
            }
        } else if ((sender instanceof Player)) {
            sender.sendMessage(StaticMethods.addColor(Settings.pluginPrefix + " &cError reloading plugin!"));
            StaticMethods.log("&cError reloading plugin!");
        } else {
            StaticMethods.log("&cError reloading plugin!");
        }
    }

    public static void sendValidCommands(CommandSender sender) {
        if ((sender instanceof Player)) {
            sender.sendMessage(StaticMethods.addColor(Settings.pluginPrefix + " &3命令:"));
            sender.sendMessage(StaticMethods.addColor(Settings.pluginPrefix + " &b/enderpads reload: &7重载插件"));
        } else {
            StaticMethods.log("&3命令:");
            StaticMethods.log("&b/enderpads reload: &7重载插件");
        }
    }
}
