/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.weapons.projectiles;

import mhyhre.lightrabbit.game.units.models.UnitModel;
import mhyhre.lightrabbit.utils.Vector2;

public class Ray extends Projectile {

    public final static int RAY_LENGTH = 600;
    public final static int RAY_START_DAMAGE_POWER = 50;
    
    protected int mBoomPower;
    protected Vector2 targetPoint;
    private float angle;

    public Ray(float pX, float pY, UnitModel parent, float angle) {
        super(parent, pX, pY);
        
        setType(ProjectileType.Ray);
        
        mBoomPower = RAY_START_DAMAGE_POWER;
 
        this.angle = angle;
        float radAngle = (float) Math.toRadians(angle);
        targetPoint = new Vector2((float) Math.cos(radAngle), (float) Math.sin(radAngle));
        targetPoint.mul(RAY_LENGTH);
        targetPoint.add(pX, pY);
    }
    

    public Vector2 getPosition() {
        return new Vector2(mX, mY);
    }

    @Override
    public void update() {

        mBoomPower *= 0.5f;
        if(mBoomPower < 2) {
            mBoomPower = 0;
        }  
    }

    public int getBoomPower() {
        return mBoomPower;
    }
    
    public Vector2 getTargetPoint() {
        return targetPoint;
    }

    public float getAngle() {
        return angle;
    }

    public boolean collideWithModel(UnitModel model) {
        
        Vector2 m  = new Vector2(model.getX(), model.getY());
        
        float top = ((targetPoint.x - getX())*(m.y-getY()))-((targetPoint.y - getY())*(m.x-getX()));
        float s1 = (targetPoint.x - getX());
        float s2 = (targetPoint.y-  getY());
        float down = (float) Math.sqrt(s1*s1+s2*s2);
        
        float h  = Math.abs(top/down);
        
        if( h < model.getRadius()) {
            boolean firstCond = model.getX() < targetPoint.x && model.getX() > getX();
            boolean secondCond = model.getX() > targetPoint.x && model.getY() < getX();
            if(firstCond || secondCond) {
                return true;
            }
        }
        
        return false;
    }
}

