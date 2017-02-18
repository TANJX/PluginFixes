package com.hundunstar.cityoccupy.selection;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Hashtable;
import java.util.Map;

public class COSelectionManager {
    private final Map<String, Location> playerLoc1;//ѡ��ĵ�һ����
    private final Map<String, Location> playerLoc2;//ѡ��ĵڶ�����
    private static final int MAX_HEIGHT = 255;
    private static final int MIN_HEIGHT = 0;

    public COSelectionManager() {
        playerLoc1 = new Hashtable<>();
        playerLoc2 = new Hashtable<>();
    }

    public void placeLoc1(Player player, Location loc)//���õ�һ����
    {
        if (loc != null) {
            playerLoc1.put(player.getName(), loc);
        }
    }

    public void placeLoc2(Player player, Location loc)//���õڶ�����
    {
        if (loc != null) {
            playerLoc2.put(player.getName(), loc);
        }
    }

    public Location getPlayerLoc1(String player)//��ȡѡ��ĵ�һ����
    {
        return playerLoc1.get(player);
    }

    public Location getPlayerLoc2(String player)//��ȡѡ��ĵڶ�����
    {
        return playerLoc2.get(player);
    }

    public static boolean isBlockOutofHeight(Block b)//�жϷ�����û�г���ָ���߶ȷ�Χ
    {
        return b.getY() >= MIN_HEIGHT && b.getY() <= MAX_HEIGHT;
    }
}
