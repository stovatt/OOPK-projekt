/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projektuppgift;

import javax.swing.*;
import javax.swing.text.*;
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
import javax.swing.text.StyleConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author lukasgu
 */
public class View extends Observable implements Observer, ActionListener{
    
    private Model theModel;
    private ChatModel activeChat;
    private ArrayList<ChatModel> chatList;
    private JTabbedPane historyPanels;
    private ArrayList<JTextPane> chatHistorys;
    private JTextArea MsgBox;
    private JButton NewChatBtn;
    private JButton PersonalSettingsBtn;
    private JButton SendMsgBtn;
    private JButton SendAndEncryptBtn;
    private JButton SendFileBtn;
    private JButton connectToChatBtn;
    private JButton kickButton;
    private JFrame TheWindow;
    
    public View(){
        new StartWindow();
    }
    
    private void draw(){
        
        TheWindow = new JFrame();
        TheWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Chat history and chat control buttons
        
        historyPanels = new JTabbedPane();
        historyPanels.setPreferredSize(new Dimension(400, 200));
        historyPanels.addChangeListener(changeListener);
        
        chatList = new ArrayList<>();
        chatHistorys = new ArrayList<>();

        NewChatBtn = new JButton("Start new chat");
        connectToChatBtn = new JButton("Connect to server");
        PersonalSettingsBtn = new JButton("Open settings");
        kickButton = new JButton("Kick from chat");
        SendFileBtn = new JButton("Send File");
        
        JPanel controlButtonPanel = new JPanel();
        controlButtonPanel.setLayout(new GridLayout(0,1));
        
        controlButtonPanel.add(NewChatBtn);
        controlButtonPanel.add(PersonalSettingsBtn);
        controlButtonPanel.add(kickButton);
        controlButtonPanel.add(SendFileBtn);
        controlButtonPanel.add(connectToChatBtn);
        
        JSplitPane controlPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                           historyPanels, controlButtonPanel);
        
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
        
        SendMsgBtn.addActionListener(this);
        SendAndEncryptBtn.addActionListener(this);
        SendFileBtn.addActionListener(this);
        NewChatBtn.addActionListener(this);
        PersonalSettingsBtn.addActionListener(this);
        kickButton.addActionListener(this);
        connectToChatBtn.addActionListener(this);
        
        // Put together, pack and set visible
        
