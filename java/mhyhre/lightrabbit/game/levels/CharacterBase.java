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

public class CharacterBase {

    private static final String CHARACTER = "character";
    private static final String ID = "id";
    private static final String ICON = "icon";
    private static final String NAME = "name";

    Map<Integer, Character> characters;

    @SuppressLint("UseSparseArrays")
    public CharacterBase(String filename) {

        characters = new HashMap<Integer, Character>();

        try {
            InputStream is = MainActivity.Me.getAssets().open(MainActivity.LEVELS_FOLDER + filename);

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbf.newDocumentBuilder();

            Document doc = docBuilder.parse(is);

            Element root = doc.getDocumentElement();
            root.normalize();

            parseXml(root);
            Log.i(MainActivity.DEBUG_ID, "Character base " + filename + " loaded and contain " + characters.size());
            
        } catch (IOException e) {
            Log.w(MainActivity.DEBUG_ID, "CharacterBase IO:" + e);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();

        } catch (SAXException e) {
            e.printStackTrace();

        }

    }

    private void parseXml(Element rootElement) {

        NodeList items = rootElement.getElementsByTagName(CHARACTER);

        for (int i = 0; i < items.getLength(); i++) {

            Element element = (Element) items.item(i);
            int id = Integer.parseInt(element.getAttribute(ID));

            Character character = new Character(element.getAttribute(NAME), element.getAttribute(ICON));

            characters.put(id, character);
        }
    }
    
    public Character getCharacter(int id) {
        return characters.get(id);
    }

}
