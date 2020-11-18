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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

/**
 *
 * @author DELL
 */
public class ChatServer {

    static Vector<ClientHandler> users = new Vector<>();

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        int port = 1333;
        ServerSocket server = null;

        server = new ServerSocket(port);
        System.out.println("server is running now.......");

        Socket s;
        try {
            while (true) {
                s = server.accept();
                System.out.println("New client is arriving " + s.toString());

                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                UserDTO user = (UserDTO) ois.readObject();
                System.out.println(user.toString2());

                ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());

                ClientHandler client = new ClientHandler(oos, ois, s);

                client.setUser(user);

                Thread thread = new Thread(client);

                users.add(client);

                System.out.println(users.toString());

                thread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.close();
        }

    }
}
