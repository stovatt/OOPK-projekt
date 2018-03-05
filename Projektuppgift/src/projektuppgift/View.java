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

/**
 *
 * @author lukasgu
 */
public class View extends Observable implements Observer, ActionListener{
    
    private Model theModel;
    private ChatModel ActiveChat;
    private ArrayList<ChatModel> chatList;
    private JTabbedPane historyPanels;
    private ArrayList<JTextPane> chatHistorys;
    private JTextArea MsgBox;
    private JButton NewChatBtn;
    private JButton PersonalSettingsBtn;
    private JButton SendMsgBtn;
    private JButton SendAndEncryptBtn;
    private JButton SendFileBtn;
    // private JButton ChangeOpenkeys;
    private JButton kickButton;
    private User me;
    
//    private Dimension PreferredSize;
    private JFrame TheWindow;
    
    public View(Model inModel){
        
        theModel = inModel;
        theModel.addObserver(this);
        me = new User(null);
//        PreferredSize = new Dimension(1000, 800);
        this.draw();
    }
    
    public void draw(){
        
        TheWindow = new JFrame();
        TheWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Chat history and chat control buttons
        
        historyPanels = new JTabbedPane();
        historyPanels.setPreferredSize(new Dimension(400, 200));
        
        chatList = new ArrayList<>();
        chatHistorys = new ArrayList<>();
        
//        chatHistory = new JTextArea("chatHistory", 20, 30);
//        chatHistory.setEditable(false);
//        JScrollPane chatPanel = new JScrollPane(chatHistory);
//        JScrollPane chatPanel2 = new JScrollPane();
//        historyPanels.addTab("Chat 1", null, chatPanel,
//                  "Does nothing");
//        historyPanels.addTab("Chat 2", null, chatPanel2,
//                  "Does nothing");

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
        ActiveChat.addObserver(this);
    }
    
    public void startNewChat(){
        
    }
    
    public void connectToServer(){
        
    }
    
    public void sendFile(){
        
    }
    
    public void openSettings(){
        SettingsWindow setWindow = new SettingsWindow();
        setWindow.addObserver(this);
    }
    
    public void changeName(){
        
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
    
    public void askForEncryptIndex(String msgText){
        EncryptWindow encWindow = new EncryptWindow(msgText);
        encWindow.addObserver(this);
    }
    
    public void ReceiveFileRequest(){
        
    }
    
    public int showConnectionRequestWindow(Message inMessage){
        System.out.println("Fick connection request");
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
    
    public void update(Observable o, Object arg){
//        System.out.println("medelanded kom fram till View");
        if(o == ActiveChat){
            if(arg instanceof Message){
                updateChatHistory((Message)arg, (ChatModel)o);
            }
        }
        if(o == theModel){
            if(arg instanceof ChatModel){
                System.out.println(((ChatModel) arg).getMe().getName());
                chatList.add((ChatModel)arg);
                JTextPane chatHistory = new JTextPane();
                //set size 20, 30
                chatHistory.setEditable(false);
                chatHistorys.add(chatHistory);
                JScrollPane chatPanel = new JScrollPane(chatHistory);
                int chatNo = chatList.size();
                historyPanels.addTab("Chat " + chatNo, null, chatPanel,
                  "Does nothing");
                changeActiveChat((ChatModel)arg);
            }
        }
        if(o instanceof EncryptWindow){
            int[] ind = (int[])arg;
            sendMsg(ind);
//            System.out.println("hejehj");
            System.out.println(ind);
        }
        if(o instanceof SettingsWindow){
            me = (User)arg;
        }
    }
    
    public void notifyObservers(Object arg){
        
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
            
        }
    }
}
