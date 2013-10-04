package mhyhre.lightrabbit.game.events;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import mhyhre.lightrabbit.MainActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class EventsLoader {
	
	private List<Event> events;
	
	

	public EventsLoader(String filename){
		
		events = new ArrayList<Event>();
		
		XmlPullParserFactory pullParserFactory;

		try {
			pullParserFactory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = pullParserFactory.newPullParser();

			    InputStream in_s = MainActivity.Me.getAssetManager().open(filename);
		        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
	            parser.setInput(in_s, null);

	            //parseXML(parser);

		} catch (XmlPullParserException e) {

			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	
	public List<Event> getEventsList(){
		return events;
	}

	
}
