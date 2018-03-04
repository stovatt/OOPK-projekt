/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projektuppgift;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.event.*;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lukasgu
 */
public class View extends Observable implements Observer, ActionListener{
    
    private Model TheModel;
    private ChatModel ActiveChat;
    private JTextArea ChatHistory;
    private JTextArea MsgBox;
    private JButton NewChatBtn;
    private JButton PersonalSettingsBtn;
    private JButton SendMsgBtn;
    private JButton SendAndEncryptBtn;
    private JButton SendFileBtn;
    // private JButton ChangeOpenkeys;
    private JButton kickButton;
    
    private Dimension PreferredSize;
    private JFrame TheWindow;
    
    public View(){
        
        //TheModel = inModel;
        PreferredSize = new Dimension(1000, 800);
        this.draw();
    }
    
    public void draw(){
        
        TheWindow = new JFrame();
        TheWindow.setLayout(new GridLayout(2,0));
        //TheWindow.setPreferredSize(PreferredSize);
        TheWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //The controlPanel makes sure everything is not stacked on top of eachother on the screen
        JPanel controlPanel = new JPanel();
        JPanel MsgPanel = new JPanel();
//        controlPanel.add(MsgPanel);
        
        // Add textAreas
        ChatHistory = new JTextArea("chatHistory", 20, 20);
        ChatHistory.setEditable(false);
        ChatHistory.setBackground(Color.RED);
        controlPanel.add(ChatHistory);
        
        MsgBox = new JTextArea("write here", 10, 20);
        MsgBox.setEditable(true);
        MsgBox.setBackground(Color.RED);
        MsgPanel.add(MsgBox);
        TheWindow.getContentPane().add(controlPanel, BorderLayout.NORTH);
        TheWindow.getContentPane().add(MsgPanel, BorderLayout.NORTH);
        
        // Add buttons with listeners
        
        SendMsgBtn = new JButton("Send Message");
        SendAndEncryptBtn = new JButton("Send+Encrypt Message");
        SendFileBtn = new JButton("Send File");
        
        MsgPanel.add(SendMsgBtn);
        MsgPanel.add(SendAndEncryptBtn);
        MsgPanel.add(SendFileBtn);
        
        NewChatBtn = new JButton("Start new chat");
        PersonalSettingsBtn = new JButton("Open settings");
        kickButton = new JButton("Kick from chat");
        
        controlPanel.add(NewChatBtn);
        controlPanel.add(PersonalSettingsBtn);
        controlPanel.add(kickButton);
        
        SendMsgBtn.addActionListener(this) ;
        SendAndEncryptBtn.addActionListener(this) ;
        SendFileBtn.addActionListener(this);
        NewChatBtn.addActionListener(this) ;
        PersonalSettingsBtn.addActionListener(this) ;
        kickButton.addActionListener(this) ;
        
        
        TheWindow.pack();
        TheWindow.setVisible(true);
    }
    
    public void changeActiveChat(ChatModel inModel){
        
    }
    
    public void startNewChat(){
        
    }
    
    public void connectToServer(){
        
    }
    
    public void sendFile(){
        
    }
    
    public void openSettings(){
        
    }
    
    public void changeName(){
        
    }
    
    public void changeColor(){
        
    }
    
    public void sendMsg(int[] ind){
        String msgText = MsgBox.getText();
        
        Color color = ActiveChat.getMe().getColor();
        String name = ActiveChat.getMe().getName();    
            
        Message message = new Message(name, color, msgText, ind, false);
        ActiveChat.sendMsg(message);
        System.out.println(message);
    }
    
    public int[] getEncryptIndex(String msgText){
        int[] a = {};
        
        JFrame encryptFrame = new JFrame();
        encryptFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        encryptFrame.setPreferredSize(new Dimension(20, 30));
        encryptFrame.setLayout(new GridLayout(2,0));
        
        JPanel writePanel = new JPanel();
        JTextArea encryptBox = new JTextArea(msgText, 10, 20);
        writePanel.add(encryptBox);
        
        JPanel btnPanel = new JPanel();
        JButton selectBtn = new JButton("Select");
        JButton finishBtn = new JButton("Finish");
        btnPanel.add(selectBtn);
        btnPanel.add(finishBtn);
        
        encryptFrame.getContentPane().add(writePanel, BorderLayout.NORTH);
        encryptFrame.getContentPane().add(btnPanel, BorderLayout.SOUTH);
        encryptFrame.setVisible(true);
        
        return a; 
    }
    
    public void ReceiveFileRequest(){
        
    }
    
    public int showConnectionRequestWindow(){
        
        return 0;
    }
    
    public void changeTab(){
        
    }
    
    public void kick(){
        
    }
    
    public void update(Observable o, Object arg){
        if(o == ActiveChat){
            if(arg instanceof Message){
                updateChatHistory((Message)arg);
            }
        }
    }
    
    public void notifyObservers(Object arg){
        
    }
    
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == SendMsgBtn){
            int[] ind = {};
            sendMsg(ind);
        }
        else if(e.getSource() == SendAndEncryptBtn){
            String msgText = MsgBox.getText();
            int[] ind = getEncryptIndex(msgText);
            sendMsg(ind);
        }
    }
    
//    public void messageReceive(Message inMessage, ChatModel chat){
//        
//    }
    
    public void updateChatHistory(Message newMessage){
        
        
    }
    
    
    
    
    
    
    
    
    
    
}
