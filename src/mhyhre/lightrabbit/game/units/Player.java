package mhyhre.lightrabbit.game.units;

import java.util.ArrayList;
import java.util.List;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.BulletUnit;
import mhyhre.lightrabbit.game.WaterPolygon;
import mhyhre.lightrabbit.game.weapons.ProtoGun;

import org.andengine.entity.sprite.Sprite;

public class Player extends Sprite {

    private float mAcceleration = 3;
    private float mSpeed = 0;
    private final float GUN_RELOADING_TIME = 0.5f;
    private float lastFireTime = 0;
    float bulletTimeCounter = 0;

    private int totalGold = 100;
    final int maxHealth = 100;
    int currentHealth = 100;
    
    List<ProtoGun> guns;
    int currentGunIndex = 0;

    public Player(float xPosition) {
        super(xPosition, 0, MainActivity.resources.getTextureRegion("boat_body"), MainActivity.Me.getVertexBufferObjectManager());
        guns = new ArrayList<ProtoGun>();
    }
    
    public void addGun(ProtoGun gun) {
        guns.add(gun);
    }
    
    public void setCurrentGun(int index) {
        if(index < guns.size() && index >= 0){
            currentGunIndex = index;
        }
        
    }
    
    public ProtoGun getGun(int index) {
        if(index < guns.size() && index >= 0){
            return guns.get(index);
        }
        return null;
    }

    public void update(WaterPolygon water, final float pSecondsElapsed) {
        
        bulletTimeCounter += pSecondsElapsed;
        
        if (getX() > (MainActivity.getWidth() - 32 - getWidth()) && mSpeed > 0) {
            mSpeed = 0;
        }

        if (getX() < 32 && mSpeed < 0) {
            mSpeed = 0;
        }

        setX(getX() + mSpeed);
        setY(water.getObjectYPosition(getX()) + 5);
        setRotation(water.getObjectAngle(getX()) / 2.0f);
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

    public BulletUnit fire() {

        if ((Math.abs(bulletTimeCounter) - Math.abs(lastFireTime)) > getGunReloadingTime()) {

            MainActivity.resources.playSound("shoot01");
            
            
            MainActivity.vibrate(30);

            BulletUnit bullet = new BulletUnit(getX(), getY() + 15);
            bullet.setAccelerationByAngle(getRotation() - 15, 8);

            lastFireTime = bulletTimeCounter;

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
    
    /*
     * Make currentHealth --
     */
    public void decrementHealth() {
        if(currentHealth > 0) {
            currentHealth--;
        }
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
