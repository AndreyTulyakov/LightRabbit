package mhyhre.lightrabbit.game.levels.events;


public class Event {

    private EventType type;
    private int id;
    private int integerArg;
    String stringArg;
    String secondStringArg;

    public Event() {
        type = EventType.NONE;
        id = -1;
        integerArg = 0;
        stringArg = "";
        secondStringArg = "";
    }

    public void setType(String typeName) {

        try {
            type = EventType.valueOf(typeName);

        } catch (IllegalArgumentException e) {
            type = EventType.NONE;
        }
    }

    public EventType getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public String getStringArg() {
        return stringArg;
    }

    public int getIntegerArg() {
        return integerArg;
    }

    public void setIntArg(int arg) {
        integerArg = arg;
    }

    public void setId(int arg) {
        id = arg;
    }

    public void setStrArg(String arg) {
        stringArg = arg;
    }

    public String getSecondStringArg() {
        return secondStringArg;
    }

    public void setSecondStringArg(String secondStringArg) {
        this.secondStringArg = secondStringArg;
    }

    @Override
    public String toString() {
        return "Event [Type=" + type + ", Id=" + id + ", IntegerArg=" + integerArg + ", StringArg=" + stringArg + "]";
    }
    
    

}
