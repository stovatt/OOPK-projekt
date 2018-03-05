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
import javax.swing.text.JTextComponent;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;              
import java.awt.event.*;        
import java.net.URL;
import java.io.IOException;

/**
 *
 * @author sartov
 */
public class ConnectionRequestWindow extends Observable implements ActionListener{
    
    private JFrame requestWindow;
    private JComboBox<String> chatDrop;
    private JButton allowBtn;
    private JButton denyBtn;
    private int noChats;
    private Object[] list;
    
    public ConnectionRequestWindow(Object[] inList, int inNoChats){
        
        noChats = inNoChats;
        list = inList; 
        Message inMessage = (Message)list[0];
        
        requestWindow =  new JFrame();
        String name = inMessage.getName();
        String text = inMessage.getText();
        JLabel msgLabel = new JLabel(name + " wants to chat with you");
        JTextArea msgBox = new JTextArea(name +  ": \""+ text + "\"", 2, 20);
        JPanel msgBoxPanel = new JPanel();
        msgBoxPanel.add(msgBox);
        msgBox.setEditable(false);
        JPanel msgPanel = new JPanel();
        msgPanel.setLayout(new GridLayout(0,1));
        msgPanel.add(msgLabel);
        msgPanel.add(msgBoxPanel);

        
        JPanel buttonPanel = new JPanel();
        JButton allowBtn = new JButton("Allow");
        JButton denyBtn = new JButton("Deny");
        allowBtn.addActionListener(this);
        denyBtn.addActionListener(this);
        buttonPanel.setLayout(new GridLayout(1,0));
        buttonPanel.add(allowBtn);
        buttonPanel.add(denyBtn);
        
        JPanel dropPanel = new JPanel();
        JLabel dropLabel = new JLabel("Select chat: ");
        
        String[] users = new String[noChats + 1];
        users[0] = "New chat";
        for(int i = 1; i <= noChats; i++){
            users[i] = "Chat " + i;
        }
        chatDrop = new JComboBox<>(users);
        
        dropPanel.add(dropLabel);
        dropPanel.add(chatDrop);
        
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(0,1));
        controlPanel.add(dropPanel);
        controlPanel.add(buttonPanel);
        
        JSplitPane theSplitPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                           msgPanel, controlPanel);
        
        requestWindow.getContentPane().add(theSplitPanel);
        
        requestWindow.pack();
        requestWindow.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e){
        int chatNo = -1; 
        if(e.getSource() == allowBtn){
            chatNo = chatDrop.getSelectedIndex();  
            if(chatNo == 0){
                chatNo = noChats + 1;
            }
        }
        
        Object[] ret = new Object[] {chatNo, list[1]};
        
        this.setChanged();
        notifyObservers(ret);
    }
    
}
