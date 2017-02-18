package com.hundunstar.cityoccupy.listener;

import com.hundunstar.cityoccupy.CityOccupy;
import com.hundunstar.cityoccupy.selection.COSelectionManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class COSelectEventListener implements Listener {
    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.HIGHEST)
    public void select(PlayerInteractEvent e) {//ѡ����з�Χ
        if (e.getItem() != null)
            if (e.getItem().getType() == Material.getMaterial(CityOccupy.config.getInt("selectItem")) && e.getPlayer().hasPermission("cityoccupy.create")) {
                if (e.getAction() == Action.LEFT_CLICK_BLOCK && COSelectionManager.isBlockOutofHeight(e.getClickedBlock())) {
                    CityOccupy.smanager.placeLoc1(e.getPlayer(), e.getClickedBlock().getLocation());
                    e.getPlayer().sendMessage("��һ��ѡ���: (" + e.getClickedBlock().getX() + "," + e.getClickedBlock().getY() + "," + e.getClickedBlock().getZ() + ") �ѱ�ѡ��");
                }
                if (e.getAction() == Action.RIGHT_CLICK_BLOCK && COSelectionManager.isBlockOutofHeight(e.getClickedBlock())) {
                    CityOccupy.smanager.placeLoc2(e.getPlayer(), e.getClickedBlock().getLocation());
                    e.getPlayer().sendMessage("�ڶ���ѡ���: (" + e.getClickedBlock().getX() + "," + e.getClickedBlock().getY() + "," + e.getClickedBlock().getZ() + ") �ѱ�ѡ��");
                }
            }
    }
}
