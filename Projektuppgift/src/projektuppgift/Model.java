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
    private int port;
    private ChatModel activeChat;
    public ArrayList ChatList;   // public for testing
//    private Controller myController;
    
    public Model(int inPort){
        me = new User(null);
        port = inPort;
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
        if(o instanceof View){
            Object[] newArg = (Object[]) arg;
            int p = (int)newArg[0] - 1;
            User applicant = (User)newArg[1];
            System.out.println("lagt till i chatt: " + p);
                if(p>-1){
                    applicant.setIsApproved();
                    Object[] Chats = ChatList.toArray();
                    if(p < Chats.length ){
                        ChatModel someChat = (ChatModel) Chats[p];
                        someChat.addUser(applicant);
                    }
                    else{
                        this.createNewChat(applicant, true);
//                        System.out.println("Chatt har skapats med dig som server!");
                    }
                }
                else{
                    applicant.sendString("<message sender=\""+ me.getName() + "\"><request type=\"no\"></request></message>");
                    applicant.kick();
                }
        }
        
        if(arg instanceof Message){
            Message Msg = (Message) arg;
            
            if(Msg.isConnectionRequest()){
                Object[] List = new Object[2];
                List[0] = Msg;
                List[1] = (User)o;
                this.setChanged();
                notifyObservers(List);
//                myController.requestConnection(List);
            }
        }
        
    }
    
    public void connectToOtherChat( String Ip, int port, Message requestMsg ){
        Socket Connection = null;
        try {
            Connection = new Socket(Ip, port);
        } catch (IOException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (Connection != null ){
            User FirstMember = new User(Connection);
            FirstMember.sendMessage(requestMsg);
            ChatModel NewChat = createNewChat(FirstMember, false);
//            NewChat.sendMsg( new Message(me.getName(), Color.BLACK, requestMsg, new int[] {}, false, true ) );
        }
        
    }
    
    public void addUser( User inUser, ChatModel chat  ){
        chat.addUser(inUser);
    }
    
    public ChatModel createNewChat( User firstMember, boolean isHost ){
//        firstMember.sendString("<message sender=\"Trasan\"> <keyrequest type=\"Ceasar\"></keyrequest> <keyrequest type=\"AES\"></keyrequest> </message>");
        ChatModel a = new ChatModel(me, firstMember, isHost);
        ChatList.add(a);
        this.setChanged();
        System.out.println("Notifiar view med ny chatmodel");
        this.notifyObservers(a);
        return a;
    }
    

    public void SendMessage(Message outMessage, ChatModel activeChat){
        
    }
    
    public ArrayList getChatList(){
        return ChatList;
    }
    
    public int getPort(){
        return port;
    }
 
//    public void setMyController(Controller inController){
//        myController = inController;
//    }
    
    public void setMe(User inMe){
        me = inMe;  
    }
    
    public User getMe(){
        return me;
    }
    
}
