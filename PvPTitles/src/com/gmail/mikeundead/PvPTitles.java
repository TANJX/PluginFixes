//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.gmail.mikeundead;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class PvPTitles extends JavaPlugin {
    public Logger log;
    private RankCommand rankCommand;
    private DatabaseHandler databaseHandler;
    private Ranks ranks;
    private HandlePlayerPrefix handlePlayerPrefix;
    private LeaderBoardCommand ladder;

    public void onEnable() {
        this.log = this.getLogger();
        this.databaseHandler = new DatabaseHandler(this);
        this.ranks = new Ranks(this.databaseHandler, this);
        this.rankCommand = new RankCommand(this.databaseHandler, this.ranks);
        this.handlePlayerPrefix = new HandlePlayerPrefix(this.databaseHandler, this.ranks, this);
        this.ladder = new LeaderBoardCommand(this);
        this.getServer().getPluginManager().registerEvents(this.handlePlayerPrefix, this);
        this.getCommand("rank").setExecutor(this.rankCommand);
        this.getCommand("ladder").setExecutor(this.ladder);
        this.log.info("声望插件已经启用！");
    }

    public void onDisable() {
        this.log.info("声望插件已经被禁用！");
    }
}
