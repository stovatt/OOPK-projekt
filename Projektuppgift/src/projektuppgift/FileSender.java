/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projektuppgift;

import java.util.Observable;
import java.io.File;
import java.net.Socket;

/**
 *
 * @author lukasgu
 */
public class FileSender extends Observable implements Runnable{
    
    
    private File myFile;
    private String requestMessage;
    private Socket messageSocket;
    private Socket fileSocket;
    private String ReplyMessage;
    
    public void run(){
        
    }
    
    public void sendFileRequest(String Message){
        
    }
    
    public void SendFile(){
        
    }
    
    public void sendProgressPercentage(){
        
    }
    
    
    public void notifyObservers( Object arg ){
        
    }
         
}
