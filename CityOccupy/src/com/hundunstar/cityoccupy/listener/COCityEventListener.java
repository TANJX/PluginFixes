package com.hundunstar.cityoccupy.listener;

import com.hundunstar.cityoccupy.CityOccupy;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;

public class COCityEventListener implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerMove(PlayerMoveEvent e)//�ж������û�н���/�˳�����
    {
        if (CityOccupy.cmanager.isInAnyCity(e.getPlayer().getName())) {
            String c = CityOccupy.cmanager.getCityIn(e.getPlayer().getName());
            if (!CityOccupy.cmanager.playerIn.containsKey(c)) {
                CityOccupy.cmanager.playerIn.put(c, new ArrayList<>());
            }
            List<Player> list = CityOccupy.cmanager.playerIn.get(c);
            if (!list.contains(e.getPlayer())) {
                list.add(e.getPlayer());
                CityOccupy.cmanager.playerIn.put(c, list);
                e.getPlayer().sendMessage(ChatColor.BLUE + "���Ѿ��������" + c);
            }
        } else {
            ConfigurationSection section = CityOccupy.citys.getConfigurationSection("citys");
            if (section != null) {
                for (String c : section.getKeys(false)) {
                    if (CityOccupy.cmanager.playerIn.containsKey(c))
                        for (Player player : CityOccupy.cmanager.playerIn.get(c))
                            if (e.getPlayer().getName().equals(player.getName())) {
                                CityOccupy.cmanager.playerIn.get(c).remove(player);
                                e.getPlayer().sendMessage(ChatColor.BLUE + "���Ѿ��뿪����" + c);
                                return;
                            }
                }
            }

        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerDie(PlayerDeathEvent e)//�ж������û�н���/�˳�����
    {
        if (CityOccupy.cmanager.isInAnyCity(e.getEntity().getName())) {
            String c = CityOccupy.cmanager.getCityIn(e.getEntity().getName());
            //c.addPlayerInCity(e.getEntity().getName());
            if (CityOccupy.cmanager.playerIn.containsKey(c)) {
                List<Player> list = CityOccupy.cmanager.playerIn.get(c);
                list.add(e.getEntity());
                CityOccupy.cmanager.playerIn.put(c, list);
            }
        } else {
            ConfigurationSection section = CityOccupy.citys.getConfigurationSection("citys");
            if (section != null) {
                for (String c : section.getKeys(false))
                    if (CityOccupy.cmanager.playerIn.containsKey(c))
                        for (Player player : CityOccupy.cmanager.playerIn.get(c))
                            if (e.getEntity().getName().equals(player.getName()))
                                CityOccupy.cmanager.playerIn.get(c).remove(player);

            }

        }
    }
}
