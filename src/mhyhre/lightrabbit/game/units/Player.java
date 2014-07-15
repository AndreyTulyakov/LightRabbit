package mhyhre.lightrabbit.game.units;

import java.util.ArrayList;
import java.util.List;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.WaterPolygon;
import mhyhre.lightrabbit.game.weapons.guns.Gun;
import mhyhre.lightrabbit.game.weapons.guns.Gun90;
import mhyhre.lightrabbit.game.weapons.projectiles.BulletUnit;

import org.andengine.entity.particle.SpriteParticleSystem;
import org.andengine.entity.particle.emitter.PointParticleEmitter;
import org.andengine.entity.particle.initializer.AccelerationParticleInitializer;
import org.andengine.entity.particle.initializer.ExpireParticleInitializer;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.particle.modifier.ScaleParticleModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;

public class Player extends Sprite {

    UnitIdeology ideology;
    Gun gun;
    
    private float mAcceleration = 3;
    private float mSpeed = 0;
    private final float GUN_RELOADING_TIME = 0.5f;
    private float lastFireTime = 0;
    float bulletTimeCounter = 0;

    private int totalGold = 100;
    private final int maxHealth = 100;
    private int currentHealth = 100;
    private boolean canJump;
    private float jumpAcceleration;
    private static final float JUMP_ACCELERATION_LIMIT = 16;

    
    VelocityParticleInitializer<Sprite> smokeVelocityInitializer;

    List<Gun> guns;
    int currentGunIndex = 0;

    public Player(float xPosition) {
        super(xPosition, 0, MainActivity.resources.getTextureRegion("boat_body"), MainActivity.Me.getVertexBufferObjectManager());
        this.setScale(MainActivity.PIXEL_MULTIPLIER);
        guns = new ArrayList<Gun>();

        gun = new Gun90(50);
        
        canJump = true;
        addSmokeParticle();
    }

    private void addSmokeParticle() {

        smokeVelocityInitializer = new VelocityParticleInitializer<Sprite>(-5, 5, 5, 15);

        ITextureRegion particleSmokeTextureRegion = MainActivity.resources.getTextureRegion("boat_smoke");
        final PointParticleEmitter particleEmitter = new PointParticleEmitter(5, 10);
        final SpriteParticleSystem particleSystem = new SpriteParticleSystem(particleEmitter, 1, 5, 20, particleSmokeTextureRegion,
                this.getVertexBufferObjectManager());
        particleSystem.addParticleInitializer(smokeVelocityInitializer);
        particleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0, 2, 0.2f, 1.0f));
        particleSystem.addParticleInitializer(new AccelerationParticleInitializer<Sprite>(1, 2));
        particleSystem.addParticleInitializer(new ExpireParticleInitializer<Sprite>(2.0f));
        particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(0.0f, 0.5f, 0.0f, 0.7f));
        particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(0.5f, 2.0f, 0.7f, 0.0f));
        
        attachChild(particleSystem);
    }

    public void addGun(Gun gun) {
        guns.add(gun);
    }

    public void setCurrentGun(int index) {
        if (index < guns.size() && index >= 0) {
            currentGunIndex = index;
        }

    }

    public Gun getGun(int index) {
        if (index < guns.size() && index >= 0) {
            return guns.get(index);
        }
        return null;
    }

    public void update(WaterPolygon water, final float pSecondsElapsed) {

        // Update jumping
        if(canJump == false && jumpAcceleration > -JUMP_ACCELERATION_LIMIT) {
            jumpAcceleration -= 0.8f;
        }
        
        // Smoke tracers
        if(mSpeed == 0) {
            smokeVelocityInitializer.setVelocity(-3, 1, 2, 3);
        } else if(mSpeed < 0) {
           smokeVelocityInitializer.setVelocity(-1, 2, 3, 10);
        } else {
            smokeVelocityInitializer.setVelocity(-5, 1, 3, 10);
        }
        
        bulletTimeCounter += pSecondsElapsed;

        if (getX() > (MainActivity.getWidth() - 32 - getWidth()) && mSpeed > 0) {
            mSpeed = 0;
        }

        if (getX() < 32 && mSpeed < 0) {
            mSpeed = 0;
        }
        
        setX(getX() + mSpeed);

        float waveYPositionUnderPlayer = 10 + water.getObjectYPosition(getX());
        
        

        setRotation(water.getObjectAngle(getX()) / 2.0f);
        
        if(getY() < waveYPositionUnderPlayer - 10 && canJump == false) {
            setY(waveYPositionUnderPlayer);
            canJump = true;
        } else {
            if(canJump == false) {
                setY(getY() + jumpAcceleration);
            } else {
                setY(waveYPositionUnderPlayer);
            }
        }
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
        if (currentHealth > 0) {
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

    public boolean isCanJump() {
        return canJump;
    }

    public void jump() {
        if(canJump == true) {
            canJump = false;
            jumpAcceleration = JUMP_ACCELERATION_LIMIT;
        }
    }
}
