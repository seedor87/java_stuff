/**
 * Created by robertseedorf on 11/8/17.
 */

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class XMLParser {

    public static void main(String[] args) {

        try {
            File inputFile = new File("AirportHeliport_WW-1.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element : " + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("AIXMBasicMessage");
            System.out.println("-------------------------------");

            int count = 0;
            nList = nList.item(0).getChildNodes();
            for (int i = 0; i < nList.getLength(); i++) {
                String padding = "";
                Node nNode = nList.item(i);
                System.out.println(padding + nNode.getNodeName() + " = " + nNode.getNodeValue());
                try {
                    for (int temp =0; temp < nNode.getAttributes().getLength(); temp ++) {
                        Node attr = nNode.getAttributes().item(temp);
                        System.out.println(padding + attr.getNodeName() + " : " + attr.getNodeValue());
                    }
                } catch (NullPointerException ex) {
                    // pass
                }

                try {
                    for (int j = 0; j < nNode.getChildNodes().getLength(); j++) {
                        padding += "\t";
                        Node nnNode = nNode.getChildNodes().item(j);
                        System.out.println(padding + nnNode.getNodeName() + " = " + nnNode.getNodeValue());
                        try {
                            for (int temp =0; temp < nnNode.getAttributes().getLength(); temp ++) {
                                Node attr = nnNode.getAttributes().item(temp);
                                System.out.println(padding + attr.getNodeName() + " : " + attr.getNodeValue());
                            }
                        } catch (NullPointerException ex) {
                            // pass
                        }

                        try {
                            for (int k = 0; k < nnNode.getAttributes().getLength(); k++) {
                                padding += "\t";
                                Node nnnNode = nnNode.getChildNodes().item(k);
                                System.out.println(padding + nnnNode.getNodeName() + " = " + nnnNode.getNodeValue());
                                try {
                                    for (int temp =0; temp < nnnNode.getAttributes().getLength(); temp ++) {
                                        Node attr = nnnNode.getAttributes().item(temp);
                                        System.out.println(padding + attr.getNodeName() + " : " + attr.getNodeValue());
                                    }
                                } catch (NullPointerException ex) {
                                    // pass
                                }

                                try {
                                    for (int l = 0; k < nnNode.getAttributes().getLength(); l++) {
                                        padding += "\t";
                                        Node nnnnNode = nnnNode.getChildNodes().item(l);
                                        System.out.println(padding + nnnnNode.getNodeName() + " = " + nnnnNode.getNodeValue());
                                        try {
                                            for (int temp =0; temp < nnnnNode.getAttributes().getLength(); temp ++) {
                                                Node attr = nnnnNode.getAttributes().item(temp);
                                                System.out.println(padding + attr.getNodeName() + " : " + attr.getNodeValue());
                                            }
                                        } catch (NullPointerException ex) {
                                            // pass
                                        }

                                    }
                                }catch (NullPointerException ex) {
                                    continue;
                                }
                            }
                        }catch (NullPointerException ex) {
                            continue;
                        }
                    }
                } catch (NullPointerException ex) {
                    continue;
                }


//                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
//                    Element eElement = (Element) nNode;
//                    System.out.println("Has Member " + eElement.getAttribute("hasMember"));
//
//                    Node identifier = eElement.getElementsByTagName("gml:identifier").item(0);
//                    System.out.println("gml:identifier: " + identifier.getTextContent());
//                    System.out.println(identifier.getAttributes().getNamedItem("codeSpace"));
//
//                    Node timeSlice = eElement.getElementsByTagName("aixm:timeSlice").item(0);
//
//                    Node airportHeliportTimeSlice = eElement.getElementsByTagName("aixm:AirportHeliportTimeSlice").item(0);
//                    System.out.println(airportHeliportTimeSlice.getAttributes().getNamedItem("gml:id"));
//
//                }
                count++;
            }
            System.out.println(count);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}