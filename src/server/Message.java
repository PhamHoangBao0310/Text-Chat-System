/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import dtos.UserDTO;
import java.io.Serializable;

/**
 *
 * @author DELL
 */
public class Message implements Serializable {

    String content;
    UserDTO receiver;
    UserDTO sender;

    public Message(String content, UserDTO receiver, UserDTO sender) {
        this.content = content;
        this.receiver = receiver;
        this.sender = sender;
    }

    public UserDTO getSender() {
        return sender;
    }

    public void setSender(UserDTO sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserDTO getReceiver() {
        return receiver;
    }

    public void setReceiver(UserDTO receiver) {
        this.receiver = receiver;
    }

}
