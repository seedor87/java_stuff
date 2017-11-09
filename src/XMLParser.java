/**
 * Created by robertseedorf on 11/8/17.
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import static Utils.ConsolePrinting.println;

public class XMLParser {

    static String outputPath = "." + File.separatorChar + "XMLParser.out.txt"; //results go here
    static BufferedWriter writer;

    /**
     * Method to write particular line to txt file now
     */
    public static void write(Object... args) {

        /** Uncomment to print each entry */
         println(args);

        String delim = "";
        try {
            for (Object o : args) {
                writer.write(delim);
                writer.write(o.toString());
                delim = " | ";
            }
            writer.write("\n");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void printXML(Node node, String padding) {

        padding += "\t";
        try {
            for (int index = 0; index < node.getAttributes().getLength(); index ++) {
                Node attr = node.getAttributes().item(index);
                write(padding + attr.getNodeName() + " = " + attr.getNodeValue());
            }
        } catch (NullPointerException ex) {}

        try {
            for (int index = 0; index < node.getChildNodes().getLength(); index++) {
                Node nNode = node.getChildNodes().item(index);
                if(!nNode.getNodeName().equals("#text")) {
                    write(padding + nNode.getNodeName() + " : ");
                }
                if(nNode.getNodeValue() != null) {
                    write(padding + nNode.getNodeValue());
                }
                printXML(nNode, padding);
            }
        } catch (NullPointerException ex) {}
    }

    public static void main(String[] args) {

        try {

            File file = new File(outputPath);
            writer = new BufferedWriter(new FileWriter(file));

            File inputFile = new File("AirportHeliport_WW-1.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            write("Root element : " + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("AIXMBasicMessage");
            write("-------------------------------");

            int count = 0;
            nList = nList.item(0).getChildNodes();
            for (int i = 0; i < nList.getLength(); i++) {
                String padding = "";
                Node nNode = nList.item(i);
                printXML(nNode, padding);
                count++;
            }
            System.out.println(count);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.flush();
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}