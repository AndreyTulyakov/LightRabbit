package mhyhre.lightrabbit.game.Levels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Level {

	private String mCaption;
	private String mDescription;
	private String mStory;
	
	private ArrayList<Event> events;
	private Map<Integer,Actor> actors;
	private Map<Integer,Dialog> dialogs;
	
	Level()
	{	
		events = new ArrayList<Event>();
		actors = new HashMap<Integer, Actor>();
		dialogs = new HashMap<Integer, Dialog>();
	}

	public String getCaption() {
		return mCaption;
	}

	public String getDescription() {
		return mDescription;
	}

	public String getStory() {
		return mStory;
	}

	public ArrayList<Event> getEventsList() {
		return events;
	}

	public Map<Integer, Actor> getActorsMap() {
		return actors;
	}

	public Map<Integer, Dialog> getDialogsMap() {
		return dialogs;
	}

	public void setCaption(String mCaption) {
		this.mCaption = mCaption;
	}

	public void setDescription(String mDescription) {
		this.mDescription = mDescription;
	}

	public void setStory(String mStory) {
		this.mStory = mStory;
	}
	
}
