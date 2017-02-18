/*
 * 
 */
package info.TrenTech.EasyKits.SQL;

import info.TrenTech.EasyKits.Kit.Kit;
import info.TrenTech.EasyKits.Utils.Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class SQLKits extends SQLUtils {

    private static Object lock = new Object();

    public static boolean tableExist() {
        try {
            Statement statement = getConnection().createStatement();
            DatabaseMetaData md = statement.getConnection().getMetaData();
            ResultSet rs = md.getTables(null, null, "kits", null);
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
        }
        return false;
    }

    public static void createTable() {
        synchronized (lock) {
            try {
                PreparedStatement statement;
                statement = prepare("CREATE TABLE kits( id INTEGER PRIMARY KEY, Kit TEXT, Inventory BLOB, Armor BLOB, Price DOUBLE, Cooldown TEXT, Limits INTEGER, Creator TEXT)");
                statement.executeUpdate();
            } catch (SQLException e) {
                Utils.getLogger().severe(e.getMessage());
            }
        }
    }

    public static boolean kitExist(String kitName) {

        try {
            PreparedStatement statement = prepare("SELECT * FROM kits");
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

    public static void createKit(String kitName, byte[] inv, byte[] armor, double price, String cooldown, int limit, String creator) {
        synchronized (lock) {
            try {
                PreparedStatement statement = prepare("INSERT into kits (Kit, Inventory, Armor, Price, Cooldown, Limits, Creator) VALUES (?, ?, ?, ?, ?, ?, ?)");
                statement.setString(1, kitName);
                statement.setBytes(2, inv);
                statement.setBytes(3, armor);
                statement.setDouble(4, price);
                statement.setString(5, cooldown);
                statement.setInt(6, limit);
                statement.setString(7, creator);
                statement.executeUpdate();
            } catch (SQLException e) {
                Utils.getLogger().severe(e.getMessage());
            }
        }
    }

    public static void setKitCooldown(String kitName, String cooldown) {
        synchronized (lock) {
            try {
                PreparedStatement statement = prepare("UPDATE kits SET Cooldown = ? WHERE Kit = ?");
                statement.setString(1, cooldown);
                statement.setString(2, kitName);
                statement.executeUpdate();
            } catch (SQLException e) {
                Utils.getLogger().severe(e.getMessage());
            }
        }
    }

    public static void setKitLimit(String kitName, int limit) {
        synchronized (lock) {
            try {
                PreparedStatement statement = prepare("UPDATE kits SET Limits = ? WHERE Kit = ?");
                statement.setInt(1, limit);
                statement.setString(2, kitName);
                statement.executeUpdate();
            } catch (SQLException e) {
                Utils.getLogger().severe(e.getMessage());
            }
        }
    }

    public static void setKitPrice(String kitName, double price) {
        synchronized (lock) {
            try {
                PreparedStatement statement = prepare("UPDATE kits SET Price = ? WHERE Kit = ?");
                statement.setDouble(1, price);
                statement.setString(2, kitName);
                statement.executeUpdate();
            } catch (SQLException e) {
                Utils.getLogger().severe(e.getMessage());
            }
        }
    }

    public static void setKitCreator(String kitName, String creator) {
        synchronized (lock) {
            try {
                PreparedStatement statement = prepare("UPDATE kits SET Creator = ? WHERE Kit = ?");
                statement.setString(1, creator);
                statement.setString(2, kitName);
                statement.executeUpdate();
            } catch (SQLException e) {
                Utils.getLogger().severe(e.getMessage());
            }
        }
    }

    public static void setInventory(String kitName, byte[] inv) {
        synchronized (lock) {
            try {
                if (inv != null) {
                    PreparedStatement statement = prepare("UPDATE kits SET Inventory = ? WHERE Kit = ?");

                    statement.setBytes(1, inv);
                    statement.setString(2, kitName);
                    statement.executeUpdate();
                }
            } catch (SQLException e) {
                Utils.getLogger().severe(e.getMessage());
            }
        }
    }

    public static void setArmor(String kitName, byte[] armor) {
        synchronized (lock) {
            try {
                if (armor != null) {
                    PreparedStatement statement = prepare("UPDATE kits SET Armor = ? WHERE Kit = ?");
                    statement.setBytes(1, armor);
                    statement.setString(2, kitName);
                    statement.executeUpdate();
                }
            } catch (SQLException e) {
                Utils.getLogger().severe(e.getMessage());
            }
        }
    }

    public static byte[] getKitInventory(String kitName) {
        try {
            PreparedStatement statement = prepare("SELECT * FROM kits");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                if (rs.getString("Kit").equalsIgnoreCase(kitName)) {
                    return rs.getBytes("Inventory");
                }
            }
        } catch (SQLException e) {
            Utils.getLogger().severe(e.getMessage());
        }
        return null;
    }

    public static byte[] getKitArmor(String kitName) {
        try {
            PreparedStatement statement = prepare("SELECT * FROM kits");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                if (rs.getString("Kit").equalsIgnoreCase(kitName)) {
                    return rs.getBytes("Armor");
                }
            }
        } catch (SQLException e) {
            Utils.getLogger().severe(e.getMessage());
        }
        return null;
    }

    public static double getKitPrice(String kitName) {
        try {
            PreparedStatement statement = prepare("SELECT * FROM kits");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                if (rs.getString("Kit").equalsIgnoreCase(kitName)) {
                    return rs.getDouble("Price");
                }
            }
        } catch (SQLException e) {
            Utils.getLogger().severe(e.getMessage());
        }
        return 0;
    }

    public static String getKitCreator(String kitName) {
        try {
            PreparedStatement statement = prepare("SELECT * FROM kits");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                if (rs.getString("Kit").equalsIgnoreCase(kitName)) {
                    return rs.getString("Creator");
                }
            }
        } catch (SQLException e) {
            Utils.getLogger().severe(e.getMessage());
        }
        return null;
    }

    public static String getKitCooldown(String kitName) {
        try {
            PreparedStatement statement = prepare("SELECT * FROM kits");
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

    public static int getKitLimit(String kitName) {
        try {
            PreparedStatement statement = prepare("SELECT * FROM kits");
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

    public static List<Kit> getKitList() {
        List<Kit> kitList = new ArrayList<Kit>();
        try {
            PreparedStatement statement = prepare("SELECT * FROM kits");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Kit kit = new Kit(rs.getString("Kit"));
                kitList.add(kit);
            }
        } catch (SQLException e) {
            Utils.getLogger().severe(e.getMessage());
        }
        return kitList;
    }

    public static void deleteKit(String kitName) {
        try {
            PreparedStatement statement = prepare("DELETE from kits WHERE Kit = ?");
            statement.setString(1, kitName);
            statement.executeUpdate();
        } catch (SQLException e) {
            Utils.getLogger().severe(e.getMessage());
        }
    }
}