/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.weapons.guns;

import mhyhre.lightrabbit.game.units.UnitMoveDirection;
import mhyhre.lightrabbit.game.units.models.UnitModel;
import mhyhre.lightrabbit.game.weapons.GunType;
import mhyhre.lightrabbit.game.weapons.projectiles.ImperialBomb;
import mhyhre.lightrabbit.game.weapons.projectiles.Projectile;

public class FastBomber extends Gun {
    
    private static final float RELOADING_TIME = 0.8f;
    private float lastFireTime;


    public FastBomber(UnitModel parent, int amount) {
        super(parent, GunType.FAST_BOMBER, amount);
        
    }

    @Override
    public Projectile fire(UnitMoveDirection direction) {

        if(canFireNow()) {   
            lastFireTime = currentTime;


            if(projectilesAmount > 0) {
                projectilesAmount--;
            }
            
            ImperialBomb bullet = new ImperialBomb(parent.getX(), parent.getY()-40, this.parent);
            
            float speed = 0.77f;
            if(direction == UnitMoveDirection.LEFT) {
                speed *= -1;
            }
            
            bullet.setAcceleration(speed, 0);
            
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

