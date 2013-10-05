package mhyhre.lightrabbit.game.Levels;

import java.util.ArrayList;
import java.util.List;

public class EventsLoader {
	
	private List<Event> events;
	
	

	public EventsLoader(String filename){
		
		events = new ArrayList<Event>();
		
	}
	
	
	public List<Event> getEventsList(){
		return events;
	}

	
}
