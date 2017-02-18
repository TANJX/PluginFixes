package com.hundunstar.cityoccupy.city;

import com.hundunstar.cityoccupy.CityOccupy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class COCityManager {
    public final Map<String, List<Player>> playerIn;
    public final Map<String, List<String>> groupAttack;
    public final Map<String, Integer> completion;

    public COCityManager() {
        groupAttack = new Hashtable<>();
        playerIn = new Hashtable<>();
        completion = new Hashtable<>();
    }

    public int getCompletion(String c) {
        return completion.containsKey(c) ? completion.get(c) : 0;
    }

    public void addCompletion(String c, int cp) {
        completion.put(c, getCompletion(c) + cp);
    }

    private boolean isCitySingleGroup(String c) {
        try {
            String group = CityOccupy.permission.getPrimaryGroup(playerIn.get(c).get(0));
            for (Player player : playerIn.get(c)) {
                if (!CityOccupy.permission.getPrimaryGroup(player).equals(group))
                    return false;
            }
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public String getCitySingleGroup(String c) {
        if (!isCitySingleGroup(c))
            return "";
        return CityOccupy.permission.getPrimaryGroup(playerIn.get(c).get(0));
    }

    public void addCity(String name, Location pos1, Location pos2, World world)//增加新城市
    {
        if (isCityExisted(name))
            return;
        CityOccupy.citys.set("citys." + name + ".X1", pos1.getBlockX());
        CityOccupy.citys.set("citys." + name + ".Y1", pos1.getBlockY());
        CityOccupy.citys.set("citys." + name + ".Z1", pos1.getBlockZ());
        CityOccupy.citys.set("citys." + name + ".X2", pos2.getBlockX());
        CityOccupy.citys.set("citys." + name + ".Y2", pos2.getBlockY());
        CityOccupy.citys.set("citys." + name + ".Z2", pos2.getBlockZ());
        CityOccupy.citys.set("citys." + name + ".world", world.getName());
        CityOccupy.citys.set("citys." + name + ".salary", 5);
    }

    public void addCity(String name, Location pos1, Location pos2, World world, int salary)//增加新城市
    {
        if (isCityExisted(name))
            return;
        CityOccupy.citys.set("citys." + name + ".X1", pos1.getBlockX());
        CityOccupy.citys.set("citys." + name + ".Y1", pos1.getBlockY());
        CityOccupy.citys.set("citys." + name + ".Z1", pos1.getBlockZ());
        CityOccupy.citys.set("citys." + name + ".X2", pos2.getBlockX());
        CityOccupy.citys.set("citys." + name + ".Y2", pos2.getBlockY());
        CityOccupy.citys.set("citys." + name + ".Z2", pos2.getBlockZ());
        CityOccupy.citys.set("citys." + name + ".world", world.getName());
        CityOccupy.citys.set("citys." + name + ".salary", salary);
    }

    public boolean delCity(String name)//删除城市
    {
//		for(City c:CityList)
//		{
//			if(c.name.equalsIgnoreCase(name))
//			{
//				return CityList.remove(c);
//			}
//		}
        ConfigurationSection section = CityOccupy.citys.getConfigurationSection("citys");
        if (section == null)
            return false;
        for (String s : section.getKeys(false))
            if (s.equals(name)) {
                CityOccupy.citys.set("citys." + name, null);
                return true;
            }
        return false;
    }

    public boolean isInAnyCity(String player)//判断玩家在不在任一城市里
    {
        Player p = Bukkit.getPlayer(player);
        ConfigurationSection section = CityOccupy.citys.getConfigurationSection("citys");
        if (section == null)
            return false;
        for (String s : section.getKeys(false)) {
//			if(c.isInCity(p.getLocation()))
//				return true;
            int x1 = Math.max(CityOccupy.citys.getInt("citys." + s + ".X1"), CityOccupy.citys.getInt("citys." + s + ".X2"));
            int x2 = Math.min(CityOccupy.citys.getInt("citys." + s + ".X1"), CityOccupy.citys.getInt("citys." + s + ".X2"));
            int y1 = Math.max(CityOccupy.citys.getInt("citys." + s + ".Y1"), CityOccupy.citys.getInt("citys." + s + ".Y2"));
            int y2 = Math.min(CityOccupy.citys.getInt("citys." + s + ".Y1"), CityOccupy.citys.getInt("citys." + s + ".Y2"));
            int z1 = Math.max(CityOccupy.citys.getInt("citys." + s + ".Z1"), CityOccupy.citys.getInt("citys." + s + ".Z2"));
            int z2 = Math.min(CityOccupy.citys.getInt("citys." + s + ".Z1"), CityOccupy.citys.getInt("citys." + s + ".Z2"));
            int x = p.getLocation().getBlockX();
            int y = p.getLocation().getBlockY();
            int z = p.getLocation().getBlockZ();
            if (x1 >= x && x >= x2 && y1 >= y && y >= y2 && z1 >= z && z >= z2
                    && p.getWorld().getName().equals(CityOccupy.citys.getString("citys." + s + ".world")))
                return true;
        }
        return false;
    }

    public String getCityIn(String player)//获取玩家所在城市
    {
        Player p = Bukkit.getPlayer(player);
        ConfigurationSection section = CityOccupy.citys.getConfigurationSection("citys");
        if (section == null)
            return "";
        for (String s : section.getKeys(false)) {
            int x1 = Math.max(CityOccupy.citys.getInt("citys." + s + ".X1"), CityOccupy.citys.getInt("citys." + s + ".X2"));
            int x2 = Math.min(CityOccupy.citys.getInt("citys." + s + ".X1"), CityOccupy.citys.getInt("citys." + s + ".X2"));
            int y1 = Math.max(CityOccupy.citys.getInt("citys." + s + ".Y1"), CityOccupy.citys.getInt("citys." + s + ".Y2"));
            int y2 = Math.min(CityOccupy.citys.getInt("citys." + s + ".Y1"), CityOccupy.citys.getInt("citys." + s + ".Y2"));
            int z1 = Math.max(CityOccupy.citys.getInt("citys." + s + ".Z1"), CityOccupy.citys.getInt("citys." + s + ".Z2"));
            int z2 = Math.min(CityOccupy.citys.getInt("citys." + s + ".Z1"), CityOccupy.citys.getInt("citys." + s + ".Z2"));
            int x = p.getLocation().getBlockX();
            int y = p.getLocation().getBlockY();
            int z = p.getLocation().getBlockZ();
            if (x1 >= x && x >= x2 && y1 >= y && y >= y2 && z1 >= z && z >= z2 &&
                    p.getWorld().getName().equals(CityOccupy.citys.getString("citys." + s + ".world")))
                return s;
        }
        return "";
    }

    public boolean isCityExisted(String name) {
        return CityOccupy.citys.contains("citys." + name);
    }
}
