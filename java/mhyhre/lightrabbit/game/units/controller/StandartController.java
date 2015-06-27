/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.units.controller;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.units.models.UnitModel;
import mhyhre.lightrabbit.game.weapons.BulletsManager;
import mhyhre.lightrabbit.game.weapons.guns.Gun;
import mhyhre.lightrabbit.game.weapons.projectiles.Projectile;

public class StandartController extends UnitController {

    private static final int BORDER_FOR_UNIT = 50;

    public StandartController(UnitModel inModel) {
        super(inModel);
    }

    @Override
    public void update(float time) {

        Gun gun = model.getGun();

        if (gun != null) {
            gun.update(time);
        }
        
        Gun secondGun = model.getSecondGun();

        if (secondGun != null) {
            secondGun.update(time);
        }        

        super.update(time);
    }


    @Override
    public void jump() {

        if (model.isDied() == false) {
            model.jump();
        }

    }

    
    @Override
    public void fireByGun(GunIndex index) {

        Gun gun = null;

        switch(index) {
        case FIRST_GUN:
            gun = model.getGun();
            break;
        case SECOND_GUN:
            gun = model.getSecondGun();
            break;
        default:
            break;
        
        }
       
        if (gun != null) {
            if (gun.canFireNow()) {

                Projectile projectile = gun.fire(model.getMoveDirection());

                if (projectile != null) {
                    BulletsManager.getInstance().addBullet(projectile);
                }
            }
        }
    }

    
    @Override
    public boolean isPlayerController() {
        return true;
    }

    
    @Override
    public void accelerate(int xAcceleration, int yAcceleration) {
        
        if (model.isDied() == false) {

            if (model.getX() > (MainActivity.getWidth() - BORDER_FOR_UNIT) && xAcceleration > 0) {
                xAcceleration = 0;
            }

            if (model.getX() < BORDER_FOR_UNIT && xAcceleration < 0) {
                xAcceleration = 0;
            }
            
            if (model.getY() > (MainActivity.getHeight() - BORDER_FOR_UNIT) && yAcceleration > 0) {
                yAcceleration = 0;
            }

            if (model.getY() < BORDER_FOR_UNIT && yAcceleration < 0) {
                yAcceleration = 0;
            }
            
            model.setX(model.getX() + model.getMoveAcceleration() * xAcceleration);
            model.setY(model.getY() + model.getMoveAcceleration() * yAcceleration);
        }
    }

}
