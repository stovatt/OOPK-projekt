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
        User me = this.getSettings();
        PreferredSize = new Dimension(1000, 800);
        this.draw();
    }
    
    public void draw(){
        
        TheWindow = new JFrame();
        TheWindow.setLayout(new GridLayout(2,0));
//        TheWindow.setPreferredSize(PreferredSize);
        TheWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //The controlPanel makes sure everything is not stacked on top of eachother on the screen
        JPanel controlPanel = new JPanel();
        JPanel MsgPanel = new JPanel();
//        controlPanel.add(MsgPanel);
        
        // Add textAreas
        ChatHistory = new JTextArea("chatHistory", 20, 30);
        ChatHistory.setEditable(false);
//        ChatHistory.setBackground(Color.RED);
        controlPanel.add(ChatHistory);
        
        MsgBox = new JTextArea("write here...", 8, 25);
        MsgBox.setEditable(true);
//        MsgBox.setBackground(Color.RED);
        MsgPanel.add(MsgBox);
        TheWindow.getContentPane().add(controlPanel, BorderLayout.NORTH);
        TheWindow.getContentPane().add(MsgPanel, BorderLayout.SOUTH);
        
        // Add buttons with listeners
        
        SendMsgBtn = new JButton("Send Message");
        SendAndEncryptBtn = new JButton("Send+Encrypt Message");
        SendFileBtn = new JButton("Send File");
        JPanel MsgButtonPanel = new JPanel();
        
        MsgButtonPanel.setLayout(new GridLayout(0,1));
        MsgButtonPanel.add(SendMsgBtn);
        MsgButtonPanel.add(SendAndEncryptBtn);
        MsgButtonPanel.add(SendFileBtn);
        MsgPanel.add(MsgButtonPanel);
        
        NewChatBtn = new JButton("Start new chat");
        PersonalSettingsBtn = new JButton("Open settings");
        kickButton = new JButton("Kick from chat");
        JPanel controlButtonPanel = new JPanel();
        
        controlButtonPanel.setLayout(new GridLayout(0,1));
        
        controlButtonPanel.add(NewChatBtn);
        controlButtonPanel.add(PersonalSettingsBtn);
        controlButtonPanel.add(kickButton);
        controlPanel.add(controlButtonPanel);
        
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
        ActiveChat = inModel;
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
    
    public User getSettings(){
        
        
        
        
        User me = new User(null);
        return(me);
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
        EncryptWindow encWindow = new EncryptWindow(msgText);

        int[] a = encWindow.getIndices();
        
        return a; 
    }
    
    public void ReceiveFileRequest(){
        
    }
    
    public int showConnectionRequestWindow(Message inMessage){
        
        int ans = 0;
        
        JFrame requestWindow =  new JFrame();
        JTextPane msgBox = new JTextPane();
        msgBox.setText(inMessage.getText());
        msgBox.setEditable(false);
        
        JPanel buttonPanel = new JPanel();
        JButton allowBtn = new JButton("Allow");
        JButton denyBtn = new JButton("Deny");
        buttonPanel.add(allowBtn);
        buttonPanel.add(denyBtn);
        
        //Add components
        requestWindow.getContentPane().add(msgBox);
        requestWindow.add(buttonPanel);
        
        requestWindow.pack();
        
        return ans;
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
//            sendMsg(ind);
        }
    }
    
//    public void messageReceive(Message inMessage, ChatModel chat){
//        
//    }
    
    public void updateChatHistory(Message newMessage){
        
        
    }   
    
}
