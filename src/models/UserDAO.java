/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import db.MyConnection;
import dtos.UserDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class UserDAO {

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

    public List<Integer> getAllCurrUserID() throws Exception {
        List<Integer> list = null;
        try {
            String sql = "Select ID from Users ";
            conn = MyConnection.makeConnection();
            preStm = conn.prepareStatement(sql);
            rs = preStm.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("ID");
                list.add(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return list;
    }

    public int getMaxID() throws Exception {
        try {
            String sql = "Select MAX(ID) as ID from Users ";
            conn = MyConnection.makeConnection();
            preStm = conn.prepareStatement(sql);
            rs = preStm.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("ID");
                return id;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return 0;
    }

    public List<UserDTO> getAllUser(int userId) throws Exception {
        List<UserDTO> list = null;
        UserDTO dto = null;
        try {
            String sql = "Select ID , LoginName , IsActive from Users  where ID <> ?";
            conn = MyConnection.makeConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, userId);
            rs = preStm.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("ID");
                String loginName = rs.getString("LoginName");
                int status = rs.getInt("IsActive");
                dto = new UserDTO(id, loginName, status);
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return list;
    }

    public List<UserDTO> getUser(List<Integer> listUsetID) throws Exception {
        List<UserDTO> list = null;
        if (listUsetID != null) {
            list = new ArrayList<>();
            for (Integer userID : listUsetID) {
                try {
                    String sql = "Select ID , LoginName , IsActive from Users  where ID = ?";
                    conn = MyConnection.makeConnection();
                    preStm = conn.prepareStatement(sql);
                    preStm.setInt(1, userID);
                    rs = preStm.executeQuery();
                    if (rs.next()) {
                        int id = rs.getInt("ID");
                        String loginName = rs.getString("LoginName");
                        int status = rs.getInt("IsActive");
                        UserDTO user = new UserDTO(id, loginName, status);
                        list.add(user);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    closeConnection();
                }
            }

        }
        return list;
    }

    public boolean register(UserDTO dto) throws Exception {
        boolean check = false;
        try {
            String sql = "Insert into Users(UserName, LoginName, Password, IsActive) "
                    + "values(?,?,?,?)";
            conn = MyConnection.makeConnection();
            preStm = conn.prepareStatement(sql);
            System.out.println(dto.toString());
            preStm.setString(1, dto.getUsername());
            preStm.setString(2, dto.getLoginName());
            preStm.setString(3, dto.getPassword());
            preStm.setInt(4, dto.getStatus());
            check = preStm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return check;
    }

    public UserDTO checkLogin(String loginName, String password) throws Exception {
        UserDTO user = null;
        try {
            String sql = "Select ID ,UserName , IsActive "
                    + "from Users "
                    + "where LoginName = ? and Password = ?";
            conn = MyConnection.makeConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, loginName);
            preStm.setString(2, password);
            rs = preStm.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("ID");
                String userName = rs.getString("UserName");
                int isActive = rs.getInt("IsActive");
                user = new UserDTO(id, loginName, isActive);
                user.setUsername(userName);
                setStatus(1, id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return user;
    }

    public boolean setStatus(int status, int id) throws Exception {
        try {
            String sql = "Update Users "
                    + "set IsActive = ? "
                    + "where ID = ?";
            conn = MyConnection.makeConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, status);
            preStm.setInt(2, id);
            return preStm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return false;
    }

    public int getStatus(int id) throws Exception {
        int result = 0;
        try {
            String sql = "Select IsActive from Users where ID = ?";
            conn = MyConnection.makeConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, id);
            rs = preStm.executeQuery();
            if (rs.next()) {
                result = rs.getInt("IsActive");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return result;
    }

}
