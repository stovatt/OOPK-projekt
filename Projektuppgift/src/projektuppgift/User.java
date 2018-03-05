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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.NoSuchPaddingException;


import java.util.*;

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
    private BufferedReader in;
    private PrintWriter out;
    private Collection<String> allowedCryptos;
    private XMLConverter myConverter;
    private boolean isApproved;
    
    public User(Socket inSocket){
        
        // Create a default User
        Name = "?";
        myColour = Color.BLACK;
        isApproved = false;
        allowedCryptos = new ArrayList<>();
        myConverter = new XMLConverter(this);
        
        // Try to open a socket and start an encrypter
        try {
            myEncrypter = new Encrypter();
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        if(inSocket != null){
            mySocket = inSocket;
            try {
                out = new PrintWriter(mySocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
            } catch (IOException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
            // this.sendString("<message sender=\"Trasan\"> <keyrequest type=\"Ceasar\"></keyrequest> <keyrequest type=\"AES\"></keyrequest> </message>");
            
            Input = new InputThread(in);
            Input.addObserver(this);
            Thread InputThread = new Thread(Input);
            InputThread.start();
        }  
    }
    
    
    public String convertToXML(Message message){
        return myConverter.MessageToXML(message);
    }
    
    public Message ReadXML( String inMessage ){
        return myConverter.StringToMessage(inMessage);
    }
    
    public String asHex(byte[] buf){
        return myEncrypter.asHex(buf);
    }
    
    public void sendMessage(Message inMessage){
        String Strang = this.convertToXML(inMessage);
        this.sendString(Strang);
    }
    
    public void sendString( String message ){
        out.println(message);
        System.out.println("Jag skickar: " + message);
    }
    
    public void addAllowedCrypto(String CryptoType){
        if(!allowedCryptos.contains(CryptoType)){
            if(CryptoType.equals("AES") || CryptoType.equals("Ceasar")){
                allowedCryptos.add(CryptoType);
            }
            
        }
    }
    
    public boolean isAllowedCrypto(String CryptoType){
        return allowedCryptos.contains(CryptoType);
    }
    
    public String getAllowedCrypto(){
        String[] Cryptos = (String[])allowedCryptos.toArray();
        if (Cryptos.length != 0){
            return Cryptos[0];
        }
        else{
            return "";
        }
    }
    
    public String[] encryptString( String Input ){
        byte[] bytes = null;
        String KeyString = null;
        String Crypto = null;
        
        // Take the inputString, check what cryptos the User allows and then encrypt with that crypto
        try {
            bytes = Input.getBytes("UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (allowedCryptos.size() == 0){
            String[] returnStrings = {"", "", "***"};
            return returnStrings;
        }
        else if(allowedCryptos.contains("Ceasar")){
            bytes = myEncrypter.ceasarEncrypt(bytes);
            Crypto = "Ceasar";
            KeyString = Encrypter.asHex(myEncrypter.getCeasarKey());
        }
        else if(allowedCryptos.contains("AES")){
            bytes = myEncrypter.aesEncrypt(bytes);
            Crypto = "AES";
            KeyString = Encrypter.asHex(myEncrypter.getAESKey());
        }
        else{
            return null;
        }
        
        // return the type of crypto, the key used and the encrypted message
        
        String[] returnStrings = {Crypto, KeyString, myEncrypter.asHex(bytes)};
        return returnStrings;
    }
    
    public String decryptString( String Input, String CryptoType, String HexaKey){
        byte[] bytes = null;
        bytes = Encrypter.hexadecimalToBytes(Input);
        
        // check if the specified cryptotype is supported and if so decrypt
        
        if(CryptoType.equals("Ceasar")){
            int key = (int) myEncrypter.hexadecimalToBytes(HexaKey)[0];
            bytes = myEncrypter.ceasarDecrypt(bytes, key);
        }
        else if(CryptoType.equals("AES")){
            byte[] key = myEncrypter.hexadecimalToBytes(HexaKey);
            bytes = myEncrypter.aesDecrypt(bytes, key);
        }
        else{
            return Input;
        }
        try {
            return (new String(bytes, "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void kick(){
        out.close(); 
        try {
            mySocket.close();
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setName( String newName ){
        Name = newName;
    }
    
    public void setColor( Color newColour ){
        myColour = newColour;
    }
    
    public void setIsApproved(){
        isApproved= true;
    }
    
    public boolean isApproved(){
        return isApproved;
    }
    
    public String getName(){
        return Name;
    }
    
    public Color getColor(){
        return myColour;
    }
    
    public byte[] getKey( String CryptoType){
        return myEncrypter.getKey(CryptoType);
    }
    
    public void update( Observable o, Object arg){
        this.setChanged();
        // This is how it listens to its socket
        if(o instanceof InputThread){
            if(arg instanceof String){
                String rawXML = (String) arg;
                Message pureMessage = myConverter.StringToMessage(rawXML);
                notifyObservers(pureMessage);
            }
            else{
                // We might not need this block
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
    }
    
}
