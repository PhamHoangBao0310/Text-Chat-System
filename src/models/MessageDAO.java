/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import db.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class MessageDAO {

    private Connection conn;
    private PreparedStatement preStm;
    private ResultSet rs;

    private void closeConnection() throws Exception {
        if (rs != null) {
            rs.close();
        }
        if (preStm != null) {
            preStm.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    public String getMessage(int idFrom, int idTo) throws Exception {
        String result = null;
        try {
            String sql = "Select Mess from Message where IDFrom = ? and IDTo = ?";
            conn = MyConnection.makeConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, idFrom);
            preStm.setInt(2, idTo);
            rs = preStm.executeQuery();
            if (rs.next()) {
                result = rs.getString("Mess");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean saveMessage(int idFrom, int idTo, String mess) throws Exception {
        try {
            String sql = "Update Message set Mess = ? where IDFrom = ? and IDTo = ? ";
            conn = MyConnection.makeConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, mess);
            preStm.setInt(2, idFrom);
            preStm.setInt(3, idTo);
            int result = preStm.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return false;
    }

    public boolean addMoreUnread(int idFrom, int idTo) throws Exception {
        try {
            String sql = "Update Message set Unread = Unread + 1 where IDFrom = ? and IDTo = ? ";
            conn = MyConnection.makeConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, idFrom);
            preStm.setInt(2, idTo);
            int result = preStm.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return false;
    }

    public boolean setDefaultUnread(int idFrom, int idTo) throws Exception {
        try {
            String sql = "Update Message set Unread = 0 where IDFrom = ? and IDTo = ? ";
            conn = MyConnection.makeConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, idFrom);
            preStm.setInt(2, idTo);
            int result = preStm.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return false;
    }

    public List<Integer> getlistUnreadUserId(int id) throws Exception {
        List<Integer> list = null;
        try {
            String sql = "select IDFrom from Message where IDTo = ? and Unread > 0";
            conn = MyConnection.makeConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, id);
            rs = preStm.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                list.add(rs.getInt("IDFrom"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return list;
    }

    public void insertNewMessage(int newID, List<Integer> listCurrID) throws Exception {
        try {
            for (int id : listCurrID) {
                if (id != newID) {
                    String sql = "insert into Message(IDFrom, IDTO, Unread) values(?,?,?)";
                    conn = MyConnection.makeConnection();
                    preStm = conn.prepareStatement(sql);
                    preStm.setInt(1, id);
                    preStm.setInt(2, newID);
                    preStm.setInt(3, 0);
                    preStm.executeUpdate();
                    preStm.setInt(1, newID);
                    preStm.setInt(2, id);
                    preStm.setInt(3, 0);
                    preStm.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }
}
