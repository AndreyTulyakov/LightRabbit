/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.weapons;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.UnitsManager;
import mhyhre.lightrabbit.game.WaterPolygon;
import mhyhre.lightrabbit.game.units.Unit;
import mhyhre.lightrabbit.game.units.models.UnitModel;
import mhyhre.lightrabbit.game.weapons.projectiles.Barrel;
import mhyhre.lightrabbit.game.weapons.projectiles.Bomb;
import mhyhre.lightrabbit.game.weapons.projectiles.Bullet150Unit;
import mhyhre.lightrabbit.game.weapons.projectiles.Bullet20Unit;
import mhyhre.lightrabbit.game.weapons.projectiles.Bullet90Unit;
import mhyhre.lightrabbit.game.weapons.projectiles.GhostBulletUnit;
import mhyhre.lightrabbit.game.weapons.projectiles.ImperialBomb;
import mhyhre.lightrabbit.game.weapons.projectiles.Projectile;
import mhyhre.lightrabbit.game.weapons.projectiles.Ray;
import mhyhre.lightrabbit.game.weapons.projectiles.RocketA;

import org.andengine.entity.sprite.batch.SpriteBatch;
import org.andengine.opengl.texture.region.ITextureRegion;

import android.util.Log;


public class BulletsManager extends SpriteBatch {

    private static BulletsManager instance;
    private static final int MAX_BULLETS_ON_SCREEN = 80;
    private List<Projectile> projectiles;

    private WaterPolygon water;
    private UnitsManager units;
    
    private Random random;

    public BulletsManager(WaterPolygon water, UnitsManager enemies) {
        super(0, 0, MainActivity.resources.getTextureAtlas("texture01"), MAX_BULLETS_ON_SCREEN + 1, MainActivity.getVboManager());

        instance = this;

        this.water = water;
        this.units = enemies;
        
        random = new Random();

        projectiles = new LinkedList<Projectile>();
    }

    public int getMaximumBulletsOnScreen() {
        return MAX_BULLETS_ON_SCREEN;
    }

    public boolean isCanCreateBullet() {
        return projectiles.size() < MAX_BULLETS_ON_SCREEN;
    }

    public void addBullet(Projectile projectile) {
        if (projectile != null) {
            projectiles.add(projectile);
        }
    }

    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {
        
        // For all projectiles
        for (int i = 0; i < projectiles.size(); i++) {

            Projectile projectile = projectiles.get(i);
            updateProjectile(projectile);
                    
            switch(projectile.getType())
            {
            case Bomb:
            case Bullet150:
            case Bullet90:
            case GhostBullet:
            case ImperialBomb:
            case Rocket:
            case SimpleBarrel:
                if (projectile.getY() < 0 || projectile.getX() > MainActivity.getWidth() || projectile.getX() < 0) {
                    projectiles.remove(projectile);
                    i--;
                    continue;
                }
                break;
                
            case Bullet20:
                Bullet20Unit bullet = (Bullet20Unit) projectile; 
                if (bullet.getBoom() == -1 || projectile.getY() < 0 || projectile.getX() > MainActivity.getWidth() || projectile.getX() < 0) {
                    projectiles.remove(projectile);
                    i--;
                    continue;
                }
                
            case Impulse:
                break;
                
            case Ray:
                Ray ray = (Ray) projectile;
                if (ray.getBoomPower() == 0) {
                    projectiles.remove(projectile);
                    i--;
                    continue;
                }
                break;

              
            case Undefined:
                break;
            default:
                break;        
            }

            
            // For all units
            for (Unit unit : units.getUnitsList()) {

                UnitModel model = unit.getModel();
                calculateProjectileActionByUnit(projectile, model);
                
            }
            drawProjectile(projectile);
        }

        submit();
        super.onManagedUpdate(pSecondsElapsed);
    }

