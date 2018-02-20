/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projektuppgift;

import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Person
 */
public class XMLConverter {
    
    public XMLConverter(){
        
    }
    
    public Message XMLToMessage(String xmlRecords){
        
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
        
        
        // Here the actaully interesting stuff happens
        
        NodeList nodes = doc.getElementsByTagName("message");
        System.out.println(nodes);

        for (int i = 0; i < nodes.getLength(); i++) {
            Element element = (Element) nodes.item(i);

            NodeList name = element.getElementsByTagName("name");
            Element line = (Element) name.item(0);
            System.out.println("Name: " + getCharacterDataFromElement(line));

            NodeList title = element.getElementsByTagName("title");
            line = (Element) title.item(0);
            System.out.println("Title: " + getCharacterDataFromElement(line));
        }  
        return null;
    }
   
    
    public Message newXMLToMessage(String xmlRecords){
        
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
        
        
        // Here the actaully interesting stuff happens
        
        NodeList nodes = doc.getElementsByTagName("message");
        // System.out.println(nodes.getLength());

        for (int i = 0; i < nodes.getLength(); i++) {
            Element element = (Element) nodes.item(i);
            String sender = element.getAttribute("sender");
            System.out.println("Sender: " + sender);
            
//            NodeList name = element.getElementsByTagName("sender");
//            
//            Element line = (Element) name.item(0);
//            System.out.println("Sender: " + getCharacterDataFromElement(line));

            NodeList text = element.getElementsByTagName("text");

            Element line = (Element) text.item(0);
            String color = line.getAttribute("color");
            System.out.println("Color: " + color);
            System.out.println("Text: " + line.getTextContent());


        }  
        return null;
    }
    
    
    public String getCharacterDataFromElement(Element ele) {
        Node child = ele.getFirstChild();
        if (child instanceof CharacterData) {
          CharacterData cd = (CharacterData) child;
          return cd.getData();
        }
        return "";
    }
    
}
