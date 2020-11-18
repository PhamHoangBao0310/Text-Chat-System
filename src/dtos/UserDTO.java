/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;

/**
 *
 * @author DELL
 */
public class UserDTO implements Serializable{
    private int id;
    private String username;
    private String loginName;
    private String password;
    private int status;

    public UserDTO() {
    }

    public UserDTO(String username, String loginName, int status) {
        this.username = username;
        this.loginName = loginName;
        this.status = status;
    }

    public UserDTO(int id, String loginName, int status) {
        this.id = id;
        this.loginName = loginName;
        this.status = status;
    }

    public UserDTO(int id, String username, String loginName, String password, int status) {
        this.id = id;
        this.username = username;
        this.loginName = loginName;
        this.password = password;
        this.status = status;
    }

    public UserDTO(int id, String username, String loginName) {
        this.id = id;
        this.username = username;
        this.loginName = loginName;
    }

    public UserDTO(String username, String loginName, String password, int status) {
        this.username = username;
        this.loginName = loginName;
        this.password = password;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public UserDTO(String username, String loginName) {
        this.username = username;
        this.loginName = loginName;
    }

    public UserDTO(String username, String loginName, String password) {
        this.username = username;
        this.loginName = loginName;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

  
    public String toString2() {
        return "UserDTO{" + "id=" + id + ", username=" + username + ", loginName=" + loginName + ", password=" + password + ", status=" + status + '}';
    }
    
    @Override
    public String toString() {
        if (this.getStatus() == 1){
            return loginName + "(Online)";
        }
        return loginName;
    }
    
    
}
