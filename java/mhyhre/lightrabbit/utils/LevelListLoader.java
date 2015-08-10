/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.util.Log;
import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.levels.LevelsPage;


public class LevelListLoader {

    private static final String XML_NAME_PAGE = "Page";
    private static final String XML_NAME_LEVEL = "level";
    public static List<LevelsPage> load() {

        String levelListFilename = "levels_" + MainActivity.LOCALIZATION + "/LevelsList.xml";
        return load(levelListFilename);
    }
    
    public static List<LevelsPage> load(String filename) {
        
        List<LevelsPage> pages = new ArrayList<LevelsPage>();
        
        try {
            InputStream is = MainActivity.Me.getAssets().open(filename);
            parseLevelListXml(is, pages);

        } catch (IOException e) {
            Log.e(MainActivity.DEBUG_ID, "LevelListLoader: Level list loading: " + e);

        }
        return pages;
    }
    
    public static void parseLevelListXml(InputStream is, List<LevelsPage> pages) {

        if(is == null || pages == null) {
            return;
        }
        
        Log.i(MainActivity.DEBUG_ID, "LevelListLoader::parseLevelListXml:");
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document dom = builder.parse(is);

            Element root = dom.getDocumentElement();

            NodeList pageItems = root.getElementsByTagName(XML_NAME_PAGE);
            Log.i(MainActivity.DEBUG_ID, "Pages count:" + pageItems.getLength());

            
            // Parsing pages
            for (int i = 0; i < pageItems.getLength(); i++) {

                Node pageNode = pageItems.item(i);

                String pageCaption = pageNode.getAttributes().getNamedItem("caption").getNodeValue();
                int pageId = Integer.parseInt(pageNode.getAttributes().getNamedItem("id").getNodeValue());

                LevelsPage page = new LevelsPage(pageId, pageCaption);

                // parsing levels
                NodeList levels = ((Element) pageNode).getElementsByTagName(XML_NAME_LEVEL);

                Log.i(MainActivity.DEBUG_ID, "On Page:" + page.getCaption() + " levels count:" + levels.getLength());

                for (int j = 0; j < levels.getLength(); j++) {
                    
                    Node levelNode = levels.item(j);
                    String filename = levelNode.getTextContent();
                    String label = levelNode.getAttributes().getNamedItem("label").getNodeValue();
                    
                    int id = 0;
                    try {
                        id = Integer.parseInt(levelNode.getAttributes().getNamedItem("id").getNodeValue());
                    } catch(NumberFormatException e) {
                        Log.w(MainActivity.DEBUG_ID, "LevelListLoader::parseLevelListXml: wrong id:" + label);
                    }
                    
                    page.addLevelItem(id, label, filename);
                }

                pages.add(page);
            }

            Log.i(MainActivity.DEBUG_ID, "LevelListLoader: Good parsing!");

        } catch (Exception e) {
            Log.e(MainActivity.DEBUG_ID, "LevelListLoader: Level list - parseXml: " + e);
        }
    }
}