    private void updateProjectile(Projectile projectile) {
        
        switch (projectile.getType()) {

        case Bullet150:
            {
                Bullet150Unit bullet = (Bullet150Unit) projectile;
                float yPosOnWater = water.getObjectYPosition(bullet.getX());

                if (bullet.getY() <= yPosOnWater) {
                    bullet.setSink(true);
                }
            }
            break;

        case Bullet90:
            {
                Bullet90Unit bullet = (Bullet90Unit) projectile;
                float yPosOnWater = water.getObjectYPosition(bullet.getX());
    
                if (bullet.getY() <= yPosOnWater) {
                    bullet.setSink(true);
                }
            }
            break;
            
        case GhostBullet:
        {
            GhostBulletUnit bullet = (GhostBulletUnit) projectile;
            float yPosOnWater = water.getObjectYPosition(bullet.getX());

            if (bullet.getY() <= yPosOnWater) {
                bullet.setSink(true);
            }
        }
        break;
        
            
        case Bullet20:
        {
            Bullet20Unit bullet = (Bullet20Unit) projectile;
            float yPosOnWater = water.getObjectYPosition(bullet.getX());

            if (bullet.getY() <= yPosOnWater) {
                bullet.setBoom(-1);
            }
        }
        break;
            
        case SimpleBarrel:
            {
                Barrel bullet = (Barrel) projectile;
                float yPosOnWater = water.getObjectYPosition(bullet.getX());
    
                if (bullet.getY() <= yPosOnWater) {
                    bullet.setSink(true);
                }
            }
            break;
            
        case Bomb:
            {
                Bomb bullet = (Bomb) projectile;
                float yPosOnWater = water.getObjectYPosition(bullet.getX());
    
                if (bullet.getY() <= yPosOnWater) {
                    bullet.setSink(true);
                }
            }
            break;
            
        case Rocket:
        {
            RocketA bullet = (RocketA) projectile;
            float yPosOnWater = water.getObjectYPosition(bullet.getX());

            if (bullet.getY() <= yPosOnWater) {
                bullet.setSink(true);
            }
        }
        break;
            
        case ImperialBomb:
        {
            ImperialBomb bullet = (ImperialBomb) projectile;
            float yPosOnWater = water.getObjectYPosition(bullet.getX());

            if (bullet.getY() <= yPosOnWater) {
                bullet.setSink(true);
            }
        }
        break;            
            
        case Impulse:
            break;
            
        case Ray:
            // None
            break;
            
        case Undefined:
            break;
        default:
            break;
        }
        
        projectile.update();
    }

