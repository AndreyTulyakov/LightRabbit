/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.levels;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.FogRect;
import mhyhre.lightrabbit.game.levels.events.Event;

import org.andengine.opengl.texture.region.TextureRegion;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.util.Log;

public class Level {

    private static final String EVENT = "Event";
    private static final String COMMAND = "command";
    private static final String ID = "id";
    private static final String INT_ARG = "arg_int";
    private static final String STRING_ARG = "arg_str";
    private static final String STRING_SECOND_ARG = "arg_str2";

    private static final String LEVEL_CHAPTER = "Chapter";
    private static final String LEVEL_NAME = "Name";
    private static final String LEVEL_DIALOGBASE = "DialogBase";
    private static final String LEVEL_FOG = "Fog";
    
    private String mName = "";
    private String mChapter = "";
    private String mDialogBaseFilename = "";

    private List<Event> events;
    private int currentEventIndex = -1;

    private DialogBase dialogBase;
    private CharacterBase characterBase;
    private Map<String, TextureRegion> picturesRegions;
    private FogRect fogRect;
    

    public Level(String filename) {

        events = new ArrayList<Event>();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            InputStream is = MainActivity.Me.getAssets().open(MainActivity.LEVELS_FOLDER + filename);
            DocumentBuilder docBuilder = dbf.newDocumentBuilder();
            Document doc = docBuilder.parse(is);
            Element root = doc.getDocumentElement();
            root.normalize();
            LoadFromXml(root);

        } catch (IOException e) {
            Log.e(MainActivity.DEBUG_ID, "Level::Level:" + e.getMessage());
            
        } catch (ParserConfigurationException e) {
            Log.e(MainActivity.DEBUG_ID, "Level::Level:" + e.getMessage());
            
        } catch (SAXException e) {
            Log.e(MainActivity.DEBUG_ID, "Level::Level:" + e.getMessage());
        }

        if (events.size() > 0) {
            currentEventIndex = 0;
        }

        dialogBase = new DialogBase(mDialogBaseFilename);
        characterBase = new CharacterBase(dialogBase.getCharacterBaseFilename());
    }
    
    public Map<String, TextureRegion> getPicturesRegions() {
        return picturesRegions;
    }

    public DialogBase getDialogBase() {
        return dialogBase;
    }

    public CharacterBase getCharacterBase() {
        return characterBase;
    }

    private void LoadFromXml(Element rootElement) {

        loadHeaderFromXML(rootElement);
        loadFogConfigFromXML(rootElement);
        loadEventsFromXML(rootElement);
    }

    private void loadFogConfigFromXML(Element rootElement) {
        
        NodeList items = rootElement.getElementsByTagName(LEVEL_FOG);
        if(items.getLength() > 0) {
            Element element = (Element) items.item(0);
            
            fogRect = new FogRect(
                    Integer.parseInt(element.getAttribute("colorR")),
                    Integer.parseInt(element.getAttribute("colorG")),
                    Integer.parseInt(element.getAttribute("colorB")),
                    Integer.parseInt(element.getAttribute("colorA")));
        } else {
            fogRect = new FogRect(0, 0, 0, 0);
        }
        
    }

    private void loadHeaderFromXML(Element rootElement) {

        mChapter = getString(LEVEL_CHAPTER, rootElement);
        mName = getString(LEVEL_NAME, rootElement);
        mDialogBaseFilename = getString(LEVEL_DIALOGBASE, rootElement);
    }

    private void loadEventsFromXML(Element rootElement) {
        NodeList items = rootElement.getElementsByTagName(EVENT);

        for (int i = 0; i < items.getLength(); i++) {

            Element element = (Element) items.item(i);
            
            Event event = new Event();
            event.setType(element.getAttribute(COMMAND));
            
            try {
                event.setId(Integer.parseInt(element.getAttribute(ID)));
            } catch (NumberFormatException e) {
                event.setId(-1);
            }
            
            try {
                event.setIntArg(Integer.parseInt(element.getAttribute(INT_ARG)));
            } catch (NumberFormatException e) {
                event.setIntArg(0);
            }
            
            event.setStrArg(element.getAttribute(STRING_ARG));
            event.setSecondStringArg(element.getAttribute(STRING_SECOND_ARG));            
            
            events.add(event);
        }
    }
    

    public static String getString(String tagName, Element element) {
        NodeList list = element.getElementsByTagName(tagName);
        if (list != null && list.getLength() > 0) {
            NodeList subList = list.item(0).getChildNodes();

            if (subList != null && subList.getLength() > 0) {
                return subList.item(0).getNodeValue();
            }
        }

        return null;
    }

    public String getName() {
        return mName;
    }

    public String getChapter() {
        return mChapter;
    }

    public void nextEvent() {
        currentEventIndex++;
    }

    public List<Event> getEventsList() {
        return events;
    }

    public Event getCurrentEvent() {

        if (currentEventIndex >= 0 && currentEventIndex < events.size()) {
            return events.get(currentEventIndex);
        }
        return null;
    }

    public int getCurrentEventIndex() {
        return currentEventIndex;
    }

    public String getDialogBaseFilename() {
        return mDialogBaseFilename;
    }

    public FogRect getConfiguredFog() {
        return fogRect;
    }

}
