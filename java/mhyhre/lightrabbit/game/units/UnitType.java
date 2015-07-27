/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.units;

public enum UnitType {
    UNDEFINED(""),
    
    STEAM_SHIP("SteamShip"),
    STEAM_SHIP_GHOST("SteamShipGhost"),
    NOVA_STEAM_SHIP("NovaSteamShip"),
    
    SHARK("Shark"),
    
    MARINE_BOMB("MarineBomb"),
    MARINE_BOMB_BANG("bullet150_boom"),
    
    PIRATE_BOAT("PirateBoat"),
    PIRATE_SHIP("PirateShip"),
    PIRATE_GHOST_SHIP("PirateGhostShip"),
    PIRATE_AIR("PirateAir"),
    
    IMPERIAL_BOAT_UNIT("ImperialBoat"),
    IMPERIAL_POLICE_UNIT("ImperialPoliceShip"),
    IMPERIAL_DIRIGIBLE("ImperialDirigible"),
    IMPERIAL_BIG_SHIP("ImperialBigShip"),
    IMPERIAL_AIR_TYPE_A("ImperialAirTypeA"),
    IMPERIAL_HELICOPTER("ImperialHelicopter"),
    
    INDUSTRIAL_SHIP("IndustrialShip"),
    BARGE("Barge"),
    MERCHANT_SHIP("MerchantShip"),

    WAR_PLANE("WarPlane"),
    ZOMBIE_PLANE("ZombiePlane");

    
    private final String name;
    
    private UnitType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public static UnitType getByName(String name) {
        
        for(UnitType type: values()) {
            if(type.getName().equals(name)) {
                return type;
            }
        }
        return UNDEFINED;
    }
}
