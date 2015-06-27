/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.units.controller;

import mhyhre.lightrabbit.game.units.models.UnitModel;
import mhyhre.lightrabbit.game.weapons.guns.Gun;

public abstract class UnitController {
    
    public enum GunIndex {
        FIRST_GUN, SECOND_GUN;
    }
    
    protected UnitModel model;
    protected float currentTime;
    
    public UnitController(UnitModel inModel) {
        this.model = inModel;
    }
    
    //public abstract void update();
    public abstract void accelerate(int xAcceleration, int yAcceleration);
    public abstract void jump();
    public abstract void fireByGun(GunIndex index);
    public abstract boolean isPlayerController();
    

    public UnitModel getModel() {
        return model;
    }

    public void setModel(UnitModel model) {
        this.model = model;
    }

    public void update(float time) {
        currentTime = time;
    }
    
    public boolean isReloadGun() {
        if(model != null) {
            Gun gun = model.getGun();
            if(gun != null) {
                return gun.isReloading();
            }
        }
        return false;
    }
    
    public boolean isReloadSecondGun() {
        if(model != null) {
            Gun gun = model.getSecondGun();
            if(gun != null) {
                return gun.isReloading();
            }
        }
        return false;
    }
}
