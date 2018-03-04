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
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author lukasgu
 */


public class Model implements Observer{
    
    private ChatModel activeChat;
    private Controller myController;
    private Collection<ChatModel> ChatList;
    private User me;
    
    public Model(int port, Controller inController){
        ChatList = new ArrayList<ChatModel>();
        myController = inController;
        Server myServer = new Server(port);
        myServer.addObserver(this);
        Thread serverThread = new Thread(myServer);
        serverThread.start();
        
    }
    
    public void update(Observable o, Object arg){
        if(arg instanceof Socket){
            Socket requestSocket = (Socket) arg;
            User Proposer = new User( (Socket) arg);
            Proposer.addObserver(this);
        }
        
        if(arg instanceof Message){
            Message Msg = (Message) arg;
            if(Msg.isConnectionRequest()){
                int p = myController.requestConnection(Msg);
                User applicant = (User) o;
                if(p>-1){
                    applicant.setIsApproved();
                    ChatModel[] Chats = (ChatModel[]) ChatList.toArray();
                    if(p < Chats.length ){
                        Chats[p].addUser(applicant);
                    }
                    else{
                        this.createNewChat(applicant, true);
                    }
                }
                else{
                    applicant.kick();
                }
                
            }
        }
        
    }
    
    public void connectToOtherChat( String Ip, int port ){
        Socket Connection = null;
        try {
            Connection = new Socket(Ip, port);
        } catch (IOException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (Connection != null ){
            User FirstMember = new User(Connection);
            createNewChat(FirstMember, false);
        }
        
    }
    
    public void addUser( User inUser, ChatModel chat  ){
        chat.addUser(inUser);
    }
    
    public void createNewChat( User firstMember, boolean isHost ){
        ChatList.add(new ChatModel(me, firstMember, isHost));
    }
    
    public void ConnectionRequest( Socket newSocket ){
        
    }
    
    public User createUser( Socket inSocket ){
        
        return null;
    }
    public void SendMessage(Message outMessage, ChatModel activeChat){
        
    }

 
    
}
