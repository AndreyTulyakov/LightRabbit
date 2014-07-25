package mhyhre.lightrabbit.game.units.agents;

import android.util.Log;
import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.units.NonPlayerUnitState;
import mhyhre.lightrabbit.game.units.Unit;
import mhyhre.lightrabbit.game.units.controller.NPCController;
import mhyhre.lightrabbit.game.units.models.UnitModel;

public class UnitStopAgent extends UnitAgent {
    
    private int position;
    private float epselon;

    public UnitStopAgent(Unit unit, int position, float epselon) {
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
                
                if(state == NonPlayerUnitState.ATTACK_AND_MOVE) {
                    controller.setState(NonPlayerUnitState.ATTACK);
                } else if(state == NonPlayerUnitState.MOVE) {
                    controller.setState(NonPlayerUnitState.NONE);
                }
            } else {
                Log.w(MainActivity.DEBUG_ID, "UnitStopAgent: deactivated, but not executed");
            }
            
            deactivate();
        }

    }
}
