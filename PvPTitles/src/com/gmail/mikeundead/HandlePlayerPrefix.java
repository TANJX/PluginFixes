//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.gmail.mikeundead;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;

public class HandlePlayerPrefix implements Listener {
    private DatabaseHandler databaseHandler;
    private Ranks ranks;
    private PvPTitles pvpTitles;
    Map<String, Integer> map = new HashMap();

    public HandlePlayerPrefix(DatabaseHandler databaseHandler, Ranks ranks, PvPTitles pvpTitles) {
        this.databaseHandler = databaseHandler;
        this.ranks = ranks;
        this.pvpTitles = pvpTitles;
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();

        try {
            this.databaseHandler.FirstRun(player.getName());
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        this.map.put(player.getName(), Integer.valueOf(0));
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String rank = null;
        boolean chat = true;
        this.databaseHandler.LoadPlayerFame(event.getPlayer().getName());
        this.databaseHandler.LoadConfig();
        rank = this.ranks.GetRank(this.databaseHandler.PlayerFame());
        chat = this.databaseHandler.getChat();
        if(event.getPlayer().hasPermission("pvptitles.chat") && rank != null && rank != "" && chat) {
            String a = String.format(ChatColor.WHITE + "[" + this.databaseHandler.PrefixColor + rank + ChatColor.WHITE + "]", new Object[0]);
            String format = event.getFormat();
            event.setFormat(a + format);
        }

    }

    @EventHandler
    public void onKill(PlayerDeathEvent death) {
        int kills = 0;
        if(death.getEntity().getKiller() instanceof Player) {
            String killed = death.getEntity().getName();
            Player player = death.getEntity().getKiller();
            if(this.map.containsKey(player.getName())) {
                kills = ((Integer)this.map.get(player.getName())).intValue();
            }

            if(this.map.containsKey(killed)) {
                this.map.put(killed, Integer.valueOf(0));
            }

            this.databaseHandler.LoadPlayerFame(player.getName());
            int fame = this.databaseHandler.PlayerFame();
            if(!player.getName().equalsIgnoreCase(killed)) {
                this.calculateFame(killed, player, fame, kills);
            }

            ++kills;
            this.map.put(player.getName(), Integer.valueOf(kills));
        }

    }

    private void calculateFame(String killed, Player player, int fame, int kills) {
        int a = this.databaseHandler.PlayerFame();
        String tag = this.databaseHandler.getTag();
        if(kills == 0) {
            ++fame;
            player.sendMessage(ChatColor.GREEN + "你杀了" + killed + "  得到了 2 " + tag + ".");
        }

        if(kills == 1) {
            fame += 2;
            player.sendMessage(ChatColor.GREEN + "你杀了" + killed + "  得到了 2 " + tag + ".");
        }

        if(kills == 2) {
            fame += 3;
            player.sendMessage(ChatColor.GREEN + "你杀了" + killed + "  得到了 3 " + tag + ".");
        }

        if(kills == 3) {
            fame += 4;
            player.sendMessage(ChatColor.GREEN + "你杀了" + killed + "  得到了 4 " + tag + ".");
        }

        if(kills == 4) {
            fame += 6;
            player.sendMessage(ChatColor.GREEN + "你杀了" + killed + "  得到了 6 " + tag + ".");
        }

        if(kills == 5) {
            fame += 8;
            player.sendMessage(ChatColor.GREEN + "你杀了" + killed + "  得到了 8 " + tag + ".");
        }

        if(kills == 6) {
            fame += 12;
            player.sendMessage(ChatColor.GREEN + "你杀了" + killed + "  得到了 3 " + tag + ".");
        }

        if(kills == 7) {
            fame += 16;
            player.sendMessage(ChatColor.GREEN + "你杀了" + killed + "  得到了 16 " + tag + ".");
        }

        if(kills == 8) {
            fame += 20;
            player.sendMessage(ChatColor.GREEN + "你杀了" + killed + "  得到了 20 " + tag + ".");
        }

        if(kills == 9) {
            fame += 24;
            player.sendMessage(ChatColor.GREEN + "你杀了" + killed + "  得到了 24 " + tag + ".");
        }

        if(kills == 10) {
            fame += 28;
            player.sendMessage(ChatColor.GREEN + "你杀了" + killed + "  得到了 28 " + tag + ".");
        }

        if(kills == 11) {
            fame += 32;
            player.sendMessage(ChatColor.GREEN + "你杀了" + killed + "  得到了 32 " + tag + ".");
        }

        if(kills == 12) {
            fame += 36;
            player.sendMessage(ChatColor.GREEN + "你杀了" + killed + "  得到了 36 " + tag + ".");
        }

        if(kills >= 13) {
            fame += 40;
            player.sendMessage(ChatColor.GREEN + "你杀了" + killed + "  得到了 40 " + tag + ".");
        }

        this.databaseHandler.SavePlayerFame(player.getName(), fame);
        this.databaseHandler.LoadPlayerFame(player.getName());
        String currentRank = this.ranks.GetRank(a);
        String newRank = this.ranks.GetRank(fame);
        if(!currentRank.equalsIgnoreCase(newRank)) {
            player.sendMessage(ChatColor.GREEN + "恭喜你现在是" + newRank);
        }

    }
}
