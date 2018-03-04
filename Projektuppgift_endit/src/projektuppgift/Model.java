/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projektuppgift;

import java.util.Observable;
import java.util.*;
import java.net.Socket;


/**
 *
 * @author lukasgu
 */


public class Model implements Observer{
    
    private ChatModel activeChat;
    private Collection<ChatModel> ChatList;
    
    public Model(){
        
    }
    
    public void update(Observable o, Object arg){
        
    }
    
    
    public void OpenServerSocket(){
        
    }
    
    public void connectToServerSocket( String Ip, int port ){
        
    }
    
    public void addUser( User inUser, ChatModel chat  ){
        
    }
    
    public ChatModel createNewChat( User firstMember, boolean isHost ){
        
        return null;
    }
    
    public void ConnectionRequest( Socket newSocket ){
        
    }
    
    public User createUser( Socket inSocket ){
        
        return null;
    }
    public void SendMessage(Message outMessage, ChatModel activeChat){
        
    }

 
    
}
