//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.gmail.mikeundead;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RankCommand implements CommandExecutor {
    private DatabaseHandler databaseHandler;
    private Ranks ranks;

    public RankCommand(DatabaseHandler databaseHandler, Ranks ranks) {
        this.databaseHandler = databaseHandler;
        this.ranks = ranks;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        } else return true;

        if (cmd.getName().equalsIgnoreCase("rank")) {
            this.HandleRankCmd(player);
        }

        if (args.length > 0) {
            player.sendMessage(ChatColor.RED + "Too many arguments!");
        }

        return true;
    }

    private void HandleRankCmd(Player player) {
        this.databaseHandler.LoadPlayerFame(player.getName());
        this.databaseHandler.LoadConfig();
        int fame = this.databaseHandler.PlayerFame();
        String rank = this.ranks.GetRank(fame);
        int rankup = this.ranks.FameToRankUp();
        String tag = this.databaseHandler.getTag();
        if (player.hasPermission("pvptitles.rank")) {
            if (rank == "") {
                player.sendMessage("Rank: none");
            } else {
                player.sendMessage("§a军衔: " + rank);
            }

            player.sendMessage(tag + ": " + fame);
            if (rankup == 999999) {
                player.sendMessage("§a你的声望已经达到最大");
            } else {
                player.sendMessage("§a升级所需:" + rankup);
            }
        } else {
            player.sendMessage(ChatColor.RED + "§c你没有权限使用此命令");
        }

    }
}
