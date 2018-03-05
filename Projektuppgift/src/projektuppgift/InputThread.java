/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projektuppgift;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lukasgu
 */

public class InputThread extends Observable implements Runnable{
    
    private BufferedReader in;
    private String TempString;
    private String New;
    
    public InputThread(BufferedReader inIn){
        in = inIn;
        TempString = "";
        New = "";
    }
    
    public void run(){
        
        boolean request = true;
//        System.out.println("Börjar lyssna på motparten");
        while(true){
            try {
                New = in.readLine();
                if (New == null || in == null) break;
//                System.out.println("Fick ett meddelande: "+ New);
            } catch (IOException ex) {
                Logger.getLogger(InputThread.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("lost connection");
            }
            // if it is a new message, reset tempString
            if(!(New.equals(""))){
//                System.out.println("Starttagen har index  -->  " + New.indexOf("<message"));
                if(New.indexOf("<message") != -1 ){
//                    System.out.println("Denna rad har en starttag!");
                    TempString = New;
                    request = false;
                }
                else{
//                    System.out.println("Denna rad har inte en starttag!");
                    TempString = TempString + "\n"+ New;
                }

                if(TempString.indexOf("</message>") != -1){
//                    System.out.println("Denna rad har en sluttag!");
                    this.setChanged();
                    System.out.println("Jag tog emot: "+ TempString);
                    this.notifyObservers(TempString);
                    
                }
            }
            
        }
    }
}
