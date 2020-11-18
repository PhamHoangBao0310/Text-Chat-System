/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import dtos.UserDTO;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author DELL
 */
public class ClientHandler implements Runnable {

    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    Socket s;
    UserDTO user;

    public UserDTO getUser() {
        return user;
    }

    public ClientHandler(ObjectOutputStream oos, ObjectInputStream ois, Socket s) {
        this.oos = oos;
        this.ois = ois;
        this.s = s;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public void run() {
        Message received;
        try {
            while(true){
                received = (Message) ois.readObject();
                System.out.println(received.getSender().getLoginName() + " typed  : " + received.getContent() + " to " + received.getReceiver().getLoginName());
                for(ClientHandler client : ChatServer.users){
                    if(client.getUser().getId() == received.getReceiver().getId()){
                        client.oos.writeObject(received);
                        client.oos.flush();
                    }
                }
            }
        } catch (Exception e) {
        } finally {
            try {
                oos.close();
                ois.close();
                s.close();
                ChatServer.users.remove(this);
                System.out.println(ChatServer.users.toString());
            } catch (Exception e) {
            }
            
        }

    }
}
