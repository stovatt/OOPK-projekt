/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projektuppgift;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.*;
import java.io.File;

/**
 *
 * @author lukasgu
 */
public class Controller extends Observable implements Observer, ActionListener{
    
    private View myView;
    private Model myModel;
    
    public Controller(View inView, Model inModel){
        myView = inView;
        myModel = inModel;
    }
    
    public String[] getMembers(ChatModel chat){
    
        return null;
    }
    
    public void requestConnection(Object[] list){
        
        myView.showConnectionRequestWindow(list);
    }
    
    public void sendFile( ChatModel activeChat, User recipient, File myFile, String message ){
        
    }
    
    public void saveFile(){
        
    }

    public void update(Observable o, Object arg){
        
    }
    
    public void notifyObservers( Object arg ){
        
    }
    
    public void actionPerformed(ActionEvent e){
        
    }
    
}
