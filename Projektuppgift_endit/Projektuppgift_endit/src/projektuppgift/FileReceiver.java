/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projektuppgift;

import java.net.Socket;
import java.util.Observable;

/**
 *
 * @author lukasgu
 */
public class FileReceiver extends Observable implements Runnable{
    
    private int serverPort;
    private int fileSize;
    private int bytesReceived;
    private byte[] file;
    private Socket fileSocket;
    
    public void sendProgressPercentage(){
        
    }
    
    public void run(){
        
    }
    
    
    public void notifyObservers( Object arg ){
        
    }
    
}
