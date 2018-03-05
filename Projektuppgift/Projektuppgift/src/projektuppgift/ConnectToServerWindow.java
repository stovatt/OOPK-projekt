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
public class ConnectToServerWindow extends Observable implements ActionListener{
    
    
    private JFrame theFrame;
    private JTextField ipField;
    private JButton connectBtn;
    
    public ConnectToServerWindow(){
        
//        System.out.println("Hi from server window");
        
        theFrame = new JFrame("Connect to server");
        
        ipField = new JTextField(20);
        ipField.addActionListener(this);
        JLabel ipFieldLabel = new JLabel("IP: ");
        JPanel ipPanel = new JPanel();
        ipPanel.add(ipFieldLabel);
        ipPanel.add(ipField);
        
        connectBtn = new JButton("Connect");
        connectBtn.addActionListener(this);
        
        theFrame.setLayout(new GridLayout(0,1));
        theFrame.getContentPane().add(ipPanel);
        theFrame.getContentPane().add(connectBtn);
        
        theFrame.setVisible(true);
        theFrame.pack();
        
    }
    
    public void actionPerformed(ActionEvent e){
         String IP = ipField.getText();

         this.setChanged();
         notifyObservers(IP);
         theFrame.dispose();
    }
    
}
