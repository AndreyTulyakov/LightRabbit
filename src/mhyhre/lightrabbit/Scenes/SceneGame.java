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

package mhyhre.lightrabbit.scenes;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.BulletUnit;
import mhyhre.lightrabbit.game.BulletsManager;
import mhyhre.lightrabbit.game.EnemiesManager;
import mhyhre.lightrabbit.game.FogRect;
import mhyhre.lightrabbit.game.GameUserInterface;
import mhyhre.lightrabbit.game.GameMessageManager;
import mhyhre.lightrabbit.game.WaterPolygon;
import mhyhre.lightrabbit.game.levels.Level;
import mhyhre.lightrabbit.game.levels.events.Event;
import mhyhre.lightrabbit.game.sky.CloudsManager;
import mhyhre.lightrabbit.game.sky.SkyManager;
import mhyhre.lightrabbit.game.units.Player;
import mhyhre.lightrabbit.scene.utils.EaseScene;

import org.andengine.input.touch.TouchEvent;





import android.util.Log;

/**
 * This scene control game process.
 */
public class SceneGame extends EaseScene {

    private float timeCounter = 0;
    boolean loaded = true;
    boolean pause = false;

    private GameUserInterface hud;
    private CloudsManager сlouds;
    private SkyManager skyes;
    private EnemiesManager enemies;
    private Player player;
    private WaterPolygon water;

    private GameMessageManager messageManager;
    private FogRect fog;   
    private Level level;
    private BulletsManager bullets;

    public SceneGame(String levelFileName) {

        level = new Level(levelFileName);
        createGameObjects();
        
        Log.i(MainActivity.DEBUG_ID, "Scene game created");
        loaded = true;
    }

    public void start() {
        pause = false;
    }

    public void pause() {
        pause = true;
    }

    private void createGameObjects() {

        hud = new GameUserInterface();

        /* Environment */
        fog = new FogRect();

        сlouds = new CloudsManager(MainActivity.getVboManager());
        water = new WaterPolygon(MainActivity.getVboManager());

        player = new Player(100);
        enemies = new EnemiesManager(water, MainActivity.getVboManager());

        skyes = new SkyManager(MainActivity.getVboManager());
        
        messageManager = new GameMessageManager();
        messageManager.setDialogBase(getLevel().getDialogBase());
        messageManager.setCharacterBase(getLevel().getCharacterBase());
        messageManager.setPictureRegions(getLevel().getPicturesRegions());
        bullets = new BulletsManager(water,enemies,player, hud);

        attachChild(skyes);
        attachChild(сlouds);
        attachChild(enemies);
        attachChild(bullets);
        attachChild(player);
        attachChild(water);
        attachChild(fog);
        
        attachChild(hud);
        attachChild(messageManager);
    }

    @Override
    public boolean onSceneTouchEvent(final TouchEvent pSceneTouchEvent) {

        if (!loaded)
            return true;
        
        if(messageManager.isActiveMessage() == true) {
            hud.resetStates();
            messageManager.onSceneTouchEvent(pSceneTouchEvent);
        } else {
            hud.onSceneTouchEvent(pSceneTouchEvent);
        }
        return super.onSceneTouchEvent(pSceneTouchEvent);
    }

    @Override
    protected void onManagedUpdate(final float pSecondsElapsed) {

        if(pause) {
            return;
        }
        
        updateControlls();
        player.update(water, pSecondsElapsed);

        timeCounter += pSecondsElapsed;
        
        updateEvents();
        enemies.update(player);;
        hud.updateHealthIndicator(player.getCurrentHealth());
        
        if(player.getCurrentHealth() == 0) {
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
                if(enemies.getEnemyCount() == 0) {
                    goToNextEvent();
                }
                break;
                
            case WAIT_ALWAYS:
                break;
                
            case SET_TIME:
                skyes.setCurrentTime(gameEvent.getIntegerArg());
                goToNextEvent();
                break;
                
            case SET_TIME_SPEED:
                skyes.setTimeSpeed(gameEvent.getIntegerArg());
                goToNextEvent();
                break;
                
            case STOP_TIME:
                skyes.stopTime();
                goToNextEvent();
                break;
                
            case STOP_TIME_IN:
                skyes.stopTimeIn(gameEvent.getIntegerArg());
                goToNextEvent();
                break;
                
            case START_TIME:
                skyes.startTime();
                goToNextEvent();
                break;
                
            case UNIT_ADD:
                enemies.addNewEnemy(gameEvent);
                goToNextEvent();
                break;
                
            case MSSG_SHOW:
                if(messageManager.lastShownMessage() == -1 && messageManager.isActiveMessage() == false) {
                    hud.hide();
                    hud.resetTouches();
                    messageManager.showMessage(gameEvent.getId());
                } else {
                    if(messageManager.isActiveMessage() == true) {
                        break;
                    } else {
                        messageManager.showMessage(-1);
                        hud.show();
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
        
        player.setBoatSpeed(0);

        if(hud.isActivated() == false) {
            return;
        }
        
        if (hud.isKeyDown(GameUserInterface.Buttons.LEFT)) {
            player.setBoatSpeed(player.getBoatSpeed() - player.getBoatAcceleration());
        }

        if (hud.isKeyDown(GameUserInterface.Buttons.RIGHT)) {
            player.setBoatSpeed(player.getBoatSpeed() + player.getBoatAcceleration());
        }

        if (hud.isKeyDown(GameUserInterface.Buttons.FIRE)) {
            if(bullets.isCanCreateBullet()) {
                BulletUnit bullet = player.fire();
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
