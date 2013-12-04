package mhyhre.lightrabbit.game.Levels;

public class Event {

    private EventType mType;
    private int mId;
    private int mIntegerArg;
    String mStringArg;

    public Event() {
        mType = EventType.NONE;
        mId = -1;
        mIntegerArg = 0;
        mStringArg = "";
    }

    public void setType(String typeName) {

        try {
            mType = EventType.valueOf(typeName);

        } catch (IllegalArgumentException e) {
            mType = EventType.NONE;
        }
    }

    public EventType getType() {
        return mType;
    }

    public int getId() {
        return mId;
    }

    public String getStringArg() {
        return mStringArg;
    }

    public int getIntegerArg() {
        return mIntegerArg;
    }

    public void setIntArg(int arg) {
        mIntegerArg = arg;
    }

    public void setId(int arg) {
        mId = arg;
    }

    public void setStrArg(String arg) {
        mStringArg = arg;
    }

    @Override
    public String toString() {
        return "Event [Type=" + mType + ", Id=" + mId + ", IntegerArg=" + mIntegerArg + ", StringArg=" + mStringArg + "]";
    }
    
    

}
