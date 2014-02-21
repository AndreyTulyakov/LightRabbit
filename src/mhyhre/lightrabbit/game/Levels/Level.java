package mhyhre.lightrabbit.game.levels;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import mhyhre.lightrabbit.MainActivity;

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

    private static final String LEVEL_CHAPTER = "Chapter";
    private static final String LEVEL_NAME = "Name";
    private static final String LEVEL_BACKSTORY = "Backstory";
    private static final String LEVEL_DIALOGBASE = "DialogBase";

    private String mName = "";
    private String mChapter = "";
    private String mStory = "";
    private String mDialogBaseFilename = "";

    private List<Event> events;
    private int currentEventIndex = -1;

    DialogBase dialogBase;
    CharacterBase characterBase;
    
    String playerType = "None";

    public Level(String filename) {

        events = new ArrayList<Event>();

        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            InputStream is = MainActivity.Me.getAssets().open(MainActivity.LEVELS_FOLDER + filename);
            DocumentBuilder docBuilder = dbf.newDocumentBuilder();
            doc = docBuilder.parse(is);
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

    public DialogBase getDialogBase() {
        return dialogBase;
    }

    public CharacterBase getCharacterBase() {
        return characterBase;
    }

    private void LoadFromXml(Element rootElement) {

        loadHeaderFromXML(rootElement);
        loadEventsFromXML(rootElement);
    }

    private void loadHeaderFromXML(Element rootElement) {

        mChapter = getString(LEVEL_CHAPTER, rootElement);
        mName = getString(LEVEL_NAME, rootElement);
        mStory = getString(LEVEL_BACKSTORY, rootElement);
        mDialogBaseFilename = getString(LEVEL_DIALOGBASE, rootElement);
    }

    private void loadEventsFromXML(Element rootElement) {
        NodeList items = rootElement.getElementsByTagName(EVENT);

        for (int i = 0; i < items.getLength(); i++) {

            Element element = (Element) items.item(i);

            Event event = new Event();
            event.setType(element.getAttribute(COMMAND));
            event.setId(Integer.parseInt(element.getAttribute(ID)));
            event.setIntArg(Integer.parseInt(element.getAttribute(INT_ARG)));
            event.setStrArg(element.getAttribute(STRING_ARG));

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

    public String getBackstory() {
        return mStory;
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

    public String getPlayerType() {
        return playerType;
    }

}
