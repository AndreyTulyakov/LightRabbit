package mhyhre.lightrabbit.game;

import org.andengine.entity.primitive.Vector2;

public abstract class Enemy {

    protected EnemyType mEnemyType;
    protected int mHealth;
    protected boolean mDied;
    protected float mX, mY;
    protected float mWidth, mHeight;
    protected float radius = 0;

    public Enemy(EnemyType pType, int pMaxHealth) {
        mHealth = pMaxHealth;
        mEnemyType = pType;

        mX = 0;
        mY = 0;
        mWidth = 0;
        mHeight = 0;
    }

    public int getHealth() {
        return mHealth;
    }

    public void setHealth(int pHealth) {
        this.mHealth = pHealth;
    }

    public boolean isDied() {
        return mDied;
    }

    public void setDied(boolean mDied) {
        this.mDied = mDied;
    }

    public abstract void update();

    public void setSize(float w, float h) {
        mWidth = w;
        mHeight = h;
    }

    public float getX() {
        return mX;
    }

    public void setX(float mX) {
        this.mX = mX;
    }

    public float getY() {
        return mY;
    }

    public void setY(float mY) {
        this.mY = mY;
    }

    public void setPosition(float pX, float pY) {
        mX = pX;
        mY = pY;
    }

    public void setCenteredPosition(float pX, float pY) {
        mX = pX - mWidth / 2;
        mY = pY - mHeight / 2;
    }

    public void setPosition(Vector2 pos) {
        mX = pos.x;
        mY = pos.y;
    }

    public float getWidth() {
        return mWidth;
    }

    public void setWidth(float width) {
        this.mWidth = width;
    }

    public float getHeight() {
        return mHeight;
    }

    public void setHeight(float height) {
        this.mHeight = height;
    }

    public EnemyType getEnemyType() {
        return mEnemyType;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}
