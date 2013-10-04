package mhyhre.lightrabbit.game.events;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import mhyhre.lightrabbit.MainActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class EventManager {

	List<Event> events = null;
	
	public EventManager() {
		events = new LinkedList<Event>();
	}
	
	public void loadEvents(int number){
		events.clear();
		
		if(number >= 0){
			String filename = "maps/map" + number + ".lrmap";
			loadFromXmlFile(filename);
		}

	}
	
	public void clearEventList(){

		events.clear();
	}
	
	// use after current event complete
	
	public boolean nextEvent(){
		
		if(events.size() > 0){
			events.remove(0);
		}
		
		if(events.size() > 0){
			return true;
		}
		
		return false;
	}
	
	public int getUncompleteEventsCount(){
		return events.size();
	}
	
	public Event getCurrentEvent(){
		
		if(events.size() > 0){
			return events.get(0);
		}

		return null;
	}
	
	public List<Event> getEventsList(){
		return events;
	}
	
	public void loadFromXmlFile(String filename){
		
		XmlPullParserFactory pullParserFactory;

		try {
			pullParserFactory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = pullParserFactory.newPullParser();

			    InputStream in_s = MainActivity.Me.getAssetManager().open( filename );
		        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
	            parser.setInput(in_s, null);

	            parseXML(parser);

		} catch (XmlPullParserException e) {

			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		

	}
	

	private void parseXML(XmlPullParser parser) throws XmlPullParserException,IOException
	{
		
        int eventType = parser.getEventType();
        Event currentEvent = null;

        while (eventType != XmlPullParser.END_DOCUMENT){
        	
            String name = null;
            
            
            /*
            switch (eventType){
            
                case XmlPullParser.START_DOCUMENT:
                	
                    break;
                    
                case XmlPullParser.START_TAG:
                	
                	
                    name = parser.getName();
                    
                    if (name.equals(new String("triger"))){
                    	
                        currentEvent = new Event();
                        String id = parser.getAttributeValue(null, "id");
                        currentEvent.setType(id);

                    } else if (currentEvent != null){
                        if (name.equals(new String("time"))){
                            currentEvent.setStartTime( Integer.parseInt(parser.nextText()));
                            break;
                        }
                        
                        if (name.equals(new String("position"))){
                        	currentEvent.setPosition( Float.parseFloat(parser.nextText()), 0);
                        	break;
                        }
 
                    }
                    break;
                    
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if (name.equalsIgnoreCase("triger") && currentEvent != null){
                    	events.add(currentEvent);
                    } 
            }
            */
            eventType = parser.next();
        }

	}

}