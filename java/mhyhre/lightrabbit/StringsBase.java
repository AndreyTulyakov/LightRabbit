package mhyhre.lightrabbit;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class StringsBase {

    private static final String XML_STRING_TAG = "string";
    private static final String XML_STRING_NAME = "name";

    private static StringsBase ourInstance = null;

    private HashMap<String, String> strings;

    public static StringsBase getInstance() {
        if(ourInstance == null) {
            ourInstance = new StringsBase();
        }
        return ourInstance;
    }

    private StringsBase() {

        strings = new HashMap<String, String>();

        try {
            InputStream inputStream = MainActivity.Me.getAssets().open(MainActivity.LOCATE_STRINGS_FILENAME);
            parseXml(inputStream);

        } catch (IOException e) {
            Log.e(MainActivity.DEBUG_ID, "LevelListLoader: Level list loading: " + e);
        }
    }

    private void parseXml(InputStream inputStream) {

        if(inputStream == null || strings == null) {
            return;
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document dom = builder.parse(inputStream);

            Element root = dom.getDocumentElement();

            NodeList stringsItems = root.getElementsByTagName(XML_STRING_TAG);

            for (int i = 0; i < stringsItems.getLength(); i++) {
                Node pageNode = stringsItems.item(i);
                String itemName = pageNode.getAttributes().getNamedItem(XML_STRING_NAME).getNodeValue();
                String itemText = pageNode.getTextContent();
                strings.put(itemName, itemText);
            }

        } catch (Exception e) {
            Log.e(MainActivity.DEBUG_ID, "StringsBase: parsing error: " + e);
        }
    }

    public String getValue(String key) {
        if(strings.containsKey(key)) {
            return strings.get(key);
        } else {
            Log.e(MainActivity.DEBUG_ID, "Locale string with key <" + key + "> does not exist.");
            return "NONE";
        }
    }

}
