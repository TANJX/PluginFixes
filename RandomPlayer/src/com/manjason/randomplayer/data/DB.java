package com.manjason.randomplayer.data;

import com.manjason.randomplayer.RandomPlayer;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DB {

    private static String table = null;


    static {
        table = RandomPlayer.getInstance().config.getString("mysql.table");
    }

    public static Connection getConnection() {
        Connection conn = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException var9) {
            JOptionPane.showMessageDialog(null, "找不到驱动程序类 ，加载驱动失败！");
            var9.printStackTrace();
        }

        String ip = RandomPlayer.getInstance().config.getString("mysql.ip");
        String port = RandomPlayer.getInstance().config.getString("mysql.port");
        String database = RandomPlayer.getInstance().config.getString("mysql.database");
        String username = RandomPlayer.getInstance().config.getString("mysql.username");
        String password = RandomPlayer.getInstance().config.getString("mysql.password");
        String url = "jdbc:mysql://" + ip + ":" + port + "/" + database;

        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException var8) {
            JOptionPane.showMessageDialog(null, "数据库连接失败！");
            var8.printStackTrace();
        }

        return conn;
    }

    public static void init() {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getConnection();
            List e = loadSql(DB.class.getClassLoader().getResourceAsStream("randomplayer.sql"));

           for (Object anE : e) {
              String sql = (String) anE;
              sql = sql.replace("%table_name%", table);
              ps = conn.prepareStatement(sql);
              ps.execute();
           }
        } catch (Exception var13) {
            var13.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }

                if (ps != null) {
                    ps.close();
                }
            } catch (Exception var12) {
                var12.printStackTrace();
            }

        }

    }

    public static void add(String playerName, String serverName) {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "insert into " + table + "(player_name,server_name) values(?,?)";

        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, playerName);
            ps.setString(2, serverName);
            ps.execute();
        } catch (Exception var14) {
            var14.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }

                if (ps != null) {
                    ps.close();
                }
            } catch (Exception var13) {
                var13.printStackTrace();
            }

        }

    }

    public static String get(String playerName) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from " + table + " where player_name=?";

        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, playerName);
            rs = ps.executeQuery();
            if (rs.next()) {
                String var7 = rs.getString("server_name");
                return var7;
            }
        } catch (Exception var16) {
            var16.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }

                if (ps != null) {
                    ps.close();
                }
            } catch (Exception var15) {
                var15.printStackTrace();
            }

        }

        return null;
    }

    private static List loadSql(InputStream input) throws Exception {
        ArrayList sqlList = new ArrayList();

        try {
            StringBuilder ex = new StringBuilder();
            byte[] buff = new byte[1024];
            boolean byteRead = false;

            int var9;
            while ((var9 = input.read(buff)) != -1) {
                ex.append(new String(buff, 0, var9));
            }

            String[] sqlArr = ex.toString().split("(;//s*//r//n)|(;//s*//n)");

           for (String aSqlArr : sqlArr) {
              String sql = aSqlArr.replaceAll("--.*", "").trim();
              if (!sql.equals("")) {
                 sqlList.add(sql);
              }
           }

            return sqlList;
        } catch (Exception var8) {
            throw new Exception(var8.getMessage());
        }
    }
}
