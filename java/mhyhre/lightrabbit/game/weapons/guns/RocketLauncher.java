/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.weapons.guns;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.units.UnitMoveDirection;
import mhyhre.lightrabbit.game.units.models.UnitModel;
import mhyhre.lightrabbit.game.weapons.GunType;
import mhyhre.lightrabbit.game.weapons.projectiles.Projectile;
import mhyhre.lightrabbit.game.weapons.projectiles.RocketA;
import android.util.Log;

public class RocketLauncher extends Gun {
    
    private static final float RELOADING_TIME = 0.6f;
    private static final int SHOT_POWER = 8;
    protected static float lashShootTime = 0;
    private float lastFireTime;


    public RocketLauncher(UnitModel parent, int amount) {
        super(parent, GunType.ROCKET_LAUNCHER, amount);
        
    }

    @Override
    public Projectile fire(UnitMoveDirection direction) {

        if(canFireNow()) {   
            Log.i(MainActivity.DEBUG_ID, "RocketLauncher:fire!");
            lastFireTime = currentTime;
            
            if(projectilesAmount > 0) {
                projectilesAmount--;
            }


            if(Math.abs(lashShootTime-currentTime) > 0.5f) {
                MainActivity.resources.playSound("shoot01");
                lashShootTime = currentTime;
            }

            RocketA bullet = new RocketA(parent.getX(), parent.getY()-15, this.parent);
            float angle = 0;
            
            switch(direction) {
            case LEFT:
                angle =  180 - parent.getRotation() + 4;
                break;
                
            case NONE:
                // None
                break;
                
            case RIGHT:
                angle = -parent.getRotation() - 4;
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
