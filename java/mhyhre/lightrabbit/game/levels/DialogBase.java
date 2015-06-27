/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.levels;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import mhyhre.lightrabbit.MainActivity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.annotation.SuppressLint;
import android.util.Log;

public class DialogBase {

    private static final String CHARACTER_BASE_FILENAME = "CharacterBase";
    private static final String ID = "id";
    private static final String ACTOR_ID = "actor_id";
    private static final String NEXT_ID = "next_id";
    private static final String DIALOG = "dialog";
    private static final String REPLIC = "replic";

    private Map<Integer, Dialog> dialogs;
    private String characterBaseFilename;

    @SuppressLint("UseSparseArrays")
    public DialogBase(String filename) {
        
        dialogs = new HashMap<Integer, Dialog>();
        
        try {
            InputStream is = MainActivity.Me.getAssets().open(MainActivity.LEVELS_FOLDER + filename);

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbf.newDocumentBuilder();
            Document doc = docBuilder.parse(is);
            Element root = doc.getDocumentElement();
            root.normalize();
            parseXml(root);

        } catch (IOException e) {
            Log.w(MainActivity.DEBUG_ID, "DialogBase IO:" + e);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        Log.i(MainActivity.DEBUG_ID, "Dialog base " + filename + " loaded and contain:" + dialogs.size());
    }

    
    private void parseXml(Element rootElement) {

        characterBaseFilename = Level.getString(CHARACTER_BASE_FILENAME, rootElement);

        NodeList items = rootElement.getElementsByTagName(DIALOG);

        for (int i = 0; i < items.getLength(); i++) {

            Element element = (Element) items.item(i);

            int id = Integer.parseInt(element.getAttribute(ID));

            Dialog dialog = new Dialog();
            parseReplics(dialog, element);
            dialogs.put(id, dialog);
        }
    }

    
    private void parseReplics(Dialog dialog, Element rootElement) {

        NodeList items = rootElement.getElementsByTagName(REPLIC);

        for (int i = 0; i < items.getLength(); i++) {

            Element element = (Element) items.item(i);

            int id = Integer.parseInt(element.getAttribute(ID));

            Replic replic = new Replic();
            replic.setActorId(Integer.parseInt(element.getAttribute(ACTOR_ID)));
            replic.setNextId(Integer.parseInt(element.getAttribute(NEXT_ID)));
            replic.setText(element.getTextContent());

            dialog.putReplic(id, replic);
        }
    }

    public String getCharacterBaseFilename() {
        return characterBaseFilename;
    }

    public Dialog getDialogs(int id) {
        if(dialogs.containsKey(id)) {
            return dialogs.get(id);
        } else {
            return null;
        }
    }

}
