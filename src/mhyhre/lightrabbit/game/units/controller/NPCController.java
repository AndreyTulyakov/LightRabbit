package mhyhre.lightrabbit.game.units.controller;

import android.util.Log;
import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.levels.events.Event;
import mhyhre.lightrabbit.game.levels.events.EventType;
import mhyhre.lightrabbit.game.units.NonPlayerUnitState;
import mhyhre.lightrabbit.game.units.UnitMoveDirection;
import mhyhre.lightrabbit.game.units.models.UnitModel;

public class NPCController extends UnitController {
    
    NonPlayerUnitState state;
    int currentGunIndex = 0;
    private static final float SPEED_FACTOR = 0.2f;

    public NPCController() {
        super(null);
        currentGunIndex = 0;
        state = NonPlayerUnitState.NONE;
    }
    
    public NPCController(UnitModel inModel, String unitState) {
        super(inModel);
        
        currentGunIndex = 0;
        
        try {
            state = NonPlayerUnitState.valueOf(unitState);
        } catch (IllegalArgumentException e) {
            state = NonPlayerUnitState.NONE;
        }

    }
    
    
    public void setStateByEvent(Event event) {
        
        if(event.getType() == EventType.NPC_CHANGE_STATE) {
            
            try {
                state = NonPlayerUnitState.valueOf(event.getStringArg());
                
                switch(state) {

                case ATTACK:
                    // Need: set weapon index
                    currentGunIndex = event.getIntegerArg();
                    break;
                    
                case ATTACK_AND_MOVE:
                    // Need: set direction and speed;
                    model.setSpeed(event.getIntegerArg() * SPEED_FACTOR);
                    model.setMoveDirection(UnitMoveDirection.getByName(event.getSecondStringArg()));
                    break;
                    
                case MOVE:
                    model.setSpeed(event.getIntegerArg() * SPEED_FACTOR);
                    model.setMoveDirection(UnitMoveDirection.getByName(event.getSecondStringArg()));
                    break;
                    
                case NONE:
                    // None
                    break;
                    
                default:
                    break; 
                
                }
                
                update();
                
            } catch (IllegalArgumentException e) {
                // Do nothing
            }
            
        } else {
            Log.w(MainActivity.DEBUG_ID, "NPC Controller::setStateByEvent: wrong event - " + event.getType().name());
        }
    }
 
    
    @Override
    public void update() 
    {
        
        switch(state) {
            
        case ATTACK:
            
            // Try to aim and fire
            if(aim()) {
                fireByGun(currentGunIndex);
            }         
            break;
            
        case ATTACK_AND_MOVE:
            
            // Try to aim and fire
            if(aim()) {
                fireByGun(currentGunIndex);
            }  
            model.moveHorizontalByDirection();
            
            break;
            
        case MOVE:
            model.moveHorizontalByDirection();
            break;
            
        case NONE:
            // None
            break;
            
        default:
            break;
        
        }
    };
    
    @Override
    public void accelerate(UnitMoveDirection moveDirection) {
        
    }

    @Override
    public void jump() {
        // TODO Auto-generated method stub

    }

    @Override
    public void fireByGun(int gunIndex) {
        // TODO Auto-generated method stub

    }

    private boolean aim() {
        return true;
    }
    
    @Override
    public boolean isPlayerController() {
        return false;
    }

    public NonPlayerUnitState getState() {
        return state;
    }

    public void setState(NonPlayerUnitState state) {
        this.state = state;
    }
}
