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
        
        View TheActualView = new View();
        
        XMLConverter myConverter = new XMLConverter();
        myConverter.newXMLToMessage("<message sender=\"Trasan Apansson\"><text color=\"#RRGGBB\"> haha du e  <mystic> lalala </mystic> rolig </text></message>");
        
//        "<data><employee><name>Trasan Apansson </name>"
//        + "<title>Manager</title></employee>" + "<employee><name>Banarne </name>"
//        + "<title>Software Developer</title></employee></data>"
        
        
        
        
        
        
        
        
        
        // testa encryptern
        Encrypter myEncrypter = null;
        try {
            myEncrypter = new Encrypter();
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(Projektuppgift.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Projektuppgift.class.getName()).log(Level.SEVERE, null, ex);
        }
        
     
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
        
        

    }
    
    
    
}
