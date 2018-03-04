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

public class InputThread extends Observable{
    
    private BufferedReader in;
    private String TempString;
    private String New;
    
    public InputThread(BufferedReader inIn){
        in = inIn;
        TempString = "";
        New = "";
    }
    
    public void run(){
        while(true){
            try {
                New = in.readLine();
            } catch (IOException ex) {
                Logger.getLogger(InputThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(!New.equals("")){
                if(New.indexOf("<message/>") == -1){
                    TempString = TempString + New;
                    New = "";
                }
                else if(!TempString.equals("")){
                    this.notifyObservers(TempString);
                    TempString = "";
                } 
            }
        }
    }
}
