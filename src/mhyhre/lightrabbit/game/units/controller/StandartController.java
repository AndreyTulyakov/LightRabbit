package mhyhre.lightrabbit.game.units.controller;

import android.util.Log;
import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.units.UnitMoveDirection;
import mhyhre.lightrabbit.game.units.models.UnitModel;

public class StandartController extends UnitController {

    
    public StandartController(UnitModel inModel) {
        super(inModel);
    }

    @Override
    public void accelerate(UnitMoveDirection moveDirection) {
    

        
        // Right-Left borders
        if (model.getX() > (MainActivity.getWidth() - 20) && model.getSpeed() > 0) {
            model.setSpeed(0);
        }

        if (model.getX() < 20 && model.getSpeed() < 0) {
            model.setSpeed(0);
        }

        model.setX(model.getX() + model.getMoveAcceleration() * moveDirection.getDirect());
    }

    @Override
    public void jump() {   
        if(model.isCanJump()) {
            model.setCanJump(false);
            model.setJumpAcceletation(UnitModel.JUMP_ACCELERATION_LIMIT);
        } 
    }

    @Override
    public void fireByGun(int gunIndex) {
        model.fireByGun(gunIndex);
    }

}
