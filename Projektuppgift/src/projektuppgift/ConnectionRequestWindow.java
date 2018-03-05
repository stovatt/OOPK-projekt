/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projektuppgift;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;

/**
 *
 * @author sartov
 */
public class ConnectionRequestWindow {
    
    public ConnectionRequestWindow(Message inMessage){
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
    }
    
}
