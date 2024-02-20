package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        String fileName = "src/main/resources/data.xml";
        List<Employee> list = parseXML(fileName);
        String s = listToJson(list);
        writeString(s);
    }

    public static List<Employee> parseXML(String fileName) {
        File xmlFile = new File(fileName);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;

        List<Employee> list = new ArrayList<>();

        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("employee");


            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    Long id = Long.parseLong(getValueFromTag("id", element));
                    String firstName = getValueFromTag("firstName", element);
                    String lastName = getValueFromTag("lastName", element);
                    String country = getValueFromTag("country", element);
                    Integer age = Integer.parseInt(getValueFromTag("age", element));
                    list.add(new Employee(id, firstName, lastName, country, age));
                }

            }
        } catch (Exception e) {
        }


        return list;

    }

    public static String getValueFromTag(String tag, Element element) {
        NodeList nodeListEmployee = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node nodeEmployee = (Node) nodeListEmployee.item(0);
        return nodeEmployee.getNodeValue();
    }

    public static String listToJson(List<Employee> list) {
        return new Gson().toJson(list);
    }

    public static void writeString(String s) {
        try (FileWriter file = new FileWriter("src/main/resources/data2.json")) {
            file.write(s);
            file.flush();
        } catch (IOException e) {
        }
    }
}