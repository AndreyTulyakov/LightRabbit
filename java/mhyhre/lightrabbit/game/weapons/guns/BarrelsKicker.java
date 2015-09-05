/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.weapons.guns;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.units.UnitMoveDirection;
import mhyhre.lightrabbit.game.units.models.UnitModel;
import mhyhre.lightrabbit.game.weapons.GunType;
import mhyhre.lightrabbit.game.weapons.projectiles.Barrel;
import mhyhre.lightrabbit.game.weapons.projectiles.Projectile;

public class BarrelsKicker extends Gun {
    
    private static final float RELOADING_TIME = 1.2f;
    private static final int SHOT_POWER = 1;
    
    private float lastFireTime;


    public BarrelsKicker(UnitModel parent, int amount) {
        super(parent, GunType.BARRELS_KICKER, amount);
        
    }

    @Override
    public Projectile fire(UnitMoveDirection direction) {

        if(canFireNow()) {
            lastFireTime = currentTime;
            
            if(projectilesAmount > 0) {
                projectilesAmount--;
            }
            
            MainActivity.resources.playSound("dropToWater");
            
            
            Barrel bullet = new Barrel(parent.getX(), parent.getY()-40, this.parent);
            
            bullet.setAcceleration(SHOT_POWER);
            
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