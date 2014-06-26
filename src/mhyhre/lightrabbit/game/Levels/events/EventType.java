package mhyhre.lightrabbit.game.levels.events;

public enum EventType {

    NONE,

    UNIT_ADD, UNIT_KILL, UNIT_REMOVE, UNIT_HEALTH, UNIT_SPEED,

    MSSG_SHOW,
    
    STOP_TIME, STOP_TIME_IN, START_TIME, SET_TIME, SET_TIME_SPEED,

    SET_WATER_LEVEL,
    SET_WAVE_HEIGHT, 
    SET_WEATHER, 
    
    SHOW_FOG, SET_FOG_VALUE,
    
    WAIT_SECONDS, 
    WAIT_ENEMIES_EXIST,
    WAIT_ALWAYS,
}