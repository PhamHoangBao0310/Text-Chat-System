/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import dtos.UserDTO;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JTabbedPane;
import models.MessageDAO;
import server.Message;

/**
 *
 * @author DELL
 */
public class OutputText extends Thread {

    Socket s;
    JTabbedPane tabb;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    UserDTO receiver; // you

    public OutputText() {

    }

    public OutputText(Socket s, JTabbedPane tabb, ObjectInputStream ois, UserDTO receiver, ObjectOutputStream oos) {
        this.s = s;
        this.tabb = tabb;
        this.ois = ois;
        this.receiver = receiver;
        this.oos = oos;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (s != null || !s.isClosed()) {
                    MessageDAO messDao = new MessageDAO();
                    Message receivedMess = (Message) ois.readObject();
                    int panelNum = tabb.getTabCount();// get number of current panel
                    boolean existed = false;
                    for (int i = 0; i < panelNum; i++) {
//                        Get panel number i
                        ChatPanel chatPanel = (ChatPanel) tabb.getComponent(i);
                        if (chatPanel.getReceiver().getId() == receivedMess.getSender().getId()) { //Check if receiver in panel and sender in message are equal
                            chatPanel.getTxtMess().append("\n" + receivedMess.getSender().getLoginName() + " : " + receivedMess.getContent());
                            existed = true;
                            try {
                                messDao.saveMessage(receiver.getId(), receivedMess.getSender().getId(), chatPanel.getTxtMess().getText());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                    }
//                   If this panel is closed
                    if (existed == false) {
//                        New chat panel
                        ChatPanel newChatPanel = new ChatPanel(s, receiver, receivedMess.getSender(), oos, ois);
                        tabb.add(receivedMess.getSender().getLoginName(), newChatPanel);
                        try {
                            String currmess = messDao.getMessage(receiver.getId(), receivedMess.getSender().getId());
                            newChatPanel.getTxtMess().append(currmess);
                            newChatPanel.getTxtMess().append("\n" + receivedMess.getSender().getLoginName() + " : " + receivedMess.getContent());
                            messDao.saveMessage(receiver.getId(), receivedMess.getSender().getId(), newChatPanel.getTxtMess().getText());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
//                    this.interrupt();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

//ChatPanel newChatPanel = new ChatPanel(s, receiver, receivedMess.getSender(), oos, ois);
//String currmess = messDao.getMessage(receiver.getId(), receivedMess.getSender().getId());
//messDao.saveMessage(receiver.getId(), receivedMess.getSender().getId(), newChatPanel.getTxtMess().getText());