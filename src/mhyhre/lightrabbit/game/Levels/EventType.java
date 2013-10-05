package mhyhre.lightrabbit.game.Levels;

public enum EventType {
	
	NONE("None"),
	
	UNIT_ADD("Unit Add"),
	UNIT_KILL("Unit Kill"),
	UNIT_REMOVE("Unit Remove"),
	UNIT_HEALTH("Unit Health"),
	UNIT_SPEED("Unit Speed"),
	
	MSSG_SHOW("Mssg Show"),
	
	GAME_WATER_LEVEL("Game WaterLevel"),
	GAME_WAVE_HEIGHT("Game WaveHeight"),
	GAME_STOP_TIME("Game StopTime"),
	GAME_START_TIME("Game StartTime"),
	GAME_TIME("Game Time"),
	GAME_WEATHER("Game Weather"),
	GAME_SHOW_FOG("Game ShowFog"),
	GAME_WAIT_SECONDS("Game WaitSeconds"),
	GAME_WAIT_ENEMIES_EXIST("Game WaitWhileEnemiesExist");
	
	
    private String value;

    EventType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}