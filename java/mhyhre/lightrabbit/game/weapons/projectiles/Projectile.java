/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.weapons.projectiles;

import mhyhre.lightrabbit.game.units.models.UnitModel;

import org.andengine.entity.Entity;

public abstract class Projectile extends Entity {
    
    protected ProjectileType type;
    protected final UnitModel unitParent;

    public Projectile(UnitModel unitParent, float mx, float my) {
        super(mx, my);
        this.unitParent = unitParent;
        this.type = ProjectileType.Undefined;
    }

    public ProjectileType getType() {
        return type;
    }

    public UnitModel getUnitParent() {
        return unitParent;
    }
    
    public abstract void update();

    protected void setType(ProjectileType type) {
        this.type = type;
    }
}
