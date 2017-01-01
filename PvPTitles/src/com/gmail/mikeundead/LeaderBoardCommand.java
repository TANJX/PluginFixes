//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.gmail.mikeundead;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;

public class LeaderBoardCommand implements CommandExecutor {
    private PvPTitles pvpTitles;
    private TreeMap<Integer, String> RankedPlayers;

    public LeaderBoardCommand(PvPTitles pvpTitles) {
        this.pvpTitles = pvpTitles;
        this.RankedPlayers = new TreeMap();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        Player player = null;
        if(sender instanceof Player) {
            player = (Player)sender;
        }

        if(cmd.getName().equalsIgnoreCase("ladder")) {
            this.LadderCmd(player);
        }

        if(args.length > 0) {
            player.sendMessage(ChatColor.RED + "Too many arguments!");
        }

        return true;
    }

    private void LadderCmd(Player player) {
        this.SetTopTenPlayers(player);
        if(player.hasPermission("pvptitles.ladder")) {
            player.sendMessage(ChatColor.AQUA + "排行榜 - 前五名玩家");
            player.sendMessage(ChatColor.AQUA + "------------------------");
            NavigableMap sortedMap = this.RankedPlayers.descendingMap();
            int number = 0;

            for(Iterator i$ = sortedMap.entrySet().iterator(); i$.hasNext(); ++number) {
                Entry entry = (Entry)i$.next();
                if(number == 5) {
                    break;
                }

                player.sendMessage(number + 1 + ". " + entry.getValue() + " (" + entry.getKey() + ")");
            }

            sortedMap.clear();
            this.RankedPlayers.clear();
        } else {
            player.sendMessage(ChatColor.RED + "You are not allowed to use this command");
        }

    }

    private void SetTopTenPlayers(Player player) {
        File file = new File(this.pvpTitles.getDataFolder() + File.separator + "players");
        File[] allFiles = file.listFiles();
        File[] arr$ = allFiles;
        int len$ = allFiles.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            File item = arr$[i$];
            File ladderFile = new File(this.pvpTitles.getDataFolder() + File.separator + "players" + File.separator + item.getName());
            YamlConfiguration config = YamlConfiguration.loadConfiguration(ladderFile);
            int fame = config.getInt("fame");
            this.RankedPlayers.put(fame, item.getName().replaceAll(".yml", ""));
        }

    }
}
