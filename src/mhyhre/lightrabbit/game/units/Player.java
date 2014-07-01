package mhyhre.lightrabbit.game.units;

import java.util.ArrayList;
import java.util.List;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.BulletUnit;
import mhyhre.lightrabbit.game.WaterPolygon;
import mhyhre.lightrabbit.game.weapons.ProtoGun;

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

    private float mAcceleration = 3;
    private float mSpeed = 0;
    private final float GUN_RELOADING_TIME = 0.5f;
    private float lastFireTime = 0;
    float bulletTimeCounter = 0;

    private int totalGold = 100;
    final int maxHealth = 100;
    int currentHealth = 100;
    
    VelocityParticleInitializer<Sprite> smokeVelocityInitializer;

    List<ProtoGun> guns;
    int currentGunIndex = 0;

    public Player(float xPosition) {
        super(xPosition, 0, MainActivity.resources.getTextureRegion("boat_body"), MainActivity.Me.getVertexBufferObjectManager());
        this.setScale(MainActivity.PIXEL_MULTIPLIER);
        guns = new ArrayList<ProtoGun>();

        addSmokeParticle();
    }

    private void addSmokeParticle() {

        smokeVelocityInitializer = new VelocityParticleInitializer<Sprite>(-5, 5, 5, 15);

        ITextureRegion particleSmokeTextureRegion = MainActivity.resources.getTextureRegion("boat_smoke");
        final PointParticleEmitter particleEmitter = new PointParticleEmitter(5, 10);
        final SpriteParticleSystem particleSystem = new SpriteParticleSystem(particleEmitter, 1, 5, 20, particleSmokeTextureRegion,
                this.getVertexBufferObjectManager());
        // particleSystem.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE));
        particleSystem.addParticleInitializer(smokeVelocityInitializer);
        particleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0, 2, 0.2f, 1.0f));
        particleSystem.addParticleInitializer(new AccelerationParticleInitializer<Sprite>(1, 2));
        particleSystem.addParticleInitializer(new ExpireParticleInitializer<Sprite>(2.0f));
        particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(0.0f, 0.5f, 0.0f, 0.7f));
        particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(0.5f, 2.0f, 0.7f, 0.0f));
        // particleSystem.addParticleModifier(new ColorParticleModifier<Sprite>(0.0f, 11.5f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f));

        attachChild(particleSystem);
    }

    public void addGun(ProtoGun gun) {
        guns.add(gun);
    }

    public void setCurrentGun(int index) {
        if (index < guns.size() && index >= 0) {
            currentGunIndex = index;
        }

    }

    public ProtoGun getGun(int index) {
        if (index < guns.size() && index >= 0) {
            return guns.get(index);
        }
        return null;
    }

    public void update(WaterPolygon water, final float pSecondsElapsed) {

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
        setY(water.getObjectYPosition(getX()) + 10);
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
}
