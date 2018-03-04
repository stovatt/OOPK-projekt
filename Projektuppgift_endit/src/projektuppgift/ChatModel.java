/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projektuppgift;

import java.util.*;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lukasgu
 */
public class ChatModel extends Observable implements Observer{
    
    private User me;
    private Collection<User> Members;
    private boolean isHost;
    private Collection<Message> History;
    
    public ChatModel(User inMe, User Member, boolean Host ){
        me = inMe;
        Members = new ArrayList<>();
        Members.add(Member);
        isHost = Host;
        Members = new ArrayList<>();   
    }
    
    public void runSetUp(){
    
    }
    
    public User getMe(){
        return me;
    }
    
    public void addUser(User inUser){
        Members.add(inUser);
    }
    
//    public void inviteOtherUser(String IpAddress, int port){
//        Socket newSocket = null;
//        try {
//            newSocket = new Socket(IpAddress, port);
//        } catch (IOException ex) {
//            Logger.getLogger(ChatModel.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        if(newSocket != null){
//            User NewMember = new User(newSocket, "?");
//            Members.add(NewMember);
//        }
//    }
        
    public void sendMsg(Message Output){
        User[] List = (User[]) Members.toArray();
        for(User Member: List){
            Member.sendMessage(Output);
        }
        this.updateChatHistory(Output);
    }
    
    public void SendFile(File output, User recipent){
    
    }
    
    public void receiveFile(){
    
    }
    
    public Message[] getHistory(){
        return (Message[]) History.toArray();
    }
    
    public void updateChatHistory( Message message ){
        History.add(message);
        this.setChanged();
        notifyObservers(message);
    }
    
    public void update( Observable o, Object arg ){
        if(o instanceof User && arg instanceof Message){
            this.updateChatHistory((Message) arg);
        }
        
    }
    
}
