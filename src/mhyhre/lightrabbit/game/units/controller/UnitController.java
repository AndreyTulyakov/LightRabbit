package mhyhre.lightrabbit.game.units.controller;

import mhyhre.lightrabbit.game.units.UnitMoveDirection;
import mhyhre.lightrabbit.game.units.models.UnitModel;

public abstract class UnitController {
    
    protected UnitModel model;
    
    public UnitController(UnitModel inModel) {
        this.model = inModel;
    }
    
    //public abstract void update();
    public abstract void accelerate(UnitMoveDirection moveDirection);
    public abstract void jump();
    public abstract void fireByGun(int gunIndex);
    public abstract boolean isPlayerController();
    
    public void update() {
        // nothing
    }

    public UnitModel getModel() {
        return model;
    }

    public void setModel(UnitModel model) {
        this.model = model;
    }
}
