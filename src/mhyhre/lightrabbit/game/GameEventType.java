package mhyhre.lightrabbit.game;

public enum GameEventType {
	
	NONE("None"),
	WAIT("Wait"),
	NEW_SHARK("Shark"),
	NEW_PIRATE_BOAT("Pirate boat"),
	NEW_PIRATE_SHIP("Pirate ship"),
	SET_TIME("Time"),
	SET_WAVE_HEIGHT("Wave height");
	
    private String value;

    GameEventType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}