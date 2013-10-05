package mhyhre.lightrabbit.game.Levels;

public class Event {

	private EventType mType;
	private int mId;
	private int mIntArg;
	String mStrArg;
	
	public Event() {
		mType = EventType.NONE; 
		mId = -1;
		mIntArg = 0;
		mStrArg = "";
	}
	
	public EventType getType() {
		return mType;
	}
	
	public int getId() {
		return mId;
	}

	public String getStrArg() {
		return mStrArg;
	}

	public int getIntArg() {
		return mIntArg;
	}

	public void setIntArg(int arg) {
		mIntArg = arg;
	}
	
	public void setId(int arg) {
		mId = arg;
	}

	public void setStrArg(String arg) {
		mStrArg = arg;
	}
	
}