    private void drawProjectile(Projectile projectile) {

        ITextureRegion bulletResultRegion;

        switch (projectile.getType()) {

        case Bullet150: {
            Bullet150Unit bullet = (Bullet150Unit) projectile;

            if (bullet.getBoom() > 0) {
                bulletResultRegion = MainActivity.resources.getTextureRegion("bullet150_boom");
            } else {
                bulletResultRegion = MainActivity.resources.getTextureRegion("bullet150");
            }

            if (bullet.getBoom() != -1) {
                draw(bulletResultRegion, projectile.getX() - bulletResultRegion.getWidth() / 2, projectile.getY() - bulletResultRegion.getHeight() / 2,
                        bulletResultRegion.getWidth() * MainActivity.PIXEL_MULTIPLIER, bulletResultRegion.getHeight() * MainActivity.PIXEL_MULTIPLIER, 1, 1, 1,
                        1, 1);
            }
            
        }
        break;
        
        case Bullet90:
        {
            Bullet90Unit bullet = (Bullet90Unit) projectile;

            if (bullet.getBoom() > 0) {
                bulletResultRegion = MainActivity.resources.getTextureRegion("bullet90_boom");
            } else {
                bulletResultRegion = MainActivity.resources.getTextureRegion("bullet90");
            }

            if (bullet.getBoom() != -1) {
                draw(bulletResultRegion,
                        projectile.getX() - bulletResultRegion.getWidth() / 2,
                        projectile.getY() - bulletResultRegion.getHeight() / 2,
                        bulletResultRegion.getWidth() * MainActivity.PIXEL_MULTIPLIER,
                        bulletResultRegion.getHeight() * MainActivity.PIXEL_MULTIPLIER,
                        0,
                        1, 1, 1, 1);
            }
            
        }
        break;
        
        case GhostBullet:
        {
            GhostBulletUnit bullet = (GhostBulletUnit) projectile;

            if (bullet.getBoom() > 0) {
                bulletResultRegion = MainActivity.resources.getTextureRegion("bullet90_boom");
            } else {
                bulletResultRegion = MainActivity.resources.getTextureRegion("bullet90");
            }

            if (bullet.getBoom() != -1) {
                draw(bulletResultRegion,
                        projectile.getX() - bulletResultRegion.getWidth() / 2,
                        projectile.getY() - bulletResultRegion.getHeight() / 2,
                        bulletResultRegion.getWidth() * MainActivity.PIXEL_MULTIPLIER,
                        bulletResultRegion.getHeight() * MainActivity.PIXEL_MULTIPLIER,
                        0,
                        1, 1, 1, 1);
            }
            
        }
        break;
        
        case Bullet20:
        {
            Bullet20Unit bullet = (Bullet20Unit) projectile;
            bulletResultRegion = MainActivity.resources.getTextureRegion("bullet20");

            if (bullet.getBoom() != -1) {
                draw(bulletResultRegion,
                        projectile.getX() - bulletResultRegion.getWidth() / 2,
                        projectile.getY() - bulletResultRegion.getHeight() / 2,
                        bulletResultRegion.getWidth() * MainActivity.PIXEL_MULTIPLIER,
                        bulletResultRegion.getHeight() * MainActivity.PIXEL_MULTIPLIER,
                        0,
                        1, 1, 1, 1);
            }
            
        }
        break;
        
        case SimpleBarrel:
        {
            Barrel bullet = (Barrel) projectile;
            bulletResultRegion = MainActivity.resources.getTextureRegion("x-barrel");
            draw(bulletResultRegion,
                            projectile.getX() - bulletResultRegion.getWidth() / 2,
                            projectile.getY() - bulletResultRegion.getHeight() / 2,
                            bulletResultRegion.getWidth() * MainActivity.PIXEL_MULTIPLIER,
                            bulletResultRegion.getHeight() * MainActivity.PIXEL_MULTIPLIER,
                            bullet.getRotation(),
                            1, 1, 1, bullet.getAlpha());
        }
            break;

        case Bomb:
        {
            Bomb bullet = (Bomb) projectile;

            if (bullet.getBoom() > 0) {
                bulletResultRegion = MainActivity.resources.getTextureRegion("bullet90_boom");
            } else {
                bulletResultRegion = MainActivity.resources.getTextureRegion("bomb");
            }

            if (bullet.getBoom() != -1) {
                draw(bulletResultRegion, projectile.getX() - bulletResultRegion.getWidth() / 2, projectile.getY() - bulletResultRegion.getHeight() / 2,
                        bulletResultRegion.getWidth() * MainActivity.PIXEL_MULTIPLIER, bulletResultRegion.getHeight() * MainActivity.PIXEL_MULTIPLIER, 1, 1, 1,
                        1, 1);
            }
            
        }
        break;  
        
        case ImperialBomb:
        {
            ImperialBomb bullet = (ImperialBomb) projectile;

            if (bullet.getBoom() > 0) {
                bulletResultRegion = MainActivity.resources.getTextureRegion("bullet90_boom");
            } else {
                bulletResultRegion = MainActivity.resources.getTextureRegion("imperial_bomb");
            }

            if (bullet.getBoom() != -1) {
                draw(bulletResultRegion, projectile.getX() - bulletResultRegion.getWidth() / 2, projectile.getY() - bulletResultRegion.getHeight() / 2,
                        bulletResultRegion.getWidth() * MainActivity.PIXEL_MULTIPLIER, bulletResultRegion.getHeight() * MainActivity.PIXEL_MULTIPLIER, 1, 1, 1,
                        1, 1);
            }
            
        }
        break;
        
        case Rocket:
        {
            RocketA rocket = (RocketA) projectile;

            if (rocket.getBoom() > 0) {
                bulletResultRegion = MainActivity.resources.getTextureRegion("bullet90_boom");
            } else {
                bulletResultRegion = MainActivity.resources.getTextureRegion("RocketA");
                ITextureRegion rocketFire = MainActivity.resources.getTextureRegion("bullet150_boom");
                
                if (rocket.getBoom() != -1) {
                    if(rocket.isSink() == false) {
                        float fireScale = 0.1f + 2.9f * random.nextFloat();
                        draw(rocketFire, projectile.getX() - bulletResultRegion.getWidth()*1.2f, projectile.getY(),
                                rocketFire.getWidth(), rocketFire.getHeight(),fireScale,fireScale, 1, 1, 1,
                                1, 1);
                    }
                }
            }

            if (rocket.getBoom() != -1) {
                draw(bulletResultRegion, projectile.getX() - bulletResultRegion.getWidth() / 2, projectile.getY() - bulletResultRegion.getHeight() / 2,
                        bulletResultRegion.getWidth() * MainActivity.PIXEL_MULTIPLIER, bulletResultRegion.getHeight() * MainActivity.PIXEL_MULTIPLIER, 1, 1, 1,
                        1, 1);
            }
        }
        
        case Impulse:
            break;
            
        case Ray:
            Ray ray = (Ray) projectile;
            bulletResultRegion = MainActivity.resources.getTextureRegion("ray_piece");
            
            draw(bulletResultRegion,
                    ray.getX(),ray.getY()+5,
                    ray.getX(), ray.getY()-5,
                    ray.getTargetPoint().x, ray.getTargetPoint().y+5,
                    ray.getTargetPoint().x, ray.getTargetPoint().y-5,
                    1,1,1,((float)ray.getBoomPower())/Ray.RAY_START_DAMAGE_POWER);
 
            break;
            
        case Undefined:
            break;
        default:
            break;
        }
    }

    
    private void calculateProjectileActionByUnit(Projectile projectile, UnitModel model) {

        switch (projectile.getType()) {

        case Bullet150:
        case Bullet90:
        case Bullet20:
        case GhostBullet:
            updateBulletByUnit(projectile, model);
            break;
            
        case Bomb:
            updateBulletByUnit(projectile, model);
            break;
            
        case ImperialBomb:
            updateBulletByUnit(projectile, model);
            break;
            
        case Rocket:
            updateBulletByUnit(projectile, model);
            break;

        case Impulse:
            break;
            
        case Ray:
            updateBulletByUnit(projectile, model);
            break;
        case Undefined:
            break;
        default:
            break;

        }
    }

