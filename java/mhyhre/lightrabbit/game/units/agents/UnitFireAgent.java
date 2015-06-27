/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.units.agents;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.units.NonPlayerUnitState;
import mhyhre.lightrabbit.game.units.Unit;
import mhyhre.lightrabbit.game.units.controller.NPCController;
import android.util.Log;

public class UnitFireAgent extends UnitAgent {
    
    private int position;
    private float epselon;

    public UnitFireAgent(Unit unit, int position, float epselon) {
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
                NPCController controller =  (NPCController)unit.getController();
                NonPlayerUnitState state = controller.getState();
                
                if(state == NonPlayerUnitState.NONE) {
                    controller.setState(NonPlayerUnitState.ATTACK);
                } else if(state == NonPlayerUnitState.MOVE) {
                    controller.setState(NonPlayerUnitState.ATTACK_AND_MOVE);
                }
            } else {
                Log.w(MainActivity.DEBUG_ID, "UnitStopAgent: deactivated, but not executed");
            }
            deactivate();
        }

    }
}
