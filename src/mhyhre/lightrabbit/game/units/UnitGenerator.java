package mhyhre.lightrabbit.game.units;

import android.util.Log;
import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.units.controller.NPCController;
import mhyhre.lightrabbit.game.units.controller.StandartController;
import mhyhre.lightrabbit.game.units.controller.UnitController;
import mhyhre.lightrabbit.game.units.models.DirigibleUnit;
import mhyhre.lightrabbit.game.units.models.PirateBoatUnit;
import mhyhre.lightrabbit.game.units.models.PirateShipUnit;
import mhyhre.lightrabbit.game.units.models.SharkUnit;
import mhyhre.lightrabbit.game.units.models.SteamShip;
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
            
        case SHARK:           
            model = new SharkUnit(id);                
            break;

        case DIRIGIBLE:
            model = new DirigibleUnit(id);                 
            break;

        case PIRATE_SHIP:
            model = new PirateShipUnit(id);                
            break;

        case PIRATE_BOAT:
            model = new PirateBoatUnit(id);
            break;

        default:
            break;
        }
        
        if(model == null) {
            Log.e(MainActivity.DEBUG_ID, "Model of <" + type.getName() + "> is null");
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
