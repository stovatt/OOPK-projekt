/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projektuppgift;

import java.io.IOException;
import java.util.Observable;
import java.util.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lukasgu
 */
public class Server extends Observable implements Runnable{
    
    private ServerSocket myServerSocket;
    
    public Server(int port){
        try {
            myServerSocket = new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void run(){
//        System.out.println("Servern är igång");
        Socket proposerSocket = null;
        int start = 0;
        int end = 0;
        while(true){
            try {
                proposerSocket = myServerSocket.accept();
                this.setChanged();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
//            System.out.println("Uppkopplingsförfrågan!");

            
            if(proposerSocket != null){
                notifyObservers(proposerSocket);
                proposerSocket = null; 
            }
                
        }
        
        
    }
    
    
}
