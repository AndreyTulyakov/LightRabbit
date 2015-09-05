/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.weapons.guns;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.units.UnitMoveDirection;
import mhyhre.lightrabbit.game.units.models.UnitModel;
import mhyhre.lightrabbit.game.weapons.GunType;
import mhyhre.lightrabbit.game.weapons.projectiles.Bullet90Unit;
import mhyhre.lightrabbit.game.weapons.projectiles.Projectile;



public class Gun90 extends Gun {
    
    private static final float RELOADING_TIME = 0.6f;
    private static final int SHOT_POWER = 8;
    protected static float lashShootTime = 0;
    private float lastFireTime;


    public Gun90(UnitModel parent, int amount) {
        super(parent, GunType.BULLET_GUN_90, amount);
        
    }

    @Override
    public Projectile fire(UnitMoveDirection direction) {

        if(canFireNow()) {
            lastFireTime = currentTime;
            
            if(projectilesAmount > 0) {
                projectilesAmount--;
            }

            if(Math.abs(lashShootTime-currentTime) > 0.5f) {
                MainActivity.resources.playSound("shoot01");
                lashShootTime = currentTime;
            }


            Bullet90Unit bullet = new Bullet90Unit(parent.getX(), parent.getY(), this.parent);
            float angle = 0;
            
            switch(direction) {
            case LEFT:
                angle =  180 - parent.getRotation() + 20;
                break;
                
            case NONE:
                // None
                break;
                
            case RIGHT:
                angle = -parent.getRotation() - 15;
                break;
            default:
                break;
            }

            bullet.setAccelerationByAngle(angle, SHOT_POWER);
            
            return bullet;
        }
        return null;
    }

    @Override
    public boolean canFireNow() {
        
        if(this.projectilesAmount > 0) {
            if(Math.abs(currentTime - lastFireTime) >= RELOADING_TIME) {
               return true;
            }
        }
        return false;
    }
}
