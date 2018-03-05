/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projektuppgift;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.awt.Color;

/**
 *
 * @author Person
 */

public class XMLConverter {
    
    private User Owner;
    
    public XMLConverter(User inUser){
        Owner = inUser;
    }
    
    public String MessageToXML(Message input){
        try {
            
            // create a doc that will be filled with info from input
            DocumentBuilderFactory dbFactory =
            DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            
            
            // chop the input text into encrypted and not encrypted pieces
            int[] Indices = input.getEncryptIndices();
            int No_parts = Indices.length + 1;
            if (Indices.length%2 != 0) return null;
            
            String[] Parts = new String[No_parts];
            String Text = input.getText();
            
            if(No_parts > 1){
                Parts[0] = Text.substring(0, Indices[0]);
                for(int i = 0; i< No_parts-2; i++){
                    Parts[i+1] = Text.substring(Indices[i], Indices[i+1]);
                }
                Parts[No_parts-1] = Text.substring(Indices[No_parts-2]);
            }
            else{
                Parts[0] = Text;
            }

            
            
            // create root element "message"
            Element RootElement = doc.createElement("message");
            RootElement.setAttribute("sender", input.getName());
            doc.appendChild(RootElement);
            
            // Create text element
            Element TextNode = doc.createElement("text");
            int[] RGB = input.getRGB();
            byte[] RGBbyte = new byte[3];
            RGBbyte[0] = (byte) RGB[0];
            RGBbyte[1] = (byte) RGB[1];
            RGBbyte[2] = (byte) RGB[2];            
            TextNode.setAttribute("color", "#" + Encrypter.asHex(RGBbyte));
            RootElement.appendChild(TextNode);
            
            // add each piece of the input text, encrypted or not
            for(int i = 0; i < Parts.length; i++){
                if(i%2 == 0) TextNode.appendChild(doc.createTextNode(Parts[i]));
                else{
                    String[] EncryptInfo = Owner.encryptString(Parts[i]);   // Here there is a problem if the User has a null socket
                    Element EncryptedNode = doc.createElement("encrypted");
                    System.out.println(Parts[i]);
                    EncryptedNode.setAttribute("type", EncryptInfo[0]);
                    EncryptedNode.setAttribute("key", EncryptInfo[1]);
                    EncryptedNode.appendChild(doc.createTextNode(EncryptInfo[2]));
                    TextNode.appendChild(EncryptedNode);
                }
            }
            
            
            // Determine if it is a disconnect message
            
            if(input.isDisconnect()){
                RootElement.appendChild(doc.createElement("disconnect"));
            }
            
            // Determine if it is a ConnectionRequest message
            
            if(input.isConnectionRequest()){
                RootElement.appendChild(doc.createElement("request"));
            }
            
            
            // turn the doc into a string
            
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = null;
            transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            String output = writer.getBuffer().toString().replaceAll("\n|\r", "");
            
            // return the result
            return output;
            
        } catch (ParserConfigurationException | TransformerException ex) {
            Logger.getLogger(XMLConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "hej";
    }
    
    
    
    public Message StringToMessage(String xmlRecords){
        
        // First we turn the string into an inputSource
        DocumentBuilder db = null;
        try {
            db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(xmlRecords));

        Document doc = null;
        try {
            doc = db.parse(is);
        } catch (SAXException ex) {
            Logger.getLogger(XMLConverter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XMLConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        // Now we have created a doc containing the info of the received message
        
        // the message tag is the root
        NodeList roots = doc.getElementsByTagName("message");
        Element root = (Element) roots.item(0);
        
        // Find who sent the message
        String sender = root.getAttribute("sender");
        
        // start reading the text
        NodeList TheTextList = doc.getElementsByTagName("text");
        Color TheColor = null;
        String Cryptotype;
        String CryptoKey;
        Element ENode;
        String Texten;
        if(TheTextList.getLength() > 0){
            Element Text = (Element) TheTextList.item(0);

            // Determine the color of the text
            String RGB = Text.getAttribute("color");
            byte[] RGBbyte = Encrypter.hexadecimalToBytes(RGB.substring(1));
            TheColor = new Color((RGBbyte[0]+256)%256 , (RGBbyte[1]+256)%256 , (RGBbyte[2]+256)%256 );

            // Chop the text into encrypted and not encrypted pieces and decipher.
            // Then replace encrypted text with decrypted text
            NodeList EncryptedParts = doc.getElementsByTagName("encrypted");
            int No_EParts = EncryptedParts.getLength();

            
            for(int i = 0; i < No_EParts; i++){
                ENode = (Element) EncryptedParts.item(i);
                Cryptotype = ENode.getAttribute("type");
                Owner.addAllowedCrypto(Cryptotype);                   // add that that one may send messages encrypted with this kind of crypto
                CryptoKey = ENode.getAttribute("key");
                String Encryptedtext = ENode.getTextContent();
                String DecryptedText = Owner.decryptString(Encryptedtext, Cryptotype, CryptoKey) ;
                ENode.setTextContent(DecryptedText);
            }
            
            Texten = Text.getTextContent();
        }
        else{
            TheColor = Color.BLACK;
            Texten = "";
        }
        
        // Change attributes that the User might have changed about themslves
        Owner.setName(sender);
        Owner.setColor(TheColor);
        
        // Check if the User is to disconnect
        boolean Disconnect;
        NodeList DisconnectTags = doc.getElementsByTagName("disconnect");
        Disconnect = DisconnectTags.getLength() != 0;
        
        // Check if this is a connection request
        boolean ConnectionRequest;
        NodeList ConnectionRequestTags = doc.getElementsByTagName("request");
        ConnectionRequest = ConnectionRequestTags.getLength() != 0;
        
        // Check if the message contains a keyrequest, if so send an empty message encrypted with both AES and Ceasar
        NodeList KeyRequestTags = doc.getElementsByTagName("keyrequest");
        for(int i = 0; i < KeyRequestTags.getLength(); i++){
            Element Node = (Element) KeyRequestTags.item(i);
            Cryptotype = Node.getAttribute("type");
            if(Owner.isAllowedCrypto(Cryptotype)){
                Owner.sendString("<message sender=\"Trasan\"> <encrypted type="+ Cryptotype +"></encrypted> </message>");
            }
            
        }
        
        // Create the output in form of an instance of the "Message" class. Disregard what parts were encrypted or not
        
        int[] dummy = {0,0};
        return new Message(sender, TheColor, Texten, dummy, Disconnect, ConnectionRequest);
    }
    
    
    public String getCharacterDataFromElement(Element ele) {
        
        // Extract the text from a Node
        
        Node child = ele.getFirstChild();
        if (child instanceof CharacterData) {
          CharacterData cd = (CharacterData) child;
          return cd.getData();
        }
        return "";
    }
    
}
