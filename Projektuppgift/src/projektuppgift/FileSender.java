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
    
    private User Owner;
    private File MyFile;
    private String RequestMessage;
    private Socket fileSocket;
    private String ReplyMessage;
    
    public FileSender(File inFile, String inRequestMessage, String Size, User inOwner){
        
        Owner = inOwner;
        MyFile = inFile; 
        RequestMessage = inRequestMessage;
        
        
    }
    
    public void run(){
        
    }
    
    public void sendFileRequest(String Message, String Size){
        Owner.sendString("<message sender=\"Trasan\"> <filerequest name=" + MyFile.getName() +" size=" + Size +">" + Message + "</filerequest> </message>");
    }
    
    public void SendFile(){
        
    }
    
    public void sendProgressPercentage(){
        
    }
    
    
    public void notifyObservers( Object arg ){
        
    }
         
}
