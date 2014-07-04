package mhyhre.lightrabbit.game;

import java.util.LinkedList;
import java.util.List;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.units.Player;

import org.andengine.entity.sprite.batch.SpriteBatch;
import org.andengine.opengl.texture.region.ITextureRegion;

public class BulletsManager extends SpriteBatch {
    
    private static final int MAX_BULLETS_ON_SCREEN = 50;
    private List<BulletUnit> mBullets;
    
    private WaterPolygon water;
    private EnemiesManager enemies;
    private Player player;
    private GameUserInterface hud;

    public BulletsManager(WaterPolygon water, EnemiesManager enemies, Player player, GameUserInterface hud) {
        super(0, 0, MainActivity.resources.getTextureAtlas("texture01"), MAX_BULLETS_ON_SCREEN+1, MainActivity.getVboManager());

        this.water = water;
        this.enemies = enemies;
        this.player = player;
        this.hud = hud;
        
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

            for (Enemy enemy : enemies.getEnemiesList()) {

                if (bullet.getBoom() == 0 && bullet.collideWithCircle(enemy.getX(), enemy.getY(), enemy.getRadius())) {

                    if (enemy.isDied() == false) {

                        enemy.setHealth(enemy.getHealth() - bullet.getBoomPower());

                        if (enemy.getHealth() <= 0) {

                            enemy.setDied(true);

                            player.setTotalGold(player.getTotalGold() + 50);
                            hud.updateGoldIndicator(player.getTotalGold());
                        }
                    }

                    bullet.setSink(true);
                    bullet.setBoom(10);
                }
            }

            if (bullet.getY() < water.getObjectYPosition(bullet.getX())) {
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
