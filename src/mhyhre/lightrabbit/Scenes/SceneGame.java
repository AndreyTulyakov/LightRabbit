/*
 * Copyright (C) 2013 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 * 
 * This work is licensed under a Creative Commons 
 * Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * You may obtain a copy of the License at
 *
 *		http://creativecommons.org/licenses/by-nc-nd/3.0/legalcode
 *
 */

package mhyhre.lightrabbit.Scenes;

import java.util.LinkedList;
import java.util.List;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.MhyhreScene;
import mhyhre.lightrabbit.game.BulletUnit;
import mhyhre.lightrabbit.game.CloudsManager;
import mhyhre.lightrabbit.game.Collisions;
import mhyhre.lightrabbit.game.EnemiesManager;
import mhyhre.lightrabbit.game.Enemy;
import mhyhre.lightrabbit.game.GameHUD;
import mhyhre.lightrabbit.game.GameMessageManager;
import mhyhre.lightrabbit.game.SkyManager;
import mhyhre.lightrabbit.game.WaterPolygon;
import mhyhre.lightrabbit.game.Levels.Event;
import mhyhre.lightrabbit.game.Levels.Level;
import mhyhre.lightrabbit.game.units.Player;

import org.andengine.entity.sprite.batch.SpriteBatch;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import android.util.Log;

/**
 * This scene control game process.
 */
public class SceneGame extends MhyhreScene {

    private static final int WATER_RESOLUTION = 16;
    private static final int CLOUDS_MAXIMUM = 5;
    private static final int MAX_BULLETS_ON_SCREEN = 50;

    float timeCounter = 0;
    float bulletTimeCounter = 0;

    boolean loaded = true;
    boolean pause = false;

    SpriteBatch bulletBatch;

    List<BulletUnit> mBullets;

    private GameHUD HUD;
    private CloudsManager mClouds;
    private SkyManager mSkyes;
    private EnemiesManager mEnemies;
    private Player mPlayer;
    private WaterPolygon water;
    private GameMessageManager messageManager;
    
    Level level;
    

    private Text textTestTimer;

    public SceneGame(String levelFileName) {

        level = new Level(levelFileName);

        HUD = new GameHUD();

        createGameObjects();
        configureGameObjects();

        attachChild(HUD);

        textTestTimer = new Text(100, 100, MainActivity.resources.getFont("White Furore"), "0", 100, MainActivity.getVboManager());
        attachChild(textTestTimer);

        Log.i(MainActivity.DEBUG_ID, "Scene game created");
        loaded = true;
    }

    public void start() {
        pause = false;
    }

    public void pause() {
        pause = true;
    }

    private void configureGameObjects() {
        mSkyes.setCurrentTime(level.getStartTime());
    }

    private void createGameObjects() {

        /* Environment */

        mClouds = new CloudsManager(CLOUDS_MAXIMUM, MainActivity.getVboManager());
        water = new WaterPolygon(WATER_RESOLUTION, MainActivity.getVboManager());

        mPlayer = new Player(100);
        mEnemies = new EnemiesManager(water, MainActivity.getVboManager());

        mBullets = new LinkedList<BulletUnit>();
        bulletBatch = new SpriteBatch(MainActivity.resources.getTextureAtlas("texture01"), MAX_BULLETS_ON_SCREEN, MainActivity.getVboManager());
        mSkyes = new SkyManager(MainActivity.getVboManager());
        
        messageManager = new GameMessageManager();
        messageManager.setDialogBase(level.getDialogBase());
        messageManager.setCharacterBase(level.getCharacterBase());

        attachChild(mSkyes);
        attachChild(mClouds);
        attachChild(mEnemies);
        attachChild(bulletBatch);
        attachChild(mPlayer);
        attachChild(water);
        attachChild(messageManager);

    }

    @Override
    public boolean onSceneTouchEvent(final TouchEvent pSceneTouchEvent) {

        if (!loaded)
            return true;
        
        if(messageManager.isActiveMessage() == true) {
            HUD.resetStates();
            messageManager.onSceneTouchEvent(pSceneTouchEvent);
        } else {
            HUD.onSceneTouchEvent(pSceneTouchEvent);
        }
        return super.onSceneTouchEvent(pSceneTouchEvent);
    }

