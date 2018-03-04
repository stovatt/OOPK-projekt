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
    
    private Model theModel;
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
    
    public View(Model inModel){
        
        theModel = inModel;
        theModel.addObserver(this);
        User me = this.getSettings();
        PreferredSize = new Dimension(1000, 800);
        this.draw();
    }
    
    public void draw(){
        
        TheWindow = new JFrame();
        TheWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Chat history and chat control buttons
        
        JTabbedPane historyPanels = new JTabbedPane();
        
        ChatHistory = new JTextArea("chatHistory", 20, 30);
        ChatHistory.setEditable(false);
        JScrollPane chatPanel = new JScrollPane(ChatHistory);
        JScrollPane chatPanel2 = new JScrollPane();
        historyPanels.addTab("Chat 1", null, chatPanel,
                  "Does nothing");
        historyPanels.addTab("Chat 2", null, chatPanel2,
                  "Does nothing");

        NewChatBtn = new JButton("Start new chat");
        PersonalSettingsBtn = new JButton("Open settings");
        kickButton = new JButton("Kick from chat");
        SendFileBtn = new JButton("Send File");
        
        JPanel controlButtonPanel = new JPanel();
        controlButtonPanel.setLayout(new GridLayout(0,1));
        
        controlButtonPanel.add(NewChatBtn);
        controlButtonPanel.add(PersonalSettingsBtn);
        controlButtonPanel.add(kickButton);
        controlButtonPanel.add(SendFileBtn);
        
        JSplitPane controlPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                           historyPanels, controlButtonPanel);
        
//        controlPanel.add(controlButtonPanel);
        
        //Buttons and textfields for sending messages
        
        MsgBox = new JTextArea("", 8, 35);
        MsgBox.setEditable(true);
        JPanel MsgPanel = new JPanel();
        MsgPanel.add(MsgBox);
        JTabbedPane msgTabbedPanel = new JTabbedPane();
        msgTabbedPanel.addTab("Write here", null, MsgPanel,
                                    "This is a tab, but it looks ok.");
        
        SendMsgBtn = new JButton("Send Message");
        SendAndEncryptBtn = new JButton("Send+Encrypt Message");
        JPanel MsgButtonPanel = new JPanel();
        
        MsgButtonPanel.setLayout(new GridLayout(0,1));
        MsgButtonPanel.add(SendMsgBtn);
        MsgButtonPanel.add(SendAndEncryptBtn);
        
        JSplitPane msgSplitPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                           msgTabbedPanel, MsgButtonPanel);
        
        // Add listeners to buttons
        
        SendMsgBtn.addActionListener(this) ;
        SendAndEncryptBtn.addActionListener(this) ;
        SendFileBtn.addActionListener(this);
        NewChatBtn.addActionListener(this) ;
        PersonalSettingsBtn.addActionListener(this) ;
        kickButton.addActionListener(this) ;
        
        // Put together, pack and set visible
        
        JSplitPane theSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                           controlPanel, msgSplitPanel);
        TheWindow.getContentPane().add(theSplitPane);
        TheWindow.pack();
        TheWindow.setVisible(true);
    }
    
    public void changeActiveChat(ChatModel inModel){
        System.out.println("Active chatt satt");
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
            
        Message message = new Message(name, color, msgText, ind, false, false);
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
        if(o == theModel){
            if(arg instanceof ChatModel){
                changeActiveChat((ChatModel)arg);
            }
        }
    }
    
    public void notifyObservers(Object arg){
        
    }
    
    public void actionPerformed(ActionEvent e){
        System.out.println("Fick ae");
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
