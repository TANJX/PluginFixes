package com.hundunstar.cityoccupy.listener;

import com.hundunstar.cityoccupy.CityOccupy;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class COResetSelectionEventListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent e)//清空玩家选择数据及设置初始数据
    {
        CityOccupy.smanager.placeLoc1(e.getPlayer(), new Location(e.getPlayer().getWorld(), 0, 257, 0));
        CityOccupy.smanager.placeLoc2(e.getPlayer(), new Location(e.getPlayer().getWorld(), 0, 257, 0));
        CityOccupy.logger.info("Player " + e.getPlayer().getName() + "'s CityOccupy Selection has been reset");
    }

    public void onPlayerChangedWorld(PlayerChangedWorldEvent e) {
        CityOccupy.smanager.placeLoc1(e.getPlayer(), new Location(e.getPlayer().getWorld(), 0, 257, 0));
        CityOccupy.smanager.placeLoc2(e.getPlayer(), new Location(e.getPlayer().getWorld(), 0, 257, 0));
        CityOccupy.logger.info("Player " + e.getPlayer().getName() + "'s CityOccupy Selection has been reset");
    }
}
