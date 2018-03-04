/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projektuppgift;

/**
 *
 * @author lukasgu
 */
public class FileRequest {
    
    private String FileRequestMessage;
    private String FileSize;
    private String Name;
    
    FileRequest(String Msg, String Size, String inName){
        FileRequestMessage = Msg;
        FileSize = Size;
        Name = inName;
    }
    
}
