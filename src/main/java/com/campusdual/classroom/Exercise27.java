package com.campusdual.classroom;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.util.LinkedHashMap;
import java.util.Map;

public class Exercise27 {

    public static void main(String[] args) {
        Map<String, Integer> shoppingList = new LinkedHashMap<>();
        shoppingList.put("Manzana", 2);
        shoppingList.put("Leche", 1);
        shoppingList.put("Pan", 3);
        shoppingList.put("Huevo", 2);
        shoppingList.put("Queso", 1);
        shoppingList.put("Cereal", 1);
        shoppingList.put("Agua", 4);
        shoppingList.put("Yogur", 6);
        shoppingList.put("Arroz", 2);

        createXML(shoppingList);
        createJSON(shoppingList);
    }

    public static void createXML(Map<String, Integer> shoppingList) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();


            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("shoppinglist");
            doc.appendChild(rootElement);


            Element items = doc.createElement("items");
            rootElement.appendChild(items);


            for (Map.Entry<String, Integer> entry : shoppingList.entrySet()) {
                Element item = doc.createElement("item");
                item.setAttribute("quantity", String.valueOf(entry.getValue()));
                item.appendChild(doc.createTextNode(entry.getKey()));
                items.appendChild(item);
            }


            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("src/main/resources/shoppingList.xml"));

            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createJSON(Map<String, Integer> shoppingList) {
        JsonArray itemsArray = new JsonArray();


        for (Map.Entry<String, Integer> entry : shoppingList.entrySet()) {
            JsonObject item = new JsonObject();
            item.addProperty("quantity", entry.getValue());
            item.addProperty("text", entry.getKey());
            itemsArray.add(item);
        }


        JsonObject root = new JsonObject();
        root.add("items", itemsArray);


        try (FileWriter file = new FileWriter("src/main/resources/shoppingList.json")) {
            file.write(root.toString());
            file.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
