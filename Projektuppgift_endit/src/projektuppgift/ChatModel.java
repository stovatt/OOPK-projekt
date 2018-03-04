/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projektuppgift;

import java.util.*;
import java.io.File;

/**
 *
 * @author lukasgu
 */
public class ChatModel extends Observable implements Observer{
    
    private User me;
    private Collection<User> members;
    private boolean isHost;
    private int[] preferredcryptos;
    private Collection<Message> History;
    
    public ChatModel(){
        
    }
    
    public void runSetUp(){
    
    }
    
    public void addUser(User inUser){
    
    }
    
    public void inviteOtherUser(String IpAddress, int port){
    
    }
    
    public Message createMessage(){
        return null;
    }
    
    public void sendMsg(String output){

    }
    
    public void SendFile(File output, User recipent){
    
    }
    
    public void receiveFile(){
    
    }
    
    public void updateChatHistory( Message message ){
    
    }
    
    public void update( Observable o, Object arg){
    
    }
    
    public void notifyObservers( Object arg ){
        
    }
    
}
