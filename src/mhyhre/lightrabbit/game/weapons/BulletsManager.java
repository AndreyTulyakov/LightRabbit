package mhyhre.lightrabbit.game.weapons;

import java.util.LinkedList;
import java.util.List;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.UnitsManager;
import mhyhre.lightrabbit.game.WaterPolygon;
import mhyhre.lightrabbit.game.units.Unit;
import mhyhre.lightrabbit.game.units.models.UnitModel;
import mhyhre.lightrabbit.game.weapons.projectiles.BulletUnit;

import org.andengine.entity.sprite.batch.SpriteBatch;
import org.andengine.opengl.texture.region.ITextureRegion;

public class BulletsManager extends SpriteBatch {
    
    private static final int MAX_BULLETS_ON_SCREEN = 50;
    private List<BulletUnit> mBullets;
    
    private WaterPolygon water;
    private UnitsManager units;

    public BulletsManager(WaterPolygon water, UnitsManager enemies) {
        super(0, 0, MainActivity.resources.getTextureAtlas("texture01"), MAX_BULLETS_ON_SCREEN+1, MainActivity.getVboManager());

        this.water = water;
        this.units = enemies;
        
        mBullets = new LinkedList<BulletUnit>();
    }
    
    public int getMaximumBulletsOnScreen() {
        return MAX_BULLETS_ON_SCREEN;
    }
    
    public boolean isCanCreateBullet() {
        return mBullets.size() < MAX_BULLETS_ON_SCREEN;
    }
    
    public void addBullet(BulletUnit bullet) {
        if (bullet != null) {
            mBullets.add(bullet);
        }
    }

    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {
        
        // Bullets
        ITextureRegion bulletRegion = MainActivity.resources.getTextureRegion("bullet");
        ITextureRegion bulletBoomRegion = MainActivity.resources.getTextureRegion("bullet_boom");
        ITextureRegion bulletResultRegion;

        for (int i = 0; i < mBullets.size(); i++) {

            BulletUnit bullet = mBullets.get(i);

            for (Unit unit : units.getUnitsList()) {
                
                UnitModel model = unit.getModel();

                if (bullet.getBoom() == 0 && bullet.collideWithCircle(model.getX(), model.getY(), model.getRadius())) {

                    if (model.isDied() == false) {

                        model.setHealth(model.getHealth() - bullet.getBoomPower());
                        
                        
                        if (model.getHealth() <= 0) {

                            model.setDied(true);

                            UnitModel bulletParent = bullet.getParentUnit();
                            bulletParent.setGold(bulletParent.getGold() + model.getGold());
                        }
                    }

                    bullet.setSink(true);
                    bullet.setBoom(10);
                    MainActivity.resources.playSound("boom01");
                }
            }

            float yPosOnWater =  water.getObjectYPosition(bullet.getX());
            
            if (bullet.getY() <= yPosOnWater) {
                bullet.setSink(true);
            }

            bullet.update();

            if (bullet.getY() < 0) {
                mBullets.remove(i);
                i--;
                continue;
            }

            if (bullet.getX() > MainActivity.getWidth() || bullet.getX() < 0) {
                mBullets.remove(i);
                i--;
                continue;
            }

            if (bullet.getBoom() > 0) {
                bulletResultRegion = bulletBoomRegion;
            } else {
                bulletResultRegion = bulletRegion;
            }
            if (bullet.getBoom() != -1) {
                draw(bulletResultRegion, bullet.getX() - bulletResultRegion.getWidth() / 2, bullet.getY() - bulletResultRegion.getHeight() / 2,
                        bulletResultRegion.getWidth()*MainActivity.PIXEL_MULTIPLIER, bulletResultRegion.getHeight()*MainActivity.PIXEL_MULTIPLIER,
                        1,  1, 1, 1, 1);

            }
        }

        submit();
        super.onManagedUpdate(pSecondsElapsed);
    }

}
