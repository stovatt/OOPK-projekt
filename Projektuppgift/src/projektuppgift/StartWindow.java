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
public class StartWindow extends Observable implements ActionListener{
    
    
    private JFrame theFrame;
    private JTextField nameField;
    private JTextField portField;
    private JComboBox<Color> colorDrop;
    private JButton saveBtn;
    
    public StartWindow(){
        
//        System.out.println("Hi from settings window");
        
        theFrame = new JFrame("Settings");
        
        nameField = new JTextField("Unknwn user",20);
        nameField.addActionListener(this);
        JLabel nameFieldLabel = new JLabel("Name: ");
        JPanel namePanel = new JPanel();
        namePanel.add(nameFieldLabel);
        namePanel.add(nameField);
        
        portField = new JTextField("4444", 20);
        portField.addActionListener(this);
        JLabel portFieldLabel = new JLabel("Port: ");
        JPanel portPanel = new JPanel();
        portPanel.add(portFieldLabel);
        portPanel.add(portField);
        
        Color[] colors = new Color[] {Color.BLACK, Color.RED, Color.BLUE,
                        Color.GREEN, Color.ORANGE, Color.PINK};
        colorDrop = new JComboBox<>(colors);
        
        saveBtn = new JButton("Save changes");
        saveBtn.addActionListener(this);
        
        theFrame.setLayout(new GridLayout(0,1));
        theFrame.getContentPane().add(namePanel);
        theFrame.getContentPane().add(portPanel);
        theFrame.getContentPane().add(colorDrop);
        theFrame.getContentPane().add(saveBtn);
        
        theFrame.setVisible(true);
        theFrame.pack();
        
    }
    
    public void actionPerformed(ActionEvent e){
         String name = nameField.getText();
         String port = portField.getText();
         Color color = (Color)colorDrop.getSelectedItem();
         
         User me = new User(null);
         me.setName(name);
         me.setColor(color);
         
         Object[] ret = new Object[] {me, port};
         
         this.setChanged();
         notifyObservers(ret);
         theFrame.dispose();
    }
    
}
