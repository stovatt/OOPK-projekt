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
    private JTextArea msgBox;
    private JButton NewChatBtn;
    private JButton PersonalSettingsBtn;
    private JButton SendMsgBtn;
    private JButton SendAndEncryptBtn;
    private JButton SendFileBtn;
    private JButton connectToChatBtn;
    private JButton kickButton;
    private JButton closeBtn;
    private JFrame TheWindow;
    
    public View(){
        StartWindow s = new StartWindow();
        s.addObserver(this);
    }
    
    private void draw(){
        
        TheWindow = new JFrame();
        TheWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Chat history and chat control buttons
        
        historyPanels = new JTabbedPane();
        historyPanels.setPreferredSize(new Dimension(600, 500));
        historyPanels.addChangeListener(changeListener);
        
        chatList = new ArrayList<>();
        chatHistorys = new ArrayList<>();

        NewChatBtn = new JButton("Start new chat");
        connectToChatBtn = new JButton("Connect to server");
        PersonalSettingsBtn = new JButton("Open settings");
        kickButton = new JButton("Kick from chat");
        closeBtn = new JButton("Close");
        
        closeBtn.setBackground(Color.RED);
        
        JPanel controlButtonPanel = new JPanel();
        controlButtonPanel.setLayout(new GridLayout(0,1));
        
        controlButtonPanel.add(NewChatBtn);
        controlButtonPanel.add(connectToChatBtn);
        controlButtonPanel.add(PersonalSettingsBtn);
        controlButtonPanel.add(kickButton);
        controlButtonPanel.add(closeBtn);
        
        JSplitPane controlPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                           historyPanels, controlButtonPanel);
        
        //Buttons and textfields for sending messages
        
        msgBox = new JTextArea("", 8, 40);
        msgBox.setEditable(true);
        JPanel MsgPanel = new JPanel();
        MsgPanel.add(msgBox);
        JTabbedPane msgTabbedPanel = new JTabbedPane();
        msgTabbedPanel.addTab("Write here", null, MsgPanel,
                                    "This is a tab, but it looks ok.");
        
        SendMsgBtn = new JButton("Send Message");
        SendMsgBtn.setBackground(Color.GREEN);
        SendAndEncryptBtn = new JButton("Send+Encrypt Message");
        SendFileBtn = new JButton("Send File");
        JPanel MsgButtonPanel = new JPanel();
        
        MsgButtonPanel.setLayout(new GridLayout(0,1));
        MsgButtonPanel.add(SendMsgBtn);
        MsgButtonPanel.add(SendAndEncryptBtn);
        MsgButtonPanel.add(SendFileBtn);
        
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
        closeBtn.addActionListener(this);
        
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
        User me = theModel.getMe();
        SettingsWindow setWindow = new SettingsWindow(me.getName(), me.getColor());
        setWindow.addObserver(this);
    }
    
    public void sendMsg(int[] ind){
        String msgText = msgBox.getText();
        
        Color color = theModel.getMe().getColor();
        String name = theModel.getMe().getName();    
            
        Message message = new Message(name, color, msgText, ind, false, false);
        activeChat.sendMsg(message, theModel.getMe());
//        System.out.println(message);
        
        msgBox.setText("");
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
    
    public void leave(){
        System.out.println("leave början");
        if(activeChat != null){
            System.out.println("leave aC != null");
            String n = theModel.getMe().getName();
            Color c = theModel.getMe().getColor();
            int[] i = new int[] {};
            Message leaveM = new Message(n, c, "Hejdå", i, true, false);
            activeChat.sendMsg(leaveM, theModel.getMe());

            Message tempM = new Message("Server", Color.BLACK,
                    "You have left this chat", i, false, false);
            updateChatHistory(tempM, activeChat);
            
            int chatIndex = chatList.indexOf(activeChat);
            chatHistorys.remove(chatIndex);
            chatList.remove(chatIndex);
            historyPanels.remove(chatIndex);
            
        }
        
        if(chatList.size() > 0){
            activeChat = chatList.get(0);
        }
        else{
            activeChat = null;
        }
    }
    
    public int close(){
        System.out.println("close, innan leave");
        leave();
        System.out.println("close, efter leave");
        TheWindow.dispose();
        System.exit(0);
        
        return 0;
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
                Message message = (Message)arg;
                if(message.isDisconnect()){
                    message.setText(message.getName() + " har loggat ut");
                    message.setName("Server");
                }
                else if(false){
                    
                }
                updateChatHistory(message, (ChatModel)o);
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
            System.out.println("hej");
            Object[] newArg = (Object[])arg;
            int port = Integer.parseInt((String)newArg[1]);
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
            String msgText = msgBox.getText();
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
                leave();
            }

        }
        else if(e.getSource() == connectToChatBtn){
            connectToServer();
        }
        else if(e.getSource() == closeBtn){
            close();
        }
    }
}