        JSplitPane theSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                           controlPanel, msgSplitPanel);
        TheWindow.getContentPane().add(theSplitPane);
        TheWindow.pack();
        TheWindow.setVisible(true);
    }
    
    public void changeActiveChat(ChatModel inModel){
//        System.out.println("Active chatt satt");
        activeChat = inModel;
        activeChat.addObserver(this);
        
        if(activeChat.isHost()){
            kickButton.setText("Kick from Chat");
        }
        else{
            kickButton.setText("Leave Chat");
        }
    }
    
    public void startNewChat(){
        
    }
    
    public void connectToServer(){
        ConnectToServerWindow serverWindow = new ConnectToServerWindow();
        serverWindow.addObserver(this);
        
    }
    
    public void sendFile(){
        
    }
    
    public void openSettings(){
        SettingsWindow setWindow = new SettingsWindow();
        setWindow.addObserver(this);
    }
    
    public void sendMsg(int[] ind){
        String msgText = MsgBox.getText();
        
        Color color = activeChat.getMe().getColor();
        String name = activeChat.getMe().getName();    
            
        Message message = new Message(name, color, msgText, ind, false, false);
        activeChat.sendMsg(message, theModel.getMe());
        System.out.println(message);
    }
    
    public void askForEncryptIndex(String msgText){
        EncryptWindow encWindow = new EncryptWindow(msgText);
        encWindow.addObserver(this);
    }
    
    public void ReceiveFileRequest(){
        
    }
    
    public void showConnectionRequestWindow(Object[] list){
        System.out.println("Fick connection request");
        
        int noChats = chatList.size();
        ConnectionRequestWindow CRWindow = new ConnectionRequestWindow(list, noChats);
        CRWindow.addObserver(this);
    }
    
    public void changeTab(int index){
        
        ChatModel newActiveC = chatList.get(index);
        changeActiveChat(newActiveC);    
    }
    
    public void kick(){
        
    }
    
    public void updateChatHistory(Message newMessage, ChatModel chat){
    int index = chatList.indexOf(chat);
    JTextPane activeChatHistory = chatHistorys.get(index);
    Color color = newMessage.getColor();
    String text = newMessage.getText();
    String name = newMessage.getName();
        
    StyledDocument doc = activeChatHistory.getStyledDocument();
    Style style = doc.addStyle("textStyle", null);
    StyleConstants.setForeground(style, color);
        
    try
    {
        doc.insertString(doc.getLength(), "\n" + name + ": " + text ,doc.getStyle("textStyle"));
    }
    catch(Exception e) { System.out.println(e); }
    }
    
    ChangeListener changeListener = new ChangeListener() {
        public void stateChanged(ChangeEvent changeEvent) {
            JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
            int index = sourceTabbedPane.getSelectedIndex();
            changeTab(index);
        }
    };
    
    public void update(Observable o, Object arg){
        if(o instanceof ChatModel){
            if(arg instanceof Message){
                updateChatHistory((Message)arg, (ChatModel)o);
            }
        }
        if(o == theModel){
            if(arg instanceof ChatModel){
//                System.out.println(((ChatModel) arg).getMe().getName());
                chatList.add((ChatModel)arg);
                JTextPane chatHistory = new JTextPane();
                //set size 20, 30
                chatHistory.setEditable(false);
                chatHistorys.add(chatHistory);
                JScrollPane chatPanel = new JScrollPane(chatHistory);
                int chatNo = chatList.size();
                historyPanels.addTab("Chat " + chatNo, null, chatPanel,
                  "Does nothing");
//                changeActiveChat((ChatModel)arg);
            }
            else{
                Object[] newArg = (Object[])arg;
                showConnectionRequestWindow(newArg);
            }
        }
        if(o instanceof EncryptWindow){
            int[] ind = (int[])arg;
            sendMsg(ind);
//            System.out.println("hejehj");
            System.out.println(ind);
        }
        if(o instanceof StartWindow){
            Object[] newArg = (Object[])arg;
            int port = (int)newArg[1];
            User me = (User)newArg[0];
            theModel = new Model(port);
            theModel.addObserver(this);
            this.addObserver(theModel);
            
            this.draw();
            theModel.setMe(me);  
        }
        if(o instanceof SettingsWindow){
            User me = (User)arg;
            theModel.setMe(me);  
        }
        if(o instanceof ConnectToServerWindow){
            String[] newArg = (String[])arg;
            String IP = newArg[0];
            int port = Integer.parseInt(newArg[1]);
            String messageText = newArg[2];
            Message message = new Message(theModel.getMe().getName(),
                    theModel.getMe().getColor(), messageText, new int[] {}, false, true);
            
            theModel.connectToOtherChat(IP, port, message);
        }
        if(o instanceof ConnectionRequestWindow){
            this.setChanged();
            notifyObservers(arg);
        }
    }
    
    public void actionPerformed(ActionEvent e){
//        System.out.println("Fick ae");
        if(e.getSource() == SendMsgBtn){
            int[] ind = {};
            sendMsg(ind);
        }
        else if(e.getSource() == SendAndEncryptBtn){
            String msgText = MsgBox.getText();
            askForEncryptIndex(msgText);
        }
        else if(e.getSource() == SendFileBtn){
            
        }
        else if(e.getSource() == NewChatBtn){
            
        }
        else if(e.getSource() == PersonalSettingsBtn){
            openSettings();
            
        }
        else if(e.getSource() == kickButton){
            if(activeChat == null){
             // do nothing   
            }
            else if(activeChat.isHost()){
                kick();
            }
            else{
                
            }

        }
        else if(e.getSource() == connectToChatBtn){
            connectToServer();
        }
    }
}