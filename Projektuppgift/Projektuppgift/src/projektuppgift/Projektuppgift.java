/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projektuppgift;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.NoSuchPaddingException;

import java.awt.Color;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.lang.Thread;
import java.net.InetAddress;

/**
 *
 * @author lukasgu
 */
public class Projektuppgift {

    private Model myModel;
    private View myView;
    private Controller myController;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
          // testa encryptern
//        Encrypter myEncrypter = null;
//        try {
//            myEncrypter = new Encrypter();
//        } catch (NoSuchPaddingException ex) {
//            Logger.getLogger(Projektuppgift.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (NoSuchAlgorithmException ex) {
//            Logger.getLogger(Projektuppgift.class.getName()).log(Level.SEVERE, null, ex);
//        }

        Projektuppgift theProject = new Projektuppgift();
        
//        theProject.myModel.connectToOtherChat("130.229.147.162", theProject.myModel.getPort(), "hej får jag vara med?");
//        Server testServer = new Server(4444);
//        Thread testThread = new Thread(testServer);
//        testThread.start();
//        System.out.println("heya");
//        try {
//            Socket testSocket = new Socket("2001:6b0:1:1041:5133:e920:9185:b5f6", 4444);
//                    
//                    
//                    
//                    
//          // Testa att konvertera till och från Message och XML-sträng
//        User testperson = new User(testSocket);
//        testperson.addAllowedCrypto("Ceasar");
//        XMLConverter myConverter = new XMLConverter(testperson);
//        
//        // try creating XMLO from message
//        String text = "del 1 krypkryp del2 encenc del3";
//        int[] indices = {6, 15, 21, 30};
//        Message trialmesage = new Message("Trasan", Color.BLUE, text, indices, true);
//
//        System.out.println("trialmessage har blivit till: " + myConverter.MessageToXML(trialmesage));
//
//
//        
//        Message testing = myConverter.StringToMessage(myConverter.MessageToXML(trialmesage));
//
//        System.out.println(testing.getText());
//        System.out.println(testing.isDisconnect());
                    
                    
                    
                
                    
//         // testning hexadecimala talen
//        String hexa = myEncrypter.stringToHexadecimal("Hello StackOverflow");
//        System.out.println(hexa);
//            
//        String regular = "funkar inte";
//        try {
//        regular = new String(myEncrypter.hexadecimalToBytes(hexa), "UTF-8");
//        } catch (UnsupportedEncodingException ex) {
//        Logger.getLogger(Projektuppgift.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        System.out.println(regular);
                    
                    
                    
//        // test av ceasarkrypto
//        
//        String secret = "hej på dig idag!";
//        byte[] bytes = null;
//        try {
//            bytes = secret.getBytes("UTF-8");
//        } catch (UnsupportedEncodingException ex) {
//            Logger.getLogger(Projektuppgift.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        System.out.println(myEncrypter.asHex(bytes));
//        
//        bytes = myEncrypter.ceasarEncrypt(bytes);
//        System.out.println(myEncrypter.asHex(bytes));
//        
//        bytes = myEncrypter.ceasarDecrypt(bytes, myEncrypter.getCeasarKey());
//        System.out.println(myEncrypter.asHex(bytes));
//        try {
//            System.out.println(new String(bytes, "UTF-8"));
//        } catch (UnsupportedEncodingException ex) {
//            Logger.getLogger(Projektuppgift.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
//        // test av AESkrypto
//        
//        String mystery = "Hello StackOverflow, jag är glad idag håhå";
//        byte[] bytes = null;
//        try {
//            bytes = mystery.getBytes("UTF-8");
//        } catch (UnsupportedEncodingException ex) {
//            Logger.getLogger(Projektuppgift.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        System.out.println(myEncrypter.asHex(bytes));
//        
//        bytes = myEncrypter.aesEncrypt(bytes);
//        System.out.println(myEncrypter.asHex(bytes));
//        
//        bytes = myEncrypter.aesDecrypt(bytes, myEncrypter.getByteAESKey());
//        System.out.println(myEncrypter.asHex(bytes));
//        try {
//            System.out.println(new String(bytes, "UTF-8"));
//        } catch (UnsupportedEncodingException ex) {
//            Logger.getLogger(Projektuppgift.class.getName()).log(Level.SEVERE, null, ex);
//        }
                    
//                    
//                    
//                    } catch (IOException ex) {
//            Logger.getLogger(Projektuppgift.class.getName()).log(Level.SEVERE, null, ex);
//        }

        

    }
    
  public Projektuppgift(){
      
            myModel = new Model(4444);
            myView = new View(myModel);
            myController = new Controller(myView, myModel);
            myModel.setMyController(myController);
            
//            Message firstMessage = new Message("Putte", Color.RED, "Hej, får jag vara med?");
//            myView.showConnectionRequestWindow(firstMessage);
        }  
    
}
