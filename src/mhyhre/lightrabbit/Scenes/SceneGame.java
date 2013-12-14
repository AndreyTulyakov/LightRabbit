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
import mhyhre.lightrabbit.game.BulletsManager;
import mhyhre.lightrabbit.game.CloudsManager;
import mhyhre.lightrabbit.game.EnemiesManager;
import mhyhre.lightrabbit.game.Enemy;
import mhyhre.lightrabbit.game.FogRect;
import mhyhre.lightrabbit.game.GameHUD;
import mhyhre.lightrabbit.game.GameMessageManager;
import mhyhre.lightrabbit.game.SkyManager;
import mhyhre.lightrabbit.game.WaterPolygon;
import mhyhre.lightrabbit.game.Levels.Event;
import mhyhre.lightrabbit.game.Levels.Level;
import mhyhre.lightrabbit.game.units.Player;

import org.andengine.entity.sprite.batch.SpriteBatch;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import android.util.Log;

/**
 * This scene control game process.
 */
public class SceneGame extends MhyhreScene {

    private static final int WATER_RESOLUTION = 16;
    private static final int CLOUDS_MAXIMUM = 5;


    float timeCounter = 0;
    float bulletTimeCounter = 0;

    boolean loaded = true;
    boolean pause = false;

    private GameHUD HUD;
    private CloudsManager mClouds;
    private SkyManager mSkyes;
    private EnemiesManager mEnemies;
    private Player mPlayer;
    private WaterPolygon water;
    private GameMessageManager messageManager;
    private FogRect fog;   
    private Level level;
    private BulletsManager bullets;

    public SceneGame(String levelFileName) {

        level = new Level(levelFileName);

        createGameObjects();
        configureGameObjects();

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

    }

    private void createGameObjects() {

        HUD = new GameHUD();

        /* Environment */
        fog = new FogRect();

        mClouds = new CloudsManager(CLOUDS_MAXIMUM, MainActivity.getVboManager());
        water = new WaterPolygon(WATER_RESOLUTION, MainActivity.getVboManager());

        mPlayer = new Player(100);
        mEnemies = new EnemiesManager(water, MainActivity.getVboManager());

        mSkyes = new SkyManager(MainActivity.getVboManager());
        
        messageManager = new GameMessageManager();
        messageManager.setDialogBase(getLevel().getDialogBase());
        messageManager.setCharacterBase(getLevel().getCharacterBase());
        
        bullets = new BulletsManager(water,mEnemies,mPlayer, HUD);

        attachChild(mSkyes);
        attachChild(mClouds);
        attachChild(mEnemies);
        attachChild(bullets);
        attachChild(mPlayer);
        attachChild(water);
        attachChild(fog);
        
        attachChild(HUD);
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

        mEnemies.update(mPlayer);;
        HUD.updateHealthIndicator(mPlayer.getCurrentHealth(), mPlayer.getMaxHealth());
        
        if(mPlayer.getCurrentHealth() == 0) {
            endGame();
        }
        
        super.onManagedUpdate(pSecondsElapsed);
    }
    
    
    private void updateEvents() {

        Event gameEvent = getLevel().getCurrentEvent();

        // if all events complete
        if (gameEvent == null) {
            endGame();

        } else {
            
            switch (gameEvent.getType()) {

            case WAIT_SECONDS:
                if (((int) timeCounter) >= gameEvent.getIntegerArg()) {
                    Log.i(MainActivity.DEBUG_ID, "Last event was: " + getLevel().getCurrentEvent());
                    timeCounter = 0;
                    getLevel().nextEvent();
                }
                break;
                
            case WAIT_ENEMIES_EXIST:           
                if(mEnemies.getEnemyCount() == 0) {
                    goToNextEvent();
                }
                break;
                
            case WAIT_ALWAYS:
                break;
                
            case SET_TIME:
                mSkyes.setCurrentTime(gameEvent.getIntegerArg());
                goToNextEvent();
                break;
                
            case SET_TIME_SPEED:
                mSkyes.setTimeSpeed(gameEvent.getIntegerArg());
                goToNextEvent();
                break;
                
            case STOP_TIME:
                mSkyes.stopTime();
                goToNextEvent();
                break;
                
            case STOP_TIME_IN:
                mSkyes.stopTimeIn(gameEvent.getIntegerArg());
                goToNextEvent();

                break;
                
            case START_TIME:
                mSkyes.startTime();
                goToNextEvent();
                break;
                
            case UNIT_ADD:
                mEnemies.addNewEnemy(gameEvent);
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
                
            case SHOW_FOG:       
                if(fog.showFogEvent(gameEvent)) {
                    goToNextEvent();
                }
                break;
                
            case SET_FOG_VALUE:
                fog.setFogValueEvent(gameEvent);
                goToNextEvent();
                break;
                
            default:
                Log.i(MainActivity.DEBUG_ID, "Unrecognized event: " + gameEvent.getType());
                goToNextEvent();
                break;
            }
        }
    }
    
    private void goToNextEvent() {
        Log.i(MainActivity.DEBUG_ID, "Last event was: " + getLevel().getCurrentEvent());
        timeCounter = 0;
        getLevel().nextEvent();
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

            if(bullets.isCanCreateBullet()) {
                BulletUnit bullet = mPlayer.fire(bulletTimeCounter);
                if (bullet != null) { 
                    bullets.addBullet(bullet);
                }
            }
        }

    }

    private void endGame() {
        MainActivity.getRootScene().SetState(SceneStates.EndGame);
    }

    public Level getLevel() {
        return level;
    }
}
