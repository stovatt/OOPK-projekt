/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projektuppgift;

import java.awt.Color;

/**
 *
 * @author lukasgu
 */
public class Message {

    private String Name;
    private Color TheColor;
    private String Text;
    private int[] EncryptIndices;
    
    public Message(){
        
    }
    
    public String getName(){        
       return Name;
    }
 
    public Color getColor(){
        return TheColor;
    }
    
    public String getText(){
        return Text;
    }
    
    public int[] getEncryptIndices(){
        return EncryptIndices;
    }
    
    public void setText(String Input){
        Text = Input;
    }
    
}
