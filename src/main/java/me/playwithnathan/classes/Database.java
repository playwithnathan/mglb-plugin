package me.playwithnathan.classes;

import me.playwithnathan.Main;
import me.playwithnathan.util.ConfigUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static Connection con;

    public static void connect() {
        ConfigUtil configUtil = Main.getConfigUtil();
        String host = configUtil.getDB("host");
        String port = configUtil.getDB("port");
        String database = configUtil.getDB("database");
        String user = configUtil.getDB("user");
        String password = configUtil.getDB("password");

        try {
            con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, user, password);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void disconnect() {
        try {
            con.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // Get players from table
    public static List<String> getPlayers(String table) {
        List<String> uuids = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT uuid FROM " + table);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                uuids.add(rs.getString("uuid"));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return uuids;
    }

    // Get stat from table
    public static String getStat(Board board, String uuid) {
        String stat = "-1";
        try {
            PreparedStatement ps = con.prepareStatement("SELECT " + board.getColumn() + " FROM " + board.getTable() + " WHERE UUID = ?");
            ps.setString(1, uuid);

            ResultSet rs = ps.executeQuery();
            if(rs.next()) stat = rs.getString(board.getColumn());
        } catch(Exception e) {
            e.printStackTrace();
        }
        return stat;
    }

    public static void setStat(Board board, String uuid) {
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE " + board.getTable() + " SET " + board.getColumn() + " = ? WHERE uuid = ?");
            ps.setInt(1, 0);
            ps.setString(2, uuid);

            ps.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
