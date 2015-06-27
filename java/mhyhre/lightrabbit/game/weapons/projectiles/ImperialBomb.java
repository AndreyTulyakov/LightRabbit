/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.weapons.projectiles;

import mhyhre.lightrabbit.game.units.models.UnitModel;
import mhyhre.lightrabbit.utils.Vector2;

public class ImperialBomb extends Projectile {

    public static final float sSinkSpeed = -1.0f;
    public static final float sGravity = -0.04f;

    private Vector2 mAcceleration;
    protected boolean mSink;
    protected int mBoom;
    protected int mBoomPower;
    
    protected float bulletRaduis = 10;

    public ImperialBomb(float pX, float pY, UnitModel parent) {
        super(parent, pX, pY);
        
        setType(ProjectileType.ImperialBomb);
        
        mBoomPower = 75;

        mAcceleration = new Vector2(0,0);
        mSink = false;
        mBoom = 0;
    }

    public Vector2 getPosition() {
        return new Vector2(mX, mY);
    }

    public Vector2 getAcceleration() {
        return mAcceleration.clone();
    }

    public void setAcceleration(float pX, float pY) {
        mAcceleration.set(pX, pY);
    }

    public void setAccelerationByAngle(float angleGrad, float power) {
        float radAngle = (float) Math.toRadians(angleGrad);
        mAcceleration.set((float) Math.cos(radAngle), (float) -Math.sin(radAngle));
        mAcceleration.mul(power);
    }

    public boolean isSink() {
        return mSink;
    }

    public void setSink(boolean pSink) {
        this.mSink = pSink;
    }

    @Override
    public void update() {

        if (mBoom > 0) {
            if (mBoom == 1) {
                mBoom = -1;
            } else {
                mBoom--;
            }
        }

        if (mSink) {
            mY += sSinkSpeed;
            mX -= 1;

        } else {
            mX += mAcceleration.x;
            mY += mAcceleration.y;

            mAcceleration.y += sGravity;
        }
    }

    public boolean collideWithCircle(float x, float y, float radius) {

        float dx = getX() - (x);
        float dy = getY() - (y);

        float c = (dx * dx) + (dy * dy);

        radius = radius + bulletRaduis;
        
        if (c <= radius * radius)
            return true;
        return false;
    }

    public boolean isBoomed() {
        return mBoom <= 0;
    }
    
    public int getBoom() {
        return mBoom;
    }

    public void setBoom(int mBoom) {
        this.mBoom = mBoom;
    }

    public int getBoomPower() {
        return mBoomPower;
    }
}

