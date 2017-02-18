package com.hundunstar.cityoccupy.selection;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Hashtable;
import java.util.Map;

public class COSelectionManager {
    private final Map<String, Location> playerLoc1;//选择的第一个点
    private final Map<String, Location> playerLoc2;//选择的第二个点
    private static final int MAX_HEIGHT = 255;
    private static final int MIN_HEIGHT = 0;

    public COSelectionManager() {
        playerLoc1 = new Hashtable<>();
        playerLoc2 = new Hashtable<>();
    }

    public void placeLoc1(Player player, Location loc)//设置第一个点
    {
        if (loc != null) {
            playerLoc1.put(player.getName(), loc);
        }
    }

    public void placeLoc2(Player player, Location loc)//设置第二个点
    {
        if (loc != null) {
            playerLoc2.put(player.getName(), loc);
        }
    }

    public Location getPlayerLoc1(String player)//获取选择的第一个点
    {
        return playerLoc1.get(player);
    }

    public Location getPlayerLoc2(String player)//获取选择的第二个点
    {
        return playerLoc2.get(player);
    }

    public static boolean isBlockOutofHeight(Block b)//判断方块有没有超出指定高度范围
    {
        return b.getY() >= MIN_HEIGHT && b.getY() <= MAX_HEIGHT;
    }
}
