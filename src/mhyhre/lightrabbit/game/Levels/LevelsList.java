package mhyhre.lightrabbit.game.Levels;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import mhyhre.lightrabbit.MainActivity;

import android.annotation.SuppressLint;
import android.util.Log;

@SuppressLint("UseSparseArrays")
public class LevelsList {
	
	public class LevelListItem{
		public String filename;
		public int pageId;
	}
	
	List<LevelListItem> levelsList;
	HashMap<Integer, String> pagesCaption;
	
	public LevelsList(String listFilename) {
		
		levelsList = new ArrayList<LevelListItem>();
		pagesCaption = new HashMap<Integer, String>();
		
		try {
			InputStream is = MainActivity.Me.getAssets().open(listFilename);

			parseXml(is);

		} catch (IOException e) {
			Log.e(MainActivity.DEBUG_ID, "Level list loading: " + e);
			
		}

	}
	
	private void parseXml(InputStream is){
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document dom = builder.parse(is);
            
            Element root = dom.getDocumentElement();
            
            Log.i(MainActivity.DEBUG_ID, "Start parsing");
            
            
            // Load pages list
            NodeList pages = root.getElementsByTagName("page");
            
            Log.i(MainActivity.DEBUG_ID, "Pages count:" + pages.getLength());
            
            for (int i=0;i<pages.getLength();i++){

                Node pageNode = pages.item(i);

                String pageCaption = pageNode.getAttributes().getNamedItem("text").getNodeValue();
                int pageId = Integer.parseInt(pageNode.getAttributes().getNamedItem("id").getNodeValue());
                
                pagesCaption.put(pageId, pageCaption);
            }
            
            // Load levels list
            NodeList items = root.getElementsByTagName("level");
            Log.i(MainActivity.DEBUG_ID, "Levels count:" + items.getLength());
            
            
            for (int i=0;i<items.getLength();i++){

                Node levelNode = items.item(i);
                
                LevelListItem levelItem = new LevelListItem();
                levelItem.filename = levelNode.getTextContent();
                levelItem.pageId = Integer.parseInt(levelNode.getAttributes().getNamedItem("pageId").getNodeValue());
                levelsList.add(levelItem);
            }
        } catch (Exception e) {
        	Log.e(MainActivity.DEBUG_ID, "Level list - parseXml: " + e);
        } 
		
	}
	

	public int getLevelsCount() {
		return levelsList.size();
	}

	public LevelListItem getLevel(int i) {
		if (i >= 0 && i < levelsList.size()) {
			return levelsList.get(i);
		}
		return null;
	}
	
	public String getPageCaption(int id){
		if (pagesCaption.containsKey(id)){
			return pagesCaption.get(id);
		}
		Log.e(MainActivity.DEBUG_ID, "LevelsList::getPageCaption: invalid id");
		return null;
	}

	public void printList() {
		
		Log.i(MainActivity.DEBUG_ID, "Levels list:");
		for (LevelListItem item : levelsList) {
			Log.i(MainActivity.DEBUG_ID, "level: " + item.filename + "\t from page: " + this.getPageCaption(item.pageId));
		}
	}
}
