package XMLParser;
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

import static Utils.Console.Printing.print;
import static Utils.Console.Printing.println;

public class XMLParser {

    static String outputPath = "." + File.separatorChar + "XMLParser.out.txt"; //results go here
    static BufferedWriter writer;

    /**
     * Method to write particular line to txt file now
     */
    public static void write(String str) {

        /** Uncomment to print each entry */
         print(str);
        try {
            writer.write(str);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void printXML(Node node, String padding) {

        try {
            for (int index = 0; index < node.getAttributes().getLength(); index ++) {
                Node attr = node.getAttributes().item(index);
                write(padding + attr.getNodeName() + " = " + attr.getNodeValue() + "\n");
            }
        } catch (NullPointerException ex) {}

        padding += "\t";
        try {
            for (int index = 0; index < node.getChildNodes().getLength(); index++) {
                Node nNode = node.getChildNodes().item(index);
                if(nNode.getNodeName().contains("#text") && nNode.getNodeValue().trim().length() < 1) {
                    // pass
                } else {
                    if(nNode.getNodeValue() == null) {
                        write(padding + nNode.getNodeName() + " : \n");
                    } else if (nNode.getNodeName().contains("#text")) {
                        write(padding + "'" + nNode.getNodeValue() + "'\n");
                    } else {
                        write(padding + nNode.getNodeName() + " : '" + nNode.getNodeValue() + "'\n");
                    }
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

            String root = doc.getDocumentElement().getNodeName();
            write("Root element : " + root + "\n");
            NodeList nodeList = doc.getElementsByTagName(root);
            write("-------------------------------\n");

            int count = 0;
            nodeList = nodeList.item(0).getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                String padding = "";
                Node node = nodeList.item(i);
                printXML(node, padding);
                count++;
            }
            println(count);

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