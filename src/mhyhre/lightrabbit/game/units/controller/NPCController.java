package mhyhre.lightrabbit.game.units.controller;

import mhyhre.lightrabbit.game.units.UnitMoveDirection;
import mhyhre.lightrabbit.game.units.models.UnitModel;

public class NPCController extends UnitController {

    public NPCController(UnitModel inModel) {
        super(inModel);
        
    }

    @Override
    public void accelerate(UnitMoveDirection moveDirection) {
        model.moveHorizontalByDirection();
    }

    @Override
    public void jump() {
        // TODO Auto-generated method stub

    }

    @Override
    public void fireByGun(int gunIndex) {
        // TODO Auto-generated method stub

    }

}