    private void updateBulletByUnit(Projectile projectile, UnitModel model) {

        if (projectile.getUnitParent().getIdeology() == model.getIdeology()) {
            return;
        }
        
        switch (projectile.getType()) {
        
        case GhostBullet:
        {
            GhostBulletUnit bullet = (GhostBulletUnit) projectile;

            if (bullet.getBoom() == 0 && bullet.collideWithCircle(model.getX(), model.getY(), model.getRadius())) {

                if (model.isDied() == false && projectile.getType().getArmorPenetration() >= model.getArmor()) {

                    model.setHealth(model.getHealth() - bullet.getBoomPower());
                }
                bullet.setSink(true);
                bullet.setBoom(10);
                
                MainActivity.resources.playSound("boom01");
            }
        }
        break;

        case Bullet150:
        case Bullet90:
        {
            Bullet90Unit bullet = (Bullet90Unit) projectile;

            if (bullet.getBoom() == 0 && bullet.collideWithCircle(model.getX(), model.getY(), model.getRadius())) {

                if (model.isDied() == false && projectile.getType().getArmorPenetration() >= model.getArmor()) {

                    model.setHealth(model.getHealth() - bullet.getBoomPower());
                }
                bullet.setSink(true);
                bullet.setBoom(10);
                
                MainActivity.resources.playSound("boom01");
            }
        }
        break;
        
        case Bullet20:
        {
            Bullet20Unit bullet = (Bullet20Unit) projectile;

            if (bullet.getBoom() == 0 && bullet.collideWithCircle(model.getX(), model.getY(), model.getRadius())) {

                if (model.isDied() == false && projectile.getType().getArmorPenetration() >= model.getArmor()) {
                    model.setHealth(model.getHealth() - bullet.getBoomPower());
                }
                bullet.setBoom(-1);
            }
        }
        break;

        case Bomb:
        {
            Bomb bullet = (Bomb) projectile;
            if (bullet.getBoom() == 0 && bullet.collideWithCircle(model.getX(), model.getY(), model.getRadius())) {

                if (model.isDied() == false && projectile.getType().getArmorPenetration() >= model.getArmor()) {

                    model.setHealth(model.getHealth() - bullet.getBoomPower()); 
                }
                bullet.setSink(true);
                bullet.setBoom(10);
                
                MainActivity.resources.playSound("boom01");
            }
        }
        break;
        
        case Rocket:
        {
            RocketA bullet = (RocketA) projectile;
            if (bullet.getBoom() == 0 && bullet.collideWithCircle(model.getX(), model.getY(), model.getRadius())) {

                if (model.isDied() == false && projectile.getType().getArmorPenetration() >= model.getArmor()) {

                    model.setHealth(model.getHealth() - bullet.getBoomPower());
                }
                bullet.setSink(true);
                bullet.setBoom(10);
                
                MainActivity.resources.playSound("boom01");
            }
        }
        break;  
            
        case ImperialBomb:
        {
            ImperialBomb bullet = (ImperialBomb) projectile;
            if (bullet.getBoom() == 0 && bullet.collideWithCircle(model.getX(), model.getY(), model.getRadius())) {

                if (model.isDied() == false && projectile.getType().getArmorPenetration() >= model.getArmor()) {

                    model.setHealth(model.getHealth() - bullet.getBoomPower());
                }
                bullet.setSink(true);
                bullet.setBoom(10);
                
                MainActivity.resources.playSound("boom01");
            }
        }
        break;

        case Impulse:
            break;
            
        case Ray:
            Ray ray = (Ray) projectile;
            if(ray.getBoomPower() > 1 && ray.collideWithModel(model)) {
                if (model.isDied() == false && projectile.getType().getArmorPenetration() >= model.getArmor()) {

                    model.setHealth(model.getHealth() - ray.getBoomPower());
                }
            }
            break;
        case Undefined:
            break;
        default:
            break;

        }

        if (model.getHealth() <= 0) {

            model.setDied(true);
            model.setHealth(0);
            UnitModel bulletParent = projectile.getUnitParent();
            bulletParent.setGold(bulletParent.getGold() + model.getGold());
        }

    }

    public static BulletsManager getInstance() {
        if (instance == null) {
            Log.e(MainActivity.DEBUG_ID, "BulletsManager:getInstance: need created instance");
        }
        return instance;
    }

}
