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
import mhyhre.lightrabbit.game.UnitsManager;
import mhyhre.lightrabbit.game.FogRect;
import mhyhre.lightrabbit.game.GameUserInterface;
import mhyhre.lightrabbit.game.GameMessageManager;
import mhyhre.lightrabbit.game.WaterPolygon;
import mhyhre.lightrabbit.game.levels.Level;
import mhyhre.lightrabbit.game.levels.events.Event;
import mhyhre.lightrabbit.game.sky.CloudsManager;
import mhyhre.lightrabbit.game.sky.SkyManager;
import mhyhre.lightrabbit.game.units.Unit;
import mhyhre.lightrabbit.game.units.UnitGenerator;
import mhyhre.lightrabbit.game.units.UnitMoveDirection;
import mhyhre.lightrabbit.game.units.controller.UnitController;
import mhyhre.lightrabbit.game.units.models.UnitModel;
import mhyhre.lightrabbit.game.weapons.BulletsManager;
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
    
    private UnitsManager units;
    private Unit player;
    
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

        сlouds = new CloudsManager(MainActivity.getVboManager());
        water = new WaterPolygon(MainActivity.getVboManager());

        units = new UnitsManager(water, MainActivity.getVboManager());
        player = UnitGenerator.generate(true, level.getPlayerType());
        units.addUnit(player);

        skyes = new SkyManager(MainActivity.getVboManager());
        
        fog = getLevel().getConfiguredFog();
        
        messageManager = new GameMessageManager();
        messageManager.setDialogBase(getLevel().getDialogBase());
        messageManager.setCharacterBase(getLevel().getCharacterBase());
        messageManager.setPictureRegions(getLevel().getPicturesRegions());
        
        bullets = new BulletsManager(water,units);

        attachChild(skyes);
        attachChild(сlouds);
        attachChild(units);
        attachChild(bullets);
        attachChild(water);
        attachChild(hud);
        attachChild(fog);
        
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

        timeCounter += pSecondsElapsed;
        
        updateEvents();
        units.update();
        
        UnitModel playerModel = player.getModel();
        hud.updateHealthIndicator(playerModel.getHealth());
        hud.updateGoldIndicator(playerModel.getGold());
   
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
                if(units.existEnemiesFor(player) == false) {
                    goToNextEvent();
                }
                break;
                
            case WAIT_FOREVER:
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
                units.addUnit(gameEvent);
                goToNextEvent();
                break;
                
            case PLAYER_SET_POSITION:
                player.getModel().setX(gameEvent.getIntegerArg());
                goToNextEvent();
                break;
                
            case UNIT_SET_IDEOLOGY:
                units.unitSetIdeology(gameEvent);
                goToNextEvent();
                break;
                
                
            case UNIT_SET_STOP_POSITION:
                units.unitSetStopPosition(gameEvent);
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
                
            case SET_FOG_VISIBLE:       
                if(fog.showFogEvent(gameEvent)) {
                    goToNextEvent();
                }
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
        
        if(hud.isActivated() == false) {
            return;
        }

        UnitController controller = player.getController();
        
        if (hud.isKeyDown(GameUserInterface.Buttons.LEFT)) {
            controller.accelerate(UnitMoveDirection.LEFT);
        }

        if (hud.isKeyDown(GameUserInterface.Buttons.RIGHT)) {
            controller.accelerate(UnitMoveDirection.RIGHT);
        }
        
        if (hud.isKeyDown(GameUserInterface.Buttons.LEFT) && hud.isKeyDown(GameUserInterface.Buttons.RIGHT)) {
            controller.accelerate(UnitMoveDirection.NONE);
        }

        if (hud.isKeyDown(GameUserInterface.Buttons.FIRE)) {
            // FIXME: gun index by fire button index;
            controller.fireByGun(0);
        }
        
        if (hud.isKeyDown(GameUserInterface.Buttons.JUMP)) {
            controller.jump();
        }
    }

    private void endGame() {
        MainActivity.getRootScene().SetState(SceneStates.EndGame);
    }

    public Level getLevel() {
        return level;
    }
}
