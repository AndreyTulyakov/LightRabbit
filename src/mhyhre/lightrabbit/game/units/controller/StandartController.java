package mhyhre.lightrabbit.game.units.controller;

import mhyhre.lightrabbit.game.units.UnitMoveDirection;
import mhyhre.lightrabbit.game.units.models.UnitModel;

public class StandartController extends UnitController {

    
    public StandartController(UnitModel inModel) {
        super(inModel);
    }

    @Override
    public void accelerate(UnitMoveDirection moveDirection) {
        model.setSpeed(model.getSpeed() + moveDirection.getDirect() * model.getMoveAcceleration());   
    }

    @Override
    public void jump() {
        model.jump();
    }

    @Override
    public void fireByGun(int gunIndex) {
        model.fireByGun(gunIndex);
    }

}
