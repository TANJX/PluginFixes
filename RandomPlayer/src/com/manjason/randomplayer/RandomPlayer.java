package com.manjason.randomplayer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.manjason.randomplayer.data.DB;
import com.manjason.randomplayer.data.HomeDB;
import com.manjason.randomplayer.lang.Lang;
import com.manjason.randomplayer.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;

public class RandomPlayer
        extends JavaPlugin
        implements Listener,
        PluginMessageListener {
    public FileConfiguration config;
    private static RandomPlayer instance = null;
    private List<ServerInfo> sis = new ArrayList<>();
    private static Map<String, Long> interval = new HashMap<>();

    private static boolean getInterval(String playerName) {
        if (interval.containsKey(playerName) && System.currentTimeMillis() - interval.get(playerName) < 10000) {
            return false;
        }
        interval.put(playerName, System.currentTimeMillis());
        return true;
    }

    public static RandomPlayer getInstance() {
        return instance;
    }

    public void onLoad() {
        instance = this;
        this.saveDefaultConfig();
        this.saveResource("lang_zh.yml", false);
        this.config = this.getConfig();
        System.out.println(MessageFormat.format(Util.convertColor(Lang.get(1)), this.getName()));
    }

    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        DB.init();
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        Bukkit.getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (String server : RandomPlayer.getInstance().config.getStringList("servers")) {
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(b);
                try {
                    out.writeUTF("PlayerCount");
                    out.writeUTF(server);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Player[] ps = Bukkit.getOnlinePlayers().toArray(new Player[0]);
                if (ps.length <= 0) continue;
                ps[0].sendPluginMessage(RandomPlayer.getInstance(), "BungeeCord", b.toByteArray());
            }
        }, 20, 20);
        System.out.println(MessageFormat.format(Util.convertColor(Lang.get(2)), this.getName()));
    }

    public void onDisable() {
        HandlerList.unregisterAll((Plugin) this);
        this.getServer().getScheduler().cancelTasks(this);
        System.out.println(MessageFormat.format(Util.convertColor(Lang.get(3)), this.getName()));
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            Player p = (Player) sender;
            if (!RandomPlayer.getInterval(p.getName())) {
                Util.sendMsg(p, Util.convertColor(Lang.get(5)));
                return true;
            }
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);
            try {
                out.writeUTF("Connect");
                List<String> homes = HomeDB.gets(p.getName());
                if (!homes.isEmpty()) {
                    out.writeUTF(homes.get(homes.size() - 1));
                } else {
                    String server = DB.get(p.getName());
                    if (server != null) {
                        out.writeUTF(server);
                    } else {
                        DB.add(p.getName(), this.sis.get(0).getName());
                        out.writeUTF(this.sis.get(0).getName());
                    }
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            p.sendPluginMessage(this, "BungeeCord", b.toByteArray());
        }
        return false;
    }

    public void onPluginMessageReceived(String channel, Player p, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        try {
            ByteArrayDataInput in = ByteStreams.newDataInput(message);
            String subchannel = in.readUTF();
            if (subchannel.equals("PlayerCount")) {
                String server = in.readUTF();
                int playercount = in.readInt();
                ServerInfo si = new ServerInfo();
                si.setName(server);
                si.setPlayers(playercount);
                int i = 0;
                while (i < this.sis.size()) {
                    ServerInfo tempSi = this.sis.get(i);
                    if (!config.getStringList("servers").contains(tempSi.getName()))
                        return;
                    if (si.getName().equals(tempSi.getName())) {
                        this.sis.set(i, si);
                        ComparatorServerInfo csi = new ComparatorServerInfo();
                        (this.sis).sort(csi);
                        return;
                    }
                    ++i;
                }
                this.sis.add(si);
                ComparatorServerInfo csi = new ComparatorServerInfo();
                (this.sis).sort(csi);
            }
        } catch (Exception in) {
            // empty catch block
        }
    }

    public class ComparatorServerInfo
            implements Comparator {
        public int compare(Object arg0, Object arg1) {
            ServerInfo si1 = (ServerInfo) arg0;
            ServerInfo si2 = (ServerInfo) arg1;
            return new Integer(si1.getPlayers()).compareTo(si2.getPlayers());
        }
    }

}

