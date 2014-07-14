package mhyhre.lightrabbit.game.levels.events;

public enum EventType {

    NONE,

    UNIT_ADD, UNIT_KILL, UNIT_REMOVE, UNIT_HEALTH, UNIT_SPEED,
    UNIT_SET_IDEOLOGY, UNIT_SET_STOP_POSITION,

    MSSG_SHOW,
    
    STOP_TIME, STOP_TIME_IN, START_TIME, SET_TIME, SET_TIME_SPEED,

    SET_WATER_LEVEL,
    SET_WAVE_HEIGHT, 
    SET_WEATHER, 
    
    SET_FOG_VISIBLE,
    
    WAIT_SECONDS, 
    WAIT_ENEMIES_EXIST,
    WAIT_FOREVER,
}