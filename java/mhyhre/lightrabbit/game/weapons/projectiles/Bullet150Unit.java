/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.weapons.projectiles;

import mhyhre.lightrabbit.game.units.models.UnitModel;

public class Bullet150Unit extends Bullet90Unit {

    public Bullet150Unit(float pX, float pY, UnitModel parent) {
        super(pX, pY, parent);

        this.setType(ProjectileType.Bullet150);
        mBoomPower = 100;
        bulletRaduis = 12;
    }

}
