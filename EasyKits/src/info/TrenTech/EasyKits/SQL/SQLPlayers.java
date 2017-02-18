package info.TrenTech.EasyKits.SQL;

import info.TrenTech.EasyKits.Utils.Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SQLPlayers extends SQLUtils {

    private static Object lock = new Object();

    public static boolean tableExist(String playerUUID) {
        try {
            Statement statement = getConnection().createStatement();
            DatabaseMetaData md = statement.getConnection().getMetaData();
            ResultSet rs = md.getTables(null, null, playerUUID, null);
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
        }
        return false;
    }

    public static void createTable(String playerUUID) {
        synchronized (lock) {
            try {
                PreparedStatement statement;
                statement = prepare("CREATE TABLE `" + playerUUID + "`( id INTEGER PRIMARY KEY, Kit TEXT, Cooldown TEXT, Limits INTEGER, Obtained TEXT)");
                statement.executeUpdate();
                Logger.getGlobal().info("Creating player tables");
            } catch (SQLException e) {
                Utils.getLogger().severe(e.getMessage());
            }
        }
    }

    public static void deleteTable(String playerUUID) {
        synchronized (lock) {
            try {
                ;
                PreparedStatement statement = prepare("DROP TABLE `" + playerUUID + "`");
                statement.executeUpdate();
            } catch (SQLException e) {
                Utils.getLogger().severe(e.getMessage());
            }
        }
    }

    public static boolean kitExist(String playerUUID, String kitName) {
        try {
            PreparedStatement statement = prepare("SELECT * FROM `" + playerUUID + "`");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                if (rs.getString("Kit").equalsIgnoreCase(kitName)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            Utils.getLogger().severe(e.getMessage());
        }
        return false;
    }

    public static void addKit(String playerUUID, String kitName, String cooldown, int limit, String obtainedBefore) {
        synchronized (lock) {
            try {
                PreparedStatement statement = prepare("INSERT into `" + playerUUID + "` (Kit, Cooldown, Limits, Obtained) VALUES (?, ?, ?, ?)");
                statement.setString(1, kitName);
                statement.setString(2, cooldown);
                statement.setInt(3, limit);
                statement.setString(4, obtainedBefore);
                statement.executeUpdate();
            } catch (SQLException e) {
                Utils.getLogger().severe(e.getMessage());
            }
        }
    }

    public static void setDateObtained(String playerUUID, String kitName, String cooldown) {
        synchronized (lock) {
            try {
                PreparedStatement statement = prepare("UPDATE `" + playerUUID + "` SET Cooldown = ? WHERE Kit = ?");
                statement.setString(1, cooldown);
                statement.setString(2, kitName);
                statement.executeUpdate();
            } catch (SQLException e) {
                Utils.getLogger().severe(e.getMessage());
            }
        }
    }

    public static void setObtained(String playerUUID, String kitName, String obtainedBefore) {
        synchronized (lock) {
            try {
                PreparedStatement statement = prepare("UPDATE `" + playerUUID + "` SET Obtained = ? WHERE Kit = ?");
                statement.setString(1, obtainedBefore);
                statement.setString(2, kitName);
                statement.executeUpdate();
            } catch (SQLException e) {
                Utils.getLogger().severe(e.getMessage());
            }
        }
    }

    public static void setLimit(String playerUUID, String kitName, int limit) {
        synchronized (lock) {
            try {
                PreparedStatement statement = prepare("UPDATE `" + playerUUID + "` SET Limits = ? WHERE Kit = ?");
                statement.setInt(1, limit);
                statement.setString(2, kitName);
                statement.executeUpdate();
            } catch (SQLException e) {
                Utils.getLogger().severe(e.getMessage());
            }
        }
    }

    public static String getDateObtained(String playerUUID, String kitName) {
        try {
            PreparedStatement statement = prepare("SELECT * FROM `" + playerUUID + "`");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                if (rs.getString("Kit").equalsIgnoreCase(kitName)) {
                    return rs.getString("Cooldown");
                }
            }
        } catch (SQLException e) {
            Utils.getLogger().severe(e.getMessage());
        }
        return "0";
    }

    public static int getLimit(String playerUUID, String kitName) {
        try {
            PreparedStatement statement = prepare("SELECT * FROM `" + playerUUID + "`");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                if (rs.getString("Kit").equalsIgnoreCase(kitName)) {
                    return rs.getInt("Limits");
                }
            }
        } catch (SQLException e) {
            Utils.getLogger().severe(e.getMessage());
        }
        return 0;
    }

    public static String getObtained(String playerUUID, String kitName) {
        try {
            PreparedStatement statement = prepare("SELECT * FROM `" + playerUUID + "`");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                if (rs.getString("Kit").equalsIgnoreCase(kitName)) {
                    return rs.getString("Obtained");
                }
            }
        } catch (SQLException e) {
            Utils.getLogger().severe(e.getMessage());
        }
        return "FALSE";
    }

    public static List<String> getPlayerList() {
        List<String> playerList = new ArrayList<String>();
        try {
            Statement statement = getConnection().createStatement();
            DatabaseMetaData md = statement.getConnection().getMetaData();
            ResultSet rs = md.getTables(null, null, "%", null);
            while (rs.next()) {
                if (!rs.getString(3).equalsIgnoreCase("kits")) {
                    playerList.add(rs.getString(3));
                }
            }
        } catch (SQLException e) {
            Utils.getLogger().severe(e.getMessage());
        }
        return playerList;
    }
}
