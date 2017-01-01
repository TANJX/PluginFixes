//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.gmail.mikeundead;

import java.util.Map;

public class Ranks {
    private int nextRank;
    private DatabaseHandler databaseHandler;
    private Map<Integer, String> rankList;
    private Map<Integer, Integer> reqFame;
    private PvPTitles pvpTitles;

    public Ranks(DatabaseHandler databaseHandler, PvPTitles pvpTitles) {
        this.databaseHandler = databaseHandler;
        this.databaseHandler.LoadConfig();
        this.rankList = this.databaseHandler.RankList();
        this.reqFame = this.databaseHandler.reqFame();
        this.pvpTitles = pvpTitles;
    }

    public String GetRank(int fame) {
        String rank = "";
        this.reqFame = this.databaseHandler.reqFame();

        for(int i = 0; i < this.reqFame.size(); ++i) {
            if(fame >= ((Integer)this.reqFame.get(Integer.valueOf(0))).intValue() && fame < ((Integer)this.reqFame.get(Integer.valueOf(1))).intValue()) {
                if(!((String)this.rankList.get(Integer.valueOf(0))).equalsIgnoreCase("none")) {
                    rank = (String)this.rankList.get(Integer.valueOf(0));
                }

                this.nextRank = ((Integer)this.reqFame.get(Integer.valueOf(1))).intValue() - fame;
                break;
            }

            if(fame >= ((Integer)this.reqFame.get(Integer.valueOf(i))).intValue() && fame < ((Integer)this.reqFame.get(Integer.valueOf(i + 1))).intValue()) {
                rank = (String)this.rankList.get(Integer.valueOf(i));
                this.nextRank = ((Integer)this.reqFame.get(Integer.valueOf(i + 1))).intValue() - fame;
                break;
            }

            if(fame >= ((Integer)this.reqFame.get(Integer.valueOf(this.reqFame.values().size() - 1))).intValue()) {
                rank = (String)this.rankList.get(Integer.valueOf(this.reqFame.values().size() - 1));
                this.nextRank = 999999;
                break;
            }
        }

        return rank;
    }

    public int FameToRankUp() {
        return this.nextRank;
    }
}
