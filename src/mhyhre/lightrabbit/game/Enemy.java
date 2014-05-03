package mhyhre.lightrabbit.game;

import mhyhre.lightrabbit.utils.Vector2;


public abstract class Enemy {

    protected EnemyType enemyType;
    protected int health;
    protected int armor;
    protected boolean isDied;
    protected float xPosition, yPosition;
    protected float width, height;
    protected float radius = 0;

    public Enemy(EnemyType pType, int pMaxHealth, int pArmor) {
        health = pMaxHealth;
        enemyType = pType;
        armor = pArmor;

        xPosition = 0;
        yPosition = 0;
        width = 0;
        height = 0;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int pHealth) {
        this.health = pHealth;
    }

    public boolean isDied() {
        return isDied;
    }

    public void setDied(boolean mDied) {
        this.isDied = mDied;
    }

    public abstract void update();

    public void setSize(float w, float h) {
        width = w;
        height = h;
    }

    public float getX() {
        return xPosition;
    }

    public void setX(float mX) {
        this.xPosition = mX;
    }

    public float getY() {
        return yPosition;
    }

    public void setY(float mY) {
        this.yPosition = mY;
    }

    public void setPosition(float pX, float pY) {
        xPosition = pX;
        yPosition = pY;
    }

    public void setCenteredPosition(float pX, float pY) {
        xPosition = pX - width / 2;
        yPosition = pY - height / 2;
    }

    public void setPosition(Vector2 pos) {
        xPosition = pos.x;
        yPosition = pos.y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public EnemyType getEnemyType() {
        return enemyType;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}
