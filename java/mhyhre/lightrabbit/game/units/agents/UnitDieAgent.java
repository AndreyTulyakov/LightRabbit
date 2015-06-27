/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.units.agents;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.units.Unit;
import mhyhre.lightrabbit.game.units.models.UnitModel;
import android.util.Log;

public class UnitDieAgent extends UnitAgent {
    
    private int position;
    private float epselon;

    public UnitDieAgent(Unit unit, int position, float epselon) {
        super(unit);
        this.position = position;
        this.epselon = epselon;
        
        if(unit.getController().isPlayerController()) {
            Log.w(MainActivity.DEBUG_ID, "UnitStopAgent: NPC unit needed.");
        }
    }

    @Override
    public void update() {       
        
        if(Math.abs(unit.getModel().getX()-position) < epselon) {
            
            // If NPC unit
            if(unit.getController().isPlayerController() == false) {
                UnitModel model = unit.getModel();
                model.setHealth(0);
                model.setDied(true);
            } else {
                Log.w(MainActivity.DEBUG_ID, "UnitDieAgent: deactivated, but not executed");
            }
            deactivate();
        }

    }
}
