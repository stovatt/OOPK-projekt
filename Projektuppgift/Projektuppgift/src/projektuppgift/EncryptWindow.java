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

/**
 *
 * @author sartov
 */
public class EncryptWindow extends Observable implements ActionListener{
    
    private JFrame encryptFrame;
    private JTextComponent textBox;
    private JButton selectBtn;
    private JButton finishBtn;
    private boolean finished = false;
    private ArrayList indices;
    
    public EncryptWindow(String msgText) {
        
        indices = new ArrayList();
        encryptFrame = new JFrame();
//        encryptFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        encryptFrame.setLayout(new GridLayout(2,0));
        
        JPanel writePanel = new JPanel();
        
        textBox = new JTextPane();
        textBox.setText(msgText);
//        textBox.setBackground(Color.RED);
        textBox.setEditable(false);
        JScrollPane scrollBox = new JScrollPane(textBox);
        scrollBox.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollBox.setPreferredSize(new Dimension(300, 150));
        writePanel.add(scrollBox);
        
        JPanel btnPanel = new JPanel();
        JButton selectBtn = new JButton("Select");
        JButton finishBtn = new JButton("Finish");
        btnPanel.add(selectBtn);
        btnPanel.add(finishBtn);
        selectBtn.addActionListener(this);
        selectBtn.setActionCommand("selectbutton");
        finishBtn.addActionListener(this);
        finishBtn.setActionCommand("finishbutton");
        
        encryptFrame.getContentPane().add(writePanel, BorderLayout.NORTH);
        encryptFrame.getContentPane().add(btnPanel, BorderLayout.SOUTH);
        encryptFrame.pack();
        encryptFrame.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e){
        
        if(e.getActionCommand().equals("selectbutton")){
            
            int start = textBox.getSelectionStart();
            int end = textBox.getSelectionEnd();
            indices.add(start);
            indices.add(end);
//            textBox.setSelectedText(Color.GREEN);
        }
        else if(e.getActionCommand().equals("finishbutton")){
            Object[] ind = indices.toArray();
            int[] indices = new int[ind.length];
            for(int i = 0; i < ind.length; i++){
                indices[i] = (int)ind[i];
            }

            Arrays.sort(indices);
            this.setChanged();
            notifyObservers(indices);
            encryptFrame.dispose();
        }
    }

}
