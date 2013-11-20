package mhyhre.lightrabbit.game.units;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.BulletUnit;
import mhyhre.lightrabbit.game.WaterPolygon;

import org.andengine.entity.sprite.Sprite;

public class Player extends Sprite {

    private float mAcceleration = 3;
    private float mSpeed = 0;
    private final float GUN_RELOADING_TIME = 0.5f;
    private float lastFireTime = 0;

    private int totalGold = 100;
    final int maxHealth = 3;
    int currentHealth = 3;

    public Player(float xPosition) {
        super(xPosition, 0, MainActivity.resources.getTextureRegion("boat_body"), MainActivity.Me.getVertexBufferObjectManager());
        // TODO Auto-generated constructor stub
    }

    public void update(WaterPolygon water) {
        if (getX() > (MainActivity.getWidth() - 32 - getWidth()) && mSpeed > 0)
            mSpeed = 0;

        if (getX() < 32 && mSpeed < 0)
            mSpeed = 0;

        setX(getX() + mSpeed);
        setY(water.getYPositionOnWave(getX()) + 5);
        setRotation(water.getAngleOnWave(getX()) / 2.0f);
    }

    public float getBoatSpeed() {
        return mSpeed;
    }

    public float getBoatAcceleration() {
        return mAcceleration;
    }

    public void setBoatSpeed(float boatSpeed) {
        this.mSpeed = boatSpeed;
    }

    public float getGunReloadingTime() {
        return GUN_RELOADING_TIME;
    }

    public BulletUnit fire(float fireTime) {

        if (fireTime - lastFireTime > getGunReloadingTime()) {

            MainActivity.vibrate(30);

            BulletUnit bullet = new BulletUnit(getX(), getY() + 15);
            bullet.setAccelerationByAngle(getRotation() - 15, 8);

            lastFireTime = fireTime;

            return bullet;
        }

        return null;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public int getTotalGold() {
        return totalGold;
    }

    public void setTotalGold(int totalGold) {
        this.totalGold = totalGold;
    }
}
