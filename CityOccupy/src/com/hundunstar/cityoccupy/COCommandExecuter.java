package com.hundunstar.cityoccupy;

import com.hundunstar.cityoccupy.selection.COSelectionManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.logging.Level;


class COCommandExecuter {
    public static boolean execute(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("cop") || command.getName().equalsIgnoreCase("cityoccupy")) {
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("commandlist")) {
                    if (args.length == 1) {
                        sender.sendMessage(ChatColor.BLUE + "城市争夺插件命令列表：");
                        sender.sendMessage(ChatColor.BLUE + "/cop commandlist                                  获取命令列表");
                        sender.sendMessage(ChatColor.BLUE + "/cop create <城市名称> [每5分钟收益(默认为100)]   创建一个城市");
                        sender.sendMessage(ChatColor.BLUE + "/cop remove <城市名称>                            删除一个城市");
                        sender.sendMessage(ChatColor.BLUE + "/cop attack <城市名称>                           发起一个进攻令");
                        sender.sendMessage(ChatColor.BLUE + "本插件由HundunStar制作，QQ:648377563");
                    }
                }
                if (args[0].equalsIgnoreCase("remove") && sender.hasPermission("cityoccupy.remove"))//移除城市
                {
                    if (args.length == 2) {
                        if (!sender.hasPermission("cityoccupy.remove"))
                            sender.sendMessage(ChatColor.BLUE + "你没有删除城市的权限哟！");
                        if (CityOccupy.cmanager.delCity(args[1]))
                            sender.sendMessage(ChatColor.BLUE + "删除城市" + args[1] + "成功");
                        else
                            sender.sendMessage(ChatColor.BLUE + "删除城市失败！此城市不存在！");
                    }
                }
                if (args[0].equalsIgnoreCase("create") && sender.hasPermission("cityoccupy.create"))//创建城市
                {
                    if (args.length >= 2 && args.length <= 3) {
                        if (!sender.hasPermission("cityoccupy.create"))
                            sender.sendMessage(ChatColor.BLUE + "你没有创建城市的权限哟！");
                        if (sender instanceof Player) {
                            if (COSelectionManager.isBlockOutofHeight(CityOccupy.smanager.getPlayerLoc1(sender.getName()).getBlock())
                                    && COSelectionManager.isBlockOutofHeight(CityOccupy.smanager.getPlayerLoc2(sender.getName()).getBlock()))
                            //判断指定范围在不在限制高度以内
                            {
                                if (CityOccupy.cmanager.isCityExisted(args[1])) {
                                    sender.sendMessage(ChatColor.BLUE + "已存在此城市！请重新为城市取名！");
                                    return true;
                                }

                                int salary = 5;
                                try {
                                    if (args.length == 3)
                                        salary = Integer.parseInt(args[2]);
                                } catch (NumberFormatException e) {
                                    sender.sendMessage(ChatColor.BLUE + "请输入正确的每5分钟收益！");
                                    return true;
                                }

                                //输出城市数据
                                int x1 = CityOccupy.smanager.getPlayerLoc1(sender.getName()).getBlockX();
                                int y1 = CityOccupy.smanager.getPlayerLoc1(sender.getName()).getBlockY();
                                int z1 = CityOccupy.smanager.getPlayerLoc1(sender.getName()).getBlockZ();
                                int x2 = CityOccupy.smanager.getPlayerLoc2(sender.getName()).getBlockX();
                                int y2 = CityOccupy.smanager.getPlayerLoc2(sender.getName()).getBlockY();
                                int z2 = CityOccupy.smanager.getPlayerLoc2(sender.getName()).getBlockZ();

                                Integer x = abs(x1 - x2 + 1);
                                Integer y = abs(y1 - y2 + 1);
                                Integer z = abs(z1 - z2 + 1);

                                Integer total = x * y * z;


                                sender.sendMessage(ChatColor.BLUE + "你创建了一个叫做 " + args[1] + "的城市");
                                sender.sendMessage(ChatColor.BLUE + "范围： " + x.toString() + "*" + y.toString() + "*" + z.toString());
                                sender.sendMessage(ChatColor.BLUE + "总范围： " + total.toString());
                                if (args.length == 2)
                                    sender.sendMessage(ChatColor.BLUE + "每5分钟收益为5");
                                else
                                    sender.sendMessage(ChatColor.BLUE + "每5分钟收益为" + args[2]);

                                CityOccupy.cmanager.addCity(args[1], CityOccupy.smanager.getPlayerLoc1(sender.getName()), CityOccupy.smanager.getPlayerLoc2(sender.getName()), ((Player) sender).getWorld(), salary);


                            } else
                                sender.sendMessage(ChatColor.BLUE + "你还没有选择城市的范围呢！（有可能只选了一个坐标而没有选另一个）");
                        } else
                            sender.sendMessage(ChatColor.BLUE + "用法: /cop create <城市名称> [每5分钟收益(默认为5)]");
                        return true;
                    } else
                        CityOccupy.logger.log(Level.WARNING, "这个插件的所有命令都不能被控制台发送哟！");
                    return true;
                }
                if (args[0].equalsIgnoreCase("attack")) {
                    if (args.length == 2) {
                        if (!sender.hasPermission("cityoccupy.attack")) {
                            sender.sendMessage(ChatColor.RED + "你没有发起进攻令的权限哟！");
                            return true;
                        }
                        if (sender instanceof Player) {
                            if (CityOccupy.economy.getBalance(sender.getName()) < CityOccupy.config.getInt("attackcost")) {
                                sender.sendMessage(ChatColor.BLUE + "你没有足够金钱去发起进攻令哟！");
                                return true;
                            }
                            if (!CityOccupy.cmanager.groupAttack.containsKey(args[1]))
                                CityOccupy.cmanager.groupAttack.put(args[1], new ArrayList<>());
                            CityOccupy.cmanager.groupAttack.get(args[1]).add(
                                    CityOccupy.permission.getPrimaryGroup((Player) sender));
                            CityOccupy.economy.withdrawPlayer(sender.getName(), CityOccupy.config.getInt("attackcost"));
                            Bukkit.broadcastMessage(ChatColor.GREEN + CityOccupy.permission.getPrimaryGroup((Player) sender) + "对" + args[1] + "发起了进攻令！");
                            return true;

                        } else
                            CityOccupy.logger.log(Level.WARNING, "这个插件的所有命令都不能被控制台发送哟！");
                    } else
                        sender.sendMessage(ChatColor.BLUE + "用法: /cop attack <城市名称>");
                }
            }
        }
        return false;
    }

    private static Integer abs(int a) {
        return a >= 0 ? a : -a;
    }
}
