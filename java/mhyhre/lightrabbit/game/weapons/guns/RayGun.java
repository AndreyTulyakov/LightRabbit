/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.weapons.guns;

import mhyhre.lightrabbit.game.units.UnitMoveDirection;
import mhyhre.lightrabbit.game.units.models.UnitModel;
import mhyhre.lightrabbit.game.weapons.GunType;
import mhyhre.lightrabbit.game.weapons.projectiles.Projectile;
import mhyhre.lightrabbit.game.weapons.projectiles.Ray;

public class RayGun extends Gun {
    
    private static final float RELOADING_TIME = 0.4f;
    private float lastFireTime;


    public RayGun(UnitModel parent, int amount) {
        super(parent, GunType.RAY_GUN, amount);
    }

    @Override
    public Projectile fire(UnitMoveDirection direction) {
 
        if(canFireNow()) {   

            lastFireTime = currentTime;
            
            if(projectilesAmount > 0) {
                projectilesAmount--;
            }
            
            Ray ray = new Ray(parent.getX(), parent.getY()-8, this.parent, parent.getRotation());
            return ray;
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