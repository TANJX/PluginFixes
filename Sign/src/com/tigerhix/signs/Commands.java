//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tigerhix.signs;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class Commands implements CommandExecutor {
    private Main plugin;
    private static final Set<Material> MATERIAL_SET = new HashSet<Material>();

    public Commands(Main plugin) {
        this.plugin = plugin;
        MATERIAL_SET.clear();
        MATERIAL_SET.add(Material.AIR);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (!sender.isOp()) {
                sender.sendMessage(ChatColor.RED + "* ��û��Ȩ�ޣ�");
                return true;
            }

            if (args.length == 2) {
                if (isInteger(args[0]) && Integer.parseInt(args[0]) >= 1 && Integer.parseInt(args[0]) <= 4) {
                    Player p = (Player) sender;
                    Block b = p.getTargetBlock(MATERIAL_SET, 10);
                    if (b.getType() != Material.SIGN_POST && b.getType() != Material.WALL_SIGN && b.getType() != Material.SIGN) {
                        sender.sendMessage(ChatColor.RED + "* ��ָ��ķ��鲻�Ǹ�ʾ�ƣ��޷��޸ġ�");
                    } else {
                        Sign s = (Sign) b.getState();
                        if (args[1].equalsIgnoreCase("clear")) {
                            s.setLine(Integer.parseInt(args[0]) - 1, "");
                        } else {
                            s.setLine(Integer.parseInt(args[0]) - 1, ChatColor.translateAlternateColorCodes('&', args[1]).replace("_", " "));
                        }

                        s.update();
                        sender.sendMessage(ChatColor.GRAY + "* ��ɣ�");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "* ��������ȷ��");
                }
            } else {
                sender.sendMessage(ChatColor.GRAY + "* ʹ�÷�����/signedit [����] [�ı�]");
            }
        } else {
            sender.sendMessage("������ֻ����ҿ��ţ�");
        }

        return true;
    }

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
}
