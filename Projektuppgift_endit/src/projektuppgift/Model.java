/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projektuppgift;

import java.awt.Color;
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


public class Model extends Observable implements Observer{
    
    private User me;
    private ChatModel activeChat;
    public ArrayList ChatList;   // public for testing
    
    public Model(int port){
        me = new User(null);
        ChatList = new ArrayList<ChatModel>();
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
                //int p = myController.requestConnection(Msg);
                int p = 5;
                User applicant = (User) o;
                if(p>-1){
                    applicant.setIsApproved();
                    Object[] Chats = ChatList.toArray();
                    if(p < Chats.length ){
                        ChatModel someChat = (ChatModel) Chats[p];
                        someChat.addUser(applicant);
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
    
    public void connectToOtherChat( String Ip, int port, String requestMsg ){
        Socket Connection = null;
        try {
            Connection = new Socket(Ip, port);
        } catch (IOException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (Connection != null ){
            User FirstMember = new User(Connection);
            ChatModel NewChat = createNewChat(FirstMember, false);
            NewChat.sendMsg( new Message(me.getName(), Color.BLACK, requestMsg, new int[] {}, false, true ) );
        }
        
    }
    
    public void addUser( User inUser, ChatModel chat  ){
        chat.addUser(inUser);
    }
    
    public ChatModel createNewChat( User firstMember, boolean isHost ){
        ChatModel a = new ChatModel(new User(null), firstMember, isHost);
        ChatList.add(a);
        this.setChanged();
        this.notifyObservers(a);
        return a;
    }
    

    public void SendMessage(Message outMessage, ChatModel activeChat){
        
    }
    
    public ArrayList getChatList(){
        return ChatList;
    }
 
    
}
