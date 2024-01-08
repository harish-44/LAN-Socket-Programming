
package com.mycompany.lansocket;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;


public class serverChatForm extends JFrame implements ActionListener {
    static ServerSocket server;
    static Socket conn;
    JPanel panel;
    JTextField NewMsg;
    JTextArea ChatHistory;
    JButton Send;
    DataInputStream dis;
    DataOutputStream dos;
    public serverChatForm() throws UnknownHostException, IOException {
        panel = new JPanel();
        NewMsg = new JTextField();
        ChatHistory = new JTextArea();
        Send = new JButton("Send");
        this.setSize(570, 570);
        this.setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        panel.setLayout(null);
        this.add(panel);
        InetAddress myIp = InetAddress.getLocalHost();
        JLabel label7 = new JLabel(myIp.getHostAddress());
        panel.add(label7);
        label7.setBounds(200, 50, 240, 25);
        JLabel label8=new JLabel("2000");
        label8.setBounds(200,80,100,25);
        panel.add(label8);
        JLabel label1= new JLabel(" IP Address :");
        label1.setBounds(70,50,100,25);
        panel.add(label1);
        JLabel label2 = new JLabel("Port:");
        label2.setBounds(70,80,100,25);
        panel.add(label2);
        ChatHistory.setBounds(20, 200, 450, 200);
        panel.add(ChatHistory);
        NewMsg.setBounds(20, 450, 400, 30);
        panel.add(NewMsg);
        Send.setBounds(430, 450, 95, 30);
        panel.add(Send);
        this.setTitle("Server");
        Send.addActionListener(this);
        server = new ServerSocket(2000, 1, InetAddress.getLocalHost());
        ChatHistory.setText("Waiting for Client");
        conn = server.accept();
        ChatHistory.setText(ChatHistory.getText() + '\n' + "Client Found");
        while (true) {
            try {
                DataInputStream dis = new DataInputStream(conn.getInputStream());
                String string = dis.readUTF();
                ChatHistory.setText(ChatHistory.getText() + '\n'+'\n' + "Client:  "+ string);
            } catch (Exception e1) {
                ChatHistory.setText(ChatHistory.getText() + '\n'+'\n'+ "Message sending fail:Network Error");
                try {
                    Thread.sleep(3000);
                    System.exit(0);
                } catch (InterruptedException e) {

                }
            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        ChatHistory.setText(ChatHistory.getText() + '\n'+'\n' + "ME :  "+ NewMsg.getText());
        try {
            DataOutputStream dos = new DataOutputStream(
                    conn.getOutputStream());
            dos.writeUTF(NewMsg.getText());
        } catch (Exception e1) {
            try {
                Thread.sleep(3000);
                System.exit(0);
            } catch (InterruptedException e2) {

            }
        }
        NewMsg.setText("");
    }
    public static void main(String[] args) throws UnknownHostException,IOException {
        serverChatForm c = new serverChatForm();
    }
}