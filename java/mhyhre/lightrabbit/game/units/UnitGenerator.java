/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.units;

import android.util.Log;
import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.units.controller.NPCController;
import mhyhre.lightrabbit.game.units.controller.StandartController;
import mhyhre.lightrabbit.game.units.controller.UnitController;
import mhyhre.lightrabbit.game.units.models.BargeUnit;
import mhyhre.lightrabbit.game.units.models.ImperialAirTypeA;
import mhyhre.lightrabbit.game.units.models.ImperialBigShip;
import mhyhre.lightrabbit.game.units.models.ImperialBoatUnit;
import mhyhre.lightrabbit.game.units.models.ImperialDirigibleUnit;
import mhyhre.lightrabbit.game.units.models.ImperialHelicopter;
import mhyhre.lightrabbit.game.units.models.IndustrialShipUnit;
import mhyhre.lightrabbit.game.units.models.MarineBomb;
import mhyhre.lightrabbit.game.units.models.MerchantShip;
import mhyhre.lightrabbit.game.units.models.NovaSteamShip;
import mhyhre.lightrabbit.game.units.models.PirateAirUnit;
import mhyhre.lightrabbit.game.units.models.PirateBoatUnit;
import mhyhre.lightrabbit.game.units.models.PirateShipUnit;
import mhyhre.lightrabbit.game.units.models.ImperialPoliceShipUnit;
import mhyhre.lightrabbit.game.units.models.SharkUnit;
import mhyhre.lightrabbit.game.units.models.SteamShip;
import mhyhre.lightrabbit.game.units.models.SteamShipGhost;
import mhyhre.lightrabbit.game.units.models.UnitModel;


public class UnitGenerator {

    public static Unit generate(boolean isPlayer, String typeName) {
        
        UnitType type = UnitType.getByName(typeName); 
        return generate(isPlayer, type, 0);
    }
    
    public static Unit generate(boolean isPlayer, UnitType type, int id) {
        
        UnitModel model = null;
        UnitController controller = null;
        
        switch (type) {
        
        case STEAM_SHIP:
            model = new SteamShip(id);
            break;
            
        case NOVA_STEAM_SHIP:
            model = new NovaSteamShip(id);
            break;
                        
        case SHARK:           
            model = new SharkUnit(id);                
            break;

        case IMPERIAL_DIRIGIBLE:
            model = new ImperialDirigibleUnit(id);                 
            break;
            
        case IMPERIAL_POLICE_UNIT:
            model = new ImperialPoliceShipUnit(id);
            break;
            
        case IMPERIAL_BOAT_UNIT:
            model = new ImperialBoatUnit(id);
            break;
            
        case IMPERIAL_BIG_SHIP:
            model = new ImperialBigShip(id);
            break;
            
        case IMPERIAL_AIR_TYPE_A:
            model = new ImperialAirTypeA(id);
            break;
            
        case IMPERIAL_HELICOPTER:
            model = new ImperialHelicopter(id);
            break;

        case PIRATE_SHIP:
            model = new PirateShipUnit(id);                
            break;

        case PIRATE_BOAT:
            model = new PirateBoatUnit(id);
            break;
            
        case MERCHANT_SHIP:
            model = new MerchantShip(id);
            break;

        case BARGE:
            model = new BargeUnit(id);
            break;
            
        case INDUSTRIAL_SHIP:
            model = new IndustrialShipUnit(id);
            break;
            
        case PIRATE_AIR:
            model = new PirateAirUnit(id);
            break;
            
        case STEAM_SHIP_GHOST:
            model = new SteamShipGhost(id);
            break;
            
        case MARINE_BOMB:
            model = new MarineBomb(id);

        default:
            break;
        }
        
        if(model == null) {
            Log.e(MainActivity.DEBUG_ID, "UnitGenerator: Unknown model of <" + type.getName() + "> is null");
        }
        
        if(isPlayer) {
            controller = new StandartController(model);
            model.setMoveDirection(UnitMoveDirection.RIGHT);
        } else {
            controller = new NPCController();
        }
        
        Unit unit = new Unit(model, controller, null);
        
        return unit;
    }
}
