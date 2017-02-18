package com.hundunstar.cityoccupy.timer;

import com.hundunstar.cityoccupy.CityOccupy;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

import static com.hundunstar.cityoccupy.CityOccupy.cmanager;

public class Occupying extends BukkitRunnable {

    @Override
    public void run() {
//        System.out.print(cmanager.playerIn.toString());
//        System.out.print(cmanager.completion.toString());
//        System.out.print(cmanager.groupAttack.toString());
        ConfigurationSection section = CityOccupy.citys.getConfigurationSection("citys");
        if (section != null) {
//            System.out.println("----");
            for (String c : section.getKeys(false)) {
                if (cmanager.playerIn.containsKey(c)) {
                    String group = cmanager.getCitySingleGroup(c);
                    boolean b = false;
                    try {
                        b = !group.isEmpty() && ((CityOccupy.citys.contains("citys." + c + ".occupied")
                                && cmanager.groupAttack.get(c).contains(group)
                                && group != CityOccupy.citys.getString("citys." + c + ".occupied"))
                                || (!CityOccupy.citys.contains("citys." + c + ".occupied")));
                    } catch (Exception ignored) {
                    }
                    if (b) {
                        cmanager.addCompletion(c, cmanager.playerIn.size());
                        System.out.println(c + " " + cmanager.getCompletion(c));
                        if (cmanager.getCompletion(c) >= 300) {
                            cmanager.groupAttack.putIfAbsent(c, new ArrayList<>());
                            if (!cmanager.groupAttack.get(c).contains(group)) {
                                cmanager.groupAttack.get(c).add(group);
                            }
                            CityOccupy.citys.set("citys." + c + ".occupied", group);
                            CityOccupy.server.broadcastMessage(ChatColor.RED + group + "已成功占领城市" + c + "！");
                        }
                    } else {
                        cmanager.completion.put(c, 0);
                    }
                } else {
                    cmanager.completion.put(c, 0);
                }
            }
        }

    }

}