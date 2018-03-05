/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projektuppgift;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author lukasgu
 */
public class Encrypter {
    
    private int CeasarKey;
    private SecretKeySpec AESkey;
    private Cipher AEScipher;
    private static final char[] HEX_CHARS = "0123456789ABCDEF".toCharArray();
    
    public Encrypter() throws NoSuchPaddingException, NoSuchAlgorithmException {
        CeasarKey = 5;
        
        // Create Key   
        KeyGenerator AESgen = KeyGenerator.getInstance("AES");
        AESgen.init(128);
        AESkey = (SecretKeySpec)AESgen.generateKey();

        // Create cipher object
        AEScipher = Cipher.getInstance("AES");
    }
    
    
    public static String asHex(byte[] buf)
    {
        char[] chars = new char[2 * buf.length];
        for (int i = 0; i < buf.length; ++i)
        {
            chars[2 * i] = HEX_CHARS[(buf[i] & 0xF0) >>> 4];
            chars[2 * i + 1] = HEX_CHARS[buf[i] & 0x0F];
        }
        return new String(chars);
    }
    
    public static byte[] hexadecimalToBytes(String Input){
        
        // turn a string of hexadecimals to bytes
        int len = Input.length();
        
        if(len % 2 != 0){
            return null;
        }
        
        byte[] bytes = new byte[len/2];
        byte value = 0;
        char c = ' ';
        char d = ' ';
        
        for(int i = 0; i < len/2; i++){
            int index = 2*i;
            c = Input.charAt(index);
            d = Input.charAt(index+1);
            value = (byte)(16*hexaValue(c) + hexaValue(d));
            bytes[i] = value;  
        }
        return bytes;
    }
    
    public static int hexaValue(char c){
        return Character.getNumericValue(c);
    }
    
    public String stringToHexadecimal(String Input){
        if (Input == null) return "";
        try{
        return asHex(Input.getBytes("UTF-8"));
        } catch(UnsupportedEncodingException e){         
        }
        return "";
    }

    
    public byte[] ceasarEncrypt(byte[] inputBytes){
        byte[] data = inputBytes;
        
        for(int i = 0; i < data.length; i++){
            data[i] = (byte) (data[i] + CeasarKey);
        }
        
        return data;    
    }
    
    public byte[] ceasarDecrypt(byte[] inputBytes, int key) {
        byte[] data = inputBytes;
        
        for(int i = 0; i < data.length; i++){
            data[i] = (byte) (data[i] - key);
        }
        
        return data;
    }
    
    public byte[] aesEncrypt(byte[] inputBytes){
        try {
            // Encrypt
            AEScipher.init(Cipher.ENCRYPT_MODE, AESkey);
            return AEScipher.doFinal(inputBytes);
        
        // Bunch of catch clauses
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(Encrypter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    return null;    
    }    
    
    
    public byte[] aesDecrypt(byte[] inputBytes, byte[] key) {
        try {
            // Decrypt            
            SecretKeySpec decodeKey = new SecretKeySpec(key, "AES");
            AEScipher.init(Cipher.DECRYPT_MODE, decodeKey); 
            return AEScipher.doFinal(inputBytes);
            
        // bunch of catch statements
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(Encrypter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public byte[] getKey(String CryptoType){
        if(CryptoType.equals("AES")){
            return this.getAESKey();
        }
        if(CryptoType.equals("Ceasar")){
            return this.getCeasarKey();
        }
        return null;
    }
    
    public byte[] getCeasarKey(){
        
        byte a = (byte)CeasarKey;   
        byte[] bytes = {a};
        return bytes;
    }
    
    public byte[] getAESKey(){
        return AESkey.getEncoded();    
    }
       
}
