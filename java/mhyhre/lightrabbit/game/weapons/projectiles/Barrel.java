/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.weapons.projectiles;

import mhyhre.lightrabbit.game.units.models.UnitModel;
import mhyhre.lightrabbit.utils.Vector2;

public class Barrel extends Projectile {

    public static final float sSinkSpeed = -1.0f;
    public static final float sGravity = -0.15f;

    private Vector2 mAcceleration;
    protected boolean mSink;

    
    protected float bulletRaduis = 12;

    public Barrel(float pX, float pY, UnitModel parent) {
        super(parent, pX, pY);
        
        setType(ProjectileType.SimpleBarrel);
        mAcceleration = new Vector2(0, 0);
        mSink = false;
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

    public void setAcceleration(float power) {
        mAcceleration.set(0, -power);
    }

    public boolean isSink() {
        return mSink;
    }

    public void setSink(boolean pSink) {
        this.mSink = pSink;
    }

    @Override
    public void update() {

        setRotation(getRotation()+1);
        float alpha = getAlpha();
        if(alpha > 0.01f) {
            alpha -= 0.005f;
        } else {
            alpha = 0.0f;
        }
        setAlpha(alpha);
        
        if (mSink) {
            mY += sSinkSpeed;


        } else {

            mAcceleration.y += sGravity;
        }
    }

}
