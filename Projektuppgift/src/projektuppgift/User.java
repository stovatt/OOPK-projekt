/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projektuppgift;

import java.util.Observable;
import java.util.Observer;
import java.net.Socket;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author lukasgu
 */
public class User extends Observable implements Observer{
    
    private String Name;
    private Encrypter myEncrypter;
    private Color myColour;
    private InputThread Input;
    private Socket mySocket;
    private InputStream myIS;
    private OutputStream myOS;
    private Collection<String> allowedCryptos;
    
    public User(Socket inSocket){
        Name = "nobody";
        myColour = Color.BLACK;
        mySocket = inSocket;
        
        
        Encrypter myEncrypter = null;
        myIS = null;
        myOS = null;
        try {
            myIS = mySocket.getInputStream();
            myOS = mySocket.getOutputStream();
            myEncrypter = new Encrypter();
        } catch (IOException | NoSuchPaddingException | NoSuchAlgorithmException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
          
    }
    
    
    public String convertToXML(Message message) {
        return null;
    }
    
    public Message ReadXML( String Message ) {
        return null;
    }
    
    
    public void sendMessage( String Message ){
        byte[] Envelope;
        try {
            Envelope = Message.getBytes("UTF-8");
            myOS.write(Envelope);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void addAllowedCrypto(String cryptotype){
        allowedCryptos.add(cryptotype);
    }
    
    public String encryptString( String Input ){
        byte[] bytes = null;
        try {
            bytes = Input.getBytes("UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (allowedCryptos.size() == 0){
            return null;
        }
        else if(allowedCryptos.contains("Ceasar")){
            bytes = myEncrypter.ceasarEncrypt(bytes);
        }
        else if(allowedCryptos.contains("AES")){
            bytes = myEncrypter.aesEncrypt(bytes);
        }
        else{
            return null;
        }
        return myEncrypter.asHex(bytes);
    }
    
    public String decryptString( String Input, String HexaKey){
        byte[] bytes = null;
        try {
            bytes = Input.getBytes("UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (allowedCryptos.size() == 0){
            return null;
        }
        else if(allowedCryptos.contains("Ceasar")){
            int key = Integer.parseInt(HexaKey);
            bytes = myEncrypter.ceasarDecrypt(bytes, key);
        }
        else if(allowedCryptos.contains("AES")){
            byte[] key = myEncrypter.hexadecimalToBytes(HexaKey);
            bytes = myEncrypter.aesDecrypt(bytes, key);
        }
        else{
            return null;
        }
        try {
            return (new String(bytes, "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void changeName( String newName ){
        Name = newName;
    }
    
    public void updateColor( Color newColour ){
        myColour = newColour;
    }
    
    public String getName(){
        return Name;
    }
    
    public Color getColor(){
        return myColour;
    }
    
    public void update( Observable o, Object arg){
        if(o instanceof InputThread){
            byte[] data = (byte[]) arg;
            String rawXML = null;
            try {
                // these bytes will be convertable to UTF-8
                rawXML = new String(data, "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            notifyObservers(rawXML);
        }
    }
    
    public void notifyObservers( Object arg ){
        
    }
    
}
