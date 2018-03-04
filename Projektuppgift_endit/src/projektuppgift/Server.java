/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projektuppgift;

import java.util.Observable;
import java.util.*;
import java.net.Socket;
import java.net.ServerSocket;

/**
 *
 * @author lukasgu
 */
public class Server extends Observable implements Runnable{
    
    private ServerSocket myServerSocket;
    
    
    public void run(){
        
    }
    
    public void  notifyObservers(Socket inSocket){
        
    }
    
}
