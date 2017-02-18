package com.hundunstar.cityoccupy.timer;

import com.hundunstar.cityoccupy.CityOccupy;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.TimerTask;

public class OccupiedSalary extends BukkitRunnable {

    @SuppressWarnings("deprecation")
    @Override
    public void run() {
        //List<City> citylist=CityOccupy.cmanager.getCityList();
        ConfigurationSection section = CityOccupy.citys.getConfigurationSection("citys");
        if (section != null)
            for (String c : section.getKeys(false)) {
                if (CityOccupy.citys.isSet("citys." + c + ".occupied")) {
                    String group = CityOccupy.citys.getString("citys." + c + ".occupied");
//                    System.out.print(group);
                    for (Player p : CityOccupy.server.getOnlinePlayers()) {
//                        System.out.print(CityOccupy.permission.getPrimaryGroup(p));
                        if (CityOccupy.permission.getPrimaryGroup(p).equals(group)) {
                            CityOccupy.economy.depositPlayer(p.getName(), CityOccupy.citys.getInt("citys." + c + ".salary"));
                            p.sendMessage(ChatColor.BLUE + "你在城市" + c + "得到的每5分钟收益(" + CityOccupy.citys.getInt("citys." + c + ".salary") + ")已发入你的账户！");
                        }
                    }
                }
            }
    }

}
