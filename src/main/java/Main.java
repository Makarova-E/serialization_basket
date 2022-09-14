import org.json.simple.parser.ParseException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ParseException, ParserConfigurationException, SAXException {
        Basket basket = null;
        boolean enabledSave = false;
        String formatFileSave = null;
        String fNameSave = null;
        boolean enabledLog = false;
        String fNameLog = null;

        Scanner scanner = new Scanner(System.in);
        String[] products = {"Хлеб", "Яблоки", "Молоко"};
        int[] prices = {100, 200, 300};
        ClientLog clientLog = new ClientLog();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File("shop.xml"));

        NodeList nodeList = doc.getElementsByTagName("load");
        if (nodeList.getLength() == 0) {
            System.out.println("Тег load не найден");
        } else {
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String fNameLoad = element.getElementsByTagName("fileName").item(0).getTextContent();
                    String formatFileLoad = element.getElementsByTagName("format").item(0).getTextContent();
                    String enabledLoad = element.getElementsByTagName("enabled").item(0).getTextContent();
                    if (enabledLoad.equals("false")) {
                        basket = new Basket(products, prices);
                    } else {
                        if (formatFileLoad.equals("json")) {
                            basket = Basket.loadFromJsonFile(new File(fNameLoad));
                        } else {
                            basket = Basket.loadFromTxtFile(new File(fNameLoad));
                        }
                    }
                }
            }
        }
        NodeList nodeSave = doc.getElementsByTagName("save");
        if (nodeSave.getLength() == 0) {
            System.out.println("Тег save не найден");
        } else {
            for (int i = 0; i < nodeSave.getLength(); i++) {
                Node node = nodeSave.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    fNameSave = element.getElementsByTagName("fileName").item(0).getTextContent();
                    formatFileSave = element.getElementsByTagName("format").item(0).getTextContent();
                    String enablSave = element.getElementsByTagName("enabled").item(0).getTextContent();
                    enabledSave = Boolean.valueOf(enablSave);
                }
            }
        }
        NodeList nodeLog = doc.getElementsByTagName("log");
        if (nodeLog.getLength() == 0) {
            System.out.println("Тег log не найден");
        } else {
            for (int i = 0; i < nodeLog.getLength(); i++) {
                Node node = nodeLog.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    fNameLog = element.getElementsByTagName("fileName").item(0).getTextContent();
                    String enablLog = element.getElementsByTagName("enabled").item(0).getTextContent();
                    enabledLog = Boolean.valueOf(enablLog);
                }
            }
        }

        System.out.println("Список возможных товаров для покупки");
        for (
                int i = 0;
                i < products.length; i++) {
            System.out.println(i + 1 + ". " + products[i]);
        }

        while (true) {
            int productNumber = 0; //номер продукта, введенный пользователем
            int productCount = 0; // кол-во, введенное пользователем

            System.out.println("Выберите товар и количество или введите `end`");
            String input = scanner.nextLine();
            if (input.equals("end")) {
                break;
            }
            String[] parts = input.split(" "); // 1 4
            productNumber = Integer.parseInt(parts[0]) - 1; //0
            productCount = Integer.parseInt(parts[1]); //3
            clientLog.log(productNumber + 1, productCount);
            basket.addToCart(productNumber, productCount);
        }
        if (enabledLog == true) {
            clientLog.exportAsCSV(new File(fNameLog));
        }
        basket.printCart();

        if (enabledSave) {
            if (formatFileSave.equals("json")) {
                basket.saveJson(new File(fNameSave));
            } else {
                basket.saveTxt(new File(fNameSave));
            }
        }
    }
}
