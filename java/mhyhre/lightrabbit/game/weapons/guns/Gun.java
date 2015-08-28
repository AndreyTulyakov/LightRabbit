/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.weapons.guns;

import mhyhre.lightrabbit.game.units.UnitMoveDirection;
import mhyhre.lightrabbit.game.units.models.UnitModel;
import mhyhre.lightrabbit.game.weapons.GunType;
import mhyhre.lightrabbit.game.weapons.projectiles.Projectile;



public abstract class Gun {
    
    protected GunType type;
    protected UnitModel parent;
    protected int projectilesAmount;
    protected float currentTime;


    protected Gun(UnitModel parent, GunType type, int projectilesAmount) {
        this.parent = parent;
        this.type = type;
        this.projectilesAmount = projectilesAmount;
    }
    
    public void update(float time) {
        currentTime += time;
    }
    
    public abstract boolean canFireNow();
    public abstract Projectile fire(UnitMoveDirection unitMoveDirection);
    
    public boolean isReloading() {
        return (canFireNow() == false);
    }
    
    public int getProjectilesAmount() {
        return projectilesAmount;
    }

    public void setProjectilesAmount(int projectilesAmount) {
        this.projectilesAmount = projectilesAmount;
    }

    public GunType getType() {
        return type;
    }

}
