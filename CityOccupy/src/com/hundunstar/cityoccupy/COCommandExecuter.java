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
                        sender.sendMessage(ChatColor.BLUE + "���������������б�");
                        sender.sendMessage(ChatColor.BLUE + "/cop commandlist                                  ��ȡ�����б�");
                        sender.sendMessage(ChatColor.BLUE + "/cop create <��������> [ÿ5��������(Ĭ��Ϊ100)]   ����һ������");
                        sender.sendMessage(ChatColor.BLUE + "/cop remove <��������>                            ɾ��һ������");
                        sender.sendMessage(ChatColor.BLUE + "/cop attack <��������>                           ����һ��������");
                        sender.sendMessage(ChatColor.BLUE + "�������HundunStar������QQ:648377563");
                    }
                }
                if (args[0].equalsIgnoreCase("remove") && sender.hasPermission("cityoccupy.remove"))//�Ƴ�����
                {
                    if (args.length == 2) {
                        if (!sender.hasPermission("cityoccupy.remove"))
                            sender.sendMessage(ChatColor.BLUE + "��û��ɾ�����е�Ȩ��Ӵ��");
                        if (CityOccupy.cmanager.delCity(args[1]))
                            sender.sendMessage(ChatColor.BLUE + "ɾ������" + args[1] + "�ɹ�");
                        else
                            sender.sendMessage(ChatColor.BLUE + "ɾ������ʧ�ܣ��˳��в����ڣ�");
                    }
                }
                if (args[0].equalsIgnoreCase("create") && sender.hasPermission("cityoccupy.create"))//��������
                {
                    if (args.length >= 2 && args.length <= 3) {
                        if (!sender.hasPermission("cityoccupy.create"))
                            sender.sendMessage(ChatColor.BLUE + "��û�д������е�Ȩ��Ӵ��");
                        if (sender instanceof Player) {
                            if (COSelectionManager.isBlockOutofHeight(CityOccupy.smanager.getPlayerLoc1(sender.getName()).getBlock())
                                    && COSelectionManager.isBlockOutofHeight(CityOccupy.smanager.getPlayerLoc2(sender.getName()).getBlock()))
                            //�ж�ָ����Χ�ڲ������Ƹ߶�����
                            {
                                if (CityOccupy.cmanager.isCityExisted(args[1])) {
                                    sender.sendMessage(ChatColor.BLUE + "�Ѵ��ڴ˳��У�������Ϊ����ȡ����");
                                    return true;
                                }

                                int salary = 5;
                                try {
                                    if (args.length == 3)
                                        salary = Integer.parseInt(args[2]);
                                } catch (NumberFormatException e) {
                                    sender.sendMessage(ChatColor.BLUE + "��������ȷ��ÿ5�������棡");
                                    return true;
                                }

                                //�����������
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


                                sender.sendMessage(ChatColor.BLUE + "�㴴����һ������ " + args[1] + "�ĳ���");
                                sender.sendMessage(ChatColor.BLUE + "��Χ�� " + x.toString() + "*" + y.toString() + "*" + z.toString());
                                sender.sendMessage(ChatColor.BLUE + "�ܷ�Χ�� " + total.toString());
                                if (args.length == 2)
                                    sender.sendMessage(ChatColor.BLUE + "ÿ5��������Ϊ5");
                                else
                                    sender.sendMessage(ChatColor.BLUE + "ÿ5��������Ϊ" + args[2]);

                                CityOccupy.cmanager.addCity(args[1], CityOccupy.smanager.getPlayerLoc1(sender.getName()), CityOccupy.smanager.getPlayerLoc2(sender.getName()), ((Player) sender).getWorld(), salary);


                            } else
                                sender.sendMessage(ChatColor.BLUE + "�㻹û��ѡ����еķ�Χ�أ����п���ֻѡ��һ�������û��ѡ��һ����");
                        } else
                            sender.sendMessage(ChatColor.BLUE + "�÷�: /cop create <��������> [ÿ5��������(Ĭ��Ϊ5)]");
                        return true;
                    } else
                        CityOccupy.logger.log(Level.WARNING, "������������������ܱ�����̨����Ӵ��");
                    return true;
                }
                if (args[0].equalsIgnoreCase("attack")) {
                    if (args.length == 2) {
                        if (!sender.hasPermission("cityoccupy.attack")) {
                            sender.sendMessage(ChatColor.RED + "��û�з���������Ȩ��Ӵ��");
                            return true;
                        }
                        if (sender instanceof Player) {
                            if (CityOccupy.economy.getBalance(sender.getName()) < CityOccupy.config.getInt("attackcost")) {
                                sender.sendMessage(ChatColor.BLUE + "��û���㹻��Ǯȥ���������Ӵ��");
                                return true;
                            }
                            if (!CityOccupy.cmanager.groupAttack.containsKey(args[1]))
                                CityOccupy.cmanager.groupAttack.put(args[1], new ArrayList<>());
                            CityOccupy.cmanager.groupAttack.get(args[1]).add(
                                    CityOccupy.permission.getPrimaryGroup((Player) sender));
                            CityOccupy.economy.withdrawPlayer(sender.getName(), CityOccupy.config.getInt("attackcost"));
                            Bukkit.broadcastMessage(ChatColor.GREEN + CityOccupy.permission.getPrimaryGroup((Player) sender) + "��" + args[1] + "�����˽����");
                            return true;

                        } else
                            CityOccupy.logger.log(Level.WARNING, "������������������ܱ�����̨����Ӵ��");
                    } else
                        sender.sendMessage(ChatColor.BLUE + "�÷�: /cop attack <��������>");
                }
            }
        }
        return false;
    }

    private static Integer abs(int a) {
        return a >= 0 ? a : -a;
    }
}
