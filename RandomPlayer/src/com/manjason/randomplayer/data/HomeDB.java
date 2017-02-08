package com.manjason.randomplayer.data;

import com.manjason.randomplayer.RandomPlayer;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HomeDB {

    private static String table = null;


    static {
        table = RandomPlayer.getInstance().config.getString("bungeesuite_mysql.table");
    }

    private static Connection getConnection() {
        Connection conn = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException var9) {
            JOptionPane.showMessageDialog(null, "找不到驱动程序类 ，加载驱动失败！");
            var9.printStackTrace();
        }

        String ip = RandomPlayer.getInstance().config.getString("bungeesuite_mysql.ip");
        String port = RandomPlayer.getInstance().config.getString("bungeesuite_mysql.port");
        String database = RandomPlayer.getInstance().config.getString("bungeesuite_mysql.database");
        String username = RandomPlayer.getInstance().config.getString("bungeesuite_mysql.username");
        String password = RandomPlayer.getInstance().config.getString("bungeesuite_mysql.password");
        String url = "jdbc:mysql://" + ip + ":" + port + "/" + database;

        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException var8) {
            JOptionPane.showMessageDialog(null, "数据库连接失败！");
            var8.printStackTrace();
        }

        return conn;
    }

    public static List<String> gets(String playerName) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs;
        ArrayList<String> homes = new ArrayList<>();
        String sql = "select * from " + table + " where player=?";

        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, playerName);
            rs = ps.executeQuery();

            while (rs.next()) {
                homes.add(rs.getString("server"));
            }
        } catch (Exception var15) {
            var15.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }

                if (ps != null) {
                    ps.close();
                }
            } catch (Exception var14) {
                var14.printStackTrace();
            }

        }

        return homes;
    }
}