    @Override
    protected void onManagedUpdate(final float pSecondsElapsed) {

        if(pause) {
            return;
        }
        
        updateControlls();

        mPlayer.update(water);

        timeCounter += pSecondsElapsed;
        bulletTimeCounter += pSecondsElapsed;

        updateEvents();

        textTestTimer.setText("Time: " + (int) timeCounter);

        updateBullets();
        mEnemies.update();
        enemiesSharks();
        HUD.updateHealthIndicator(mPlayer.getCurrentHealth(), mPlayer.getMaxHealth());
        super.onManagedUpdate(pSecondsElapsed);
    }
    
    
    private void updateEvents() {

        Event gameEvent = level.getCurrentEvent();

        // if all events complete
        if (gameEvent == null) {
            endGame();

        } else {
            
            switch (gameEvent.getType()) {

            case GAME_WAIT_SECONDS:
                if (((int) timeCounter) >= gameEvent.getIntegerArg()) {
                    Log.i(MainActivity.DEBUG_ID, "Last event was: " + level.getCurrentEvent());
                    timeCounter = 0;
                    level.nextEvent();
                }
                break;
                
            case GAME_STOP_TIME:
                mSkyes.stopTime();
                goToNextEvent();
                break;
                
            case GAME_START_TIME:
                mSkyes.startTime();
                goToNextEvent();
                break;
                
            case MSSG_SHOW:
                
                if(messageManager.lastShownMessage() == -1 && messageManager.isActiveMessage() == false) {
                    messageManager.showMessage(gameEvent.getId());
                } else {
                    if(messageManager.isActiveMessage() == true) {
                        break;
                    } else {
                        messageManager.showMessage(-1);
                        goToNextEvent();
                    }
                }
                break;

            default:

                // TODO: make some actions
                goToNextEvent();
                break;
            }
        }
    }
    
    private void goToNextEvent() {
        Log.i(MainActivity.DEBUG_ID, "Last event was: " + level.getCurrentEvent());
        timeCounter = 0;
        level.nextEvent();
    }

    private void enemiesSharks() {

        for (Enemy enemy : mEnemies.getEnemiesList()) {

            // If player collides with enemy.
            if (Collisions.sptireCircleByCircle(mPlayer, 20, enemy.getX(), enemy.getY(), 20)) {

                if (enemy.isDied() == false) {
                    MainActivity.vibrate(30);
                    mPlayer.decrementHealth();
                    enemy.setDied(true);
                }
            }
        }
    }

    private void updateBullets() {
        // Bullets
        ITextureRegion bulletRegion = MainActivity.resources.getTextureRegion("bullet");
        ITextureRegion bulletBoomRegion = MainActivity.resources.getTextureRegion("bullet_boom");
        ITextureRegion bulletResultRegion;

        for (int i = 0; i < mBullets.size(); i++) {

            BulletUnit bullet = mBullets.get(i);

            for (Enemy enemy : mEnemies.getEnemiesList()) {

                if (bullet.getBoom() == 0 && bullet.collideWithCircle(enemy.getX(), enemy.getY(), enemy.getRadius())) {

                    if (enemy.isDied() == false) {

                        enemy.setHealth(enemy.getHealth() - bullet.getBoomPower());

                        if (enemy.getHealth() <= 0) {

                            enemy.setDied(true);

                            mPlayer.setTotalGold(mPlayer.getTotalGold() + 50);
                            HUD.updateGoldIndicator(mPlayer.getTotalGold());
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
                bulletBatch.draw(bulletResultRegion, bullet.getX() - bulletResultRegion.getWidth() / 2, bullet.getY() - bulletResultRegion.getHeight() / 2,
                        bulletResultRegion.getWidth(), bulletResultRegion.getHeight(), 1, 1, 1, 1, 1);

            }
        }

        bulletBatch.submit();
    }

    private void updateControlls() {

        mPlayer.setBoatSpeed(0);

        if (HUD.isKeyDown(GameHUD.Buttons.LEFT)) {
            mPlayer.setBoatSpeed(mPlayer.getBoatSpeed() - mPlayer.getBoatAcceleration());
        }

        if (HUD.isKeyDown(GameHUD.Buttons.RIGHT)) {
            mPlayer.setBoatSpeed(mPlayer.getBoatSpeed() + mPlayer.getBoatAcceleration());
        }

        if (HUD.isKeyDown(GameHUD.Buttons.FIRE)) {

            if (mBullets.size() < MAX_BULLETS_ON_SCREEN) {

                BulletUnit bullet = mPlayer.fire(bulletTimeCounter);

                if (bullet != null) {
                    mBullets.add(bullet);
                }
            }
        }

    }

    private void endGame() {
        MainActivity.getRootScene().SetState(SceneStates.EndGame);
    }
}
