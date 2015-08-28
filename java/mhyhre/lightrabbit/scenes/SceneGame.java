/*
 * Copyright (C) 2013-2014 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.scenes;

import android.util.Log;

import org.andengine.audio.music.Music;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.color.Color;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.FogRect;
import mhyhre.lightrabbit.game.GameMessageManager;
import mhyhre.lightrabbit.game.GameUserInterface;
import mhyhre.lightrabbit.game.UnitsManager;
import mhyhre.lightrabbit.game.WaterPolygon;
import mhyhre.lightrabbit.game.levels.Level;
import mhyhre.lightrabbit.game.levels.events.Event;
import mhyhre.lightrabbit.game.sky.CloudsManager;
import mhyhre.lightrabbit.game.sky.SkyManager;
import mhyhre.lightrabbit.game.units.Unit;
import mhyhre.lightrabbit.game.units.UnitMoveDirection;
import mhyhre.lightrabbit.game.units.controller.UnitController;
import mhyhre.lightrabbit.game.units.models.UnitModel;
import mhyhre.lightrabbit.game.weapons.BulletsManager;
import mhyhre.lightrabbit.game.weapons.guns.Gun;
import mhyhre.lightrabbit.scenes.utils.EaseScene;
import mhyhre.lightrabbit.scenes.utils.PlayerDeadEventListener;
import mhyhre.lightrabbit.utils.ColorDecoder;
import mhyhre.lightrabbit.utils.GameOverCondition;


/**
 * This scene control game process.
 */
public class SceneGame extends EaseScene implements PlayerDeadEventListener {

    public static final int PLAYER_UNIT_ID = 0;

    private float timeCounter = 0;
    boolean loaded = true;
    boolean pause = false;

    private GameOverCondition gameOverCondition;

    private GameUserInterface hud;
    private CloudsManager clouds;
    private SkyManager skyes;

    private UnitsManager units;
    private WaterPolygon water;

    private GameMessageManager messageManager;
    private FogRect fog;
    private Level level;
    private BulletsManager bullets;


    public SceneGame(String levelFileName) {

        gameOverCondition = GameOverCondition.PLAYER_DEATH;

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

        clouds = new CloudsManager(MainActivity.getVboManager());
        water = new WaterPolygon(MainActivity.getVboManager());

        units = new UnitsManager(water, MainActivity.getVboManager());
        units.setPlayerDeadListener(this);

        skyes = new SkyManager(MainActivity.getVboManager());

        fog = getLevel().getConfiguredFog();

        messageManager = new GameMessageManager();
        messageManager.setDialogBase(getLevel().getDialogBase());
        messageManager.setCharacterBase(getLevel().getCharacterBase());

        bullets = new BulletsManager(water, units);

        attachChild(skyes);
        attachChild(clouds);
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

        if (messageManager.isActiveMessage() == true && messageManager.getCurrentShowMode() != GameMessageManager.ShowMode.SHOW_REPLIC) {
            hud.resetStates();
            messageManager.onSceneTouchEvent(pSceneTouchEvent);
        } else {
            hud.onSceneTouchEvent(pSceneTouchEvent);
        }
        return super.onSceneTouchEvent(pSceneTouchEvent);
    }

    @Override
    protected void onManagedUpdate(final float pSecondsElapsed) {

        if (pause) {
            return;
        }

        updateControlls();

        timeCounter += pSecondsElapsed;
        updateEvents();
        units.update(pSecondsElapsed);


        Unit player = units.getFirstUnitWithId(PLAYER_UNIT_ID);

        if (player != null) {
            UnitModel playerModel = player.getModel();
            hud.updateHealthIndicator(playerModel.getHealth());

            Gun gun = player.getModel().getGun();

            if (gun != null) {
                hud.updateProjectilesIndicator(gun.getProjectilesAmount());
            } else {
                hud.updateProjectilesIndicator(-1);
            }

            Gun secondGun = player.getModel().getSecondGun();

            if (secondGun != null) {
                hud.updateSecondProjectilesIndicator(secondGun.getProjectilesAmount());
            } else {
                hud.updateSecondProjectilesIndicator(-1);
            }
        }
        super.onManagedUpdate(pSecondsElapsed);
    }


    private void updateEvents() {

        Event gameEvent = getLevel().getCurrentEvent();

        // if all events complete
        if (gameEvent == null) {
            endGame();
        } else {
            Unit player;

            switch (gameEvent.getType()) {

                case ENABLE_CLOUDS:
                    clouds.setVisible(true);
                    clouds.setIgnoreUpdate(false);
                    goToNextEvent();
                    break;

                case DISABLE_CLOUDS:
                    clouds.setVisible(false);
                    clouds.setIgnoreUpdate(true);
                    goToNextEvent();
                    break;

                case MAKE_LIGHTNING:
                    skyes.getLightingManager().lightingHit(gameEvent.getIntegerArg(), (int) water.getObjectYPosition(gameEvent.getIntegerArg()));
                    goToNextEvent();
                    break;

                case UNLOCK_LEVEL:
                    MainActivity.Me.getLevelUnlocker().unlockLevel(gameEvent.getIntegerArg());
                    Log.i(MainActivity.DEBUG_ID, "Unlock:" + gameEvent.getIntegerArg());
                    goToNextEvent();
                    break;

                case SET_GAME_OVER_CONTIDION:
                    setGameOverCondition(gameEvent.getStringArg());
                    goToNextEvent();
                    break;

                case SET_WATER_WAVE_HEIGHT:
                    water.setWaveHeight(gameEvent.getIntegerArg());
                    if(water.getWaveHeight() > 25) {
                        Music wave = MainActivity.resources.getMusic("sea");
                        wave.setLooping(true);
                        MainActivity.resources.playMusic("sea");

                    } else {
                        if(MainActivity.resources.getMusic("sea").isPlaying())
                        {
                            MainActivity.resources.getMusic("sea").stop();
                        }
                    }
                    goToNextEvent();
                    break;

                case SET_WATER_WAVE_REPEATING:
                    water.setWaveRepeating(gameEvent.getIntegerArg() / 10.0f);
                    goToNextEvent();
                    break;

                case SET_WATER_COLOR: {
                    Color waterColor = ColorDecoder.convertFromString(gameEvent.getStringArg());
                    water.setColor(waterColor.getRed(), waterColor.getGreen(), waterColor.getBlue(), waterColor.getAlpha());
                    goToNextEvent();
                }
                break;

                case SET_WATER_LEVEL: {
                    water.setWaterLevel(gameEvent.getIntegerArg());
                    goToNextEvent();
                }
                break;

                case END_GAME:
                    endGame();
                    goToNextEvent();
                    break;

                case SKY_SET_PALETTE:
                    skyes.setPalette(gameEvent.getStringArg());
                    goToNextEvent();
                    break;

                case ENABLE_RAIN:
                    Music rain = MainActivity.resources.getMusic("rain");
                    rain.setLooping(true);
                    MainActivity.resources.playMusic("rain");

                    skyes.enableRain();

                    goToNextEvent();
                    break;

                case DISABLE_RAIN:
                    skyes.disableRain();
                    MainActivity.resources.stopMusic("rain");

                    goToNextEvent();
                    break;

                case WAIT_SECONDS:
                    if (((int) timeCounter) >= gameEvent.getIntegerArg()) {
                        goToNextEvent();
                    }
                    break;

                case WAIT_ENEMIES_EXIST:
                    player = units.getFirstUnitWithId(PLAYER_UNIT_ID);
                    if (player == null) {
                        goToNextEvent();
                        Log.i(MainActivity.DEBUG_ID, "WAIT_ENEMIES_EXIST: can't find player!");
                    } else {
                        if (units.existEnemiesFor(player) == false) {
                            goToNextEvent();
                            Log.i(MainActivity.DEBUG_ID, "WAIT_ENEMIES_EXIST: all enemies is died!");
                        }
                    }
                    break;

                case NPC_CHANGE_STATE:
                    units.changeNPCState(gameEvent);
                    goToNextEvent();
                    break;

                case WAIT_WHILE_UNIT_AGENTS_ACTIVE:
                    if (units.hasActiveAgents(gameEvent.getId()) == false) {
                        goToNextEvent();
                    }
                    break;

                case WAIT_WHILE_OTHER_UNITS_EXIST:
                    player = units.getFirstUnitWithId(PLAYER_UNIT_ID);
                    if (player == null) {
                        goToNextEvent();
                    } else {
                        if (units.isOtherUnitsExist(player.getModel().getId()) == false) {
                            goToNextEvent();
                        }
                    }
                    break;

                case WAIT_WHILE_UNIT_ALIVE:
                    if (units.isUnitAlive(gameEvent.getId()) == false) {
                        goToNextEvent();
                    }
                    break;

                case SET_DIE_POSITION:
                    units.setDiePositionXForEveryone(gameEvent.getIntegerArg());
                    goToNextEvent();
                    break;

                case WAIT_FOREVER:
                    break;

                case UNIT_REMOVE:
                    units.removeUnits(gameEvent.getId());
                    goToNextEvent();
                    break;

                case UNIT_SET_GUN:
                    units.setGun(gameEvent.getId(), gameEvent.getStringArg(), gameEvent.getIntegerArg());
                    goToNextEvent();
                    break;

                case UNIT_SET_PROJECTILES_COUNT:
                    units.setProjectilesCount(gameEvent.getId(), gameEvent.getIntegerArg());
                    goToNextEvent();
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

                case NONE:
                    break;

                case UNIT_ADD:
                    units.addUnit(gameEvent);
                    goToNextEvent();
                    break;

                case UNIT_SET_POSITION:
                    units.setUnitPosition(gameEvent.getId(), gameEvent.getIntegerArg());
                    goToNextEvent();
                    break;

                case UNIT_SET_VPOSITION:
                    units.setUnitVPosition(gameEvent.getId(), gameEvent.getIntegerArg());
                    goToNextEvent();
                    break;

                case UNIT_HEALTH:
                    break;

                case UNIT_SPEED:
                    break;

                case UNIT_SET_IDEOLOGY:
                    units.unitSetIdeology(gameEvent);
                    goToNextEvent();
                    break;

                case UNIT_KILL:
                    units.killUnit(gameEvent.getId());
                    goToNextEvent();
                    break;

                case UNIT_SET_STOP_POSITION:
                    units.unitSetStopPosition(gameEvent);
                    goToNextEvent();
                    break;

                case UNIT_SET_DIE_POSITION:
                    units.unitSetDiePosition(gameEvent);
                    goToNextEvent();
                    break;

                case UNIT_SET_FIRE_POSITION:
                    units.unitSetFirePosition(gameEvent);
                    goToNextEvent();
                    break;

                case GO_TO_MAIN_MENU:
                    MainActivity.getRootScene().SetState(SceneStates.MainMenu);
                    break;

                case SHOW_DIALOG:
                    if (messageManager.lastShownMessage() == -1 && messageManager.isActiveMessage() == false) {

                        hud.hide();
                        hud.resetTouches();
                        MainActivity.resources.playSound("message");
                        messageManager.showMessage(gameEvent.getId());

                    } else {

                        if (messageManager.isActiveMessage()) {
                            break;
                        } else {
                            messageManager.showMessage(-1);
                            hud.show();
                            goToNextEvent();
                        }
                    }
                    break;

                case SHOW_TITLES:
                    if (messageManager.lastShownMessage() == -1 && messageManager.isActiveMessage() == false) {

                        hud.hide();
                        hud.resetTouches();
                        messageManager.showTitles(gameEvent.getId());

                    } else {

                        if (messageManager.isActiveMessage()) {
                            break;
                        } else {
                            messageManager.showMessage(-1);
                            hud.show();
                            goToNextEvent();
                        }
                    }
                    break;

                case SHOW_REPLIC:
                    if (messageManager.lastShownMessage() == -1 && messageManager.isActiveMessage() == false) {
                        messageManager.showReplic(gameEvent.getId(), gameEvent.getIntegerArg());
                    } else {

                        if (messageManager.isActiveMessage()) {
                            break;
                        } else {
                            messageManager.showMessage(-1);
                            goToNextEvent();
                        }
                    }
                    break;

                case SET_FOG_VISIBLE:
                    if (fog.showFogEvent(gameEvent)) {
                        goToNextEvent();
                    }
                    break;

                case GUI_SHOW_ELEMENT:
                    hud.showElement(gameEvent.getStringArg());
                    goToNextEvent();
                    break;

                case GUI_HIDE_ELEMENT:
                    hud.hideElement(gameEvent.getStringArg());
                    goToNextEvent();
                    break;

                case GUI_HIDE_ALL_ELEMENTS:
                    hud.hideAllElements();
                    goToNextEvent();
                    break;

                case GUI_BLINK_ELEMENT:
                    hud.blinkElement(gameEvent.getStringArg());
                    goToNextEvent();
                    break;

                case HIDE_TOP_PANEL:
                    hud.hideTopPanel();
                    goToNextEvent();
                    break;

                case SHOW_TOP_PANEL:
                    hud.hideTopPanel();
                    goToNextEvent();
                    break;

                default:
                    Log.i(MainActivity.DEBUG_ID, "Unrecognized event: " + gameEvent.getType());
                    goToNextEvent();
                    break;
            }
            checkGameOverConditions();
        }
    }

    private void goToNextEvent() {
        timeCounter = 0;
        getLevel().nextEvent();
    }


    private void updateControlls() {

        if (hud.isActivated() == false) {
            return;
        }

        Unit player = units.getFirstUnitWithId(PLAYER_UNIT_ID);

        if (player != null) {
            UnitController controller = player.getController();

            if (controller.isReloadGun()) {
                hud.reloadGun();
            } else {
                hud.readyGun();
            }

            if (controller.isReloadSecondGun()) {
                hud.reloadSecondGun();
            } else {
                hud.readySecondGun();
            }

            hud.enableSecondGunIndicators(controller.getModel().getSecondGun() != null);

            if (player.getModel().getControllType() != hud.getControllType()) {
                hud.setControllType(player.getModel().getControllType());
            }

            switch (hud.getControllType()) {
                case LEFT_RIGHT: {
                    int valueX = 0;

                    if (hud.isKeyDown(GameUserInterface.Buttons.LEFT)) {
                        valueX = UnitMoveDirection.LEFT.getVector().x;
                    }

                    if (hud.isKeyDown(GameUserInterface.Buttons.RIGHT)) {
                        valueX = UnitMoveDirection.RIGHT.getVector().x;
                    }

                    if (hud.isKeyDown(GameUserInterface.Buttons.LEFT) && hud.isKeyDown(GameUserInterface.Buttons.RIGHT)) {
                        valueX = 0;
                    }
                    controller.accelerate(valueX, 0);
                }
                break;

                case ALL_DIRECTIONS: {
                    int valueX = 0;
                    if (hud.isKeyDown(GameUserInterface.Buttons.LEFT)) {
                        valueX = UnitMoveDirection.LEFT.getVector().x;
                    }

                    if (hud.isKeyDown(GameUserInterface.Buttons.RIGHT)) {
                        valueX = UnitMoveDirection.RIGHT.getVector().x;
                    }

                    if (hud.isKeyDown(GameUserInterface.Buttons.LEFT) && hud.isKeyDown(GameUserInterface.Buttons.RIGHT)) {
                        valueX = 0;
                    }

                    int valueY = 0;
                    if (hud.isKeyDown(GameUserInterface.Buttons.UP)) {
                        valueY = UnitMoveDirection.UP.getVector().y;
                    }

                    if (hud.isKeyDown(GameUserInterface.Buttons.DOWN)) {
                        valueY = UnitMoveDirection.DOWN.getVector().y;
                    }

                    if (hud.isKeyDown(GameUserInterface.Buttons.UP) && hud.isKeyDown(GameUserInterface.Buttons.DOWN)) {
                        valueY = 0;
                    }
                    controller.accelerate(valueX, valueY);
                }
                break;

                default:
                    Log.w(MainActivity.DEBUG_ID, "Wrong HUD controll type.");
                    break;
            }

            if (hud.isKeyDown(GameUserInterface.Buttons.FIRE)) {
                controller.fireByGun(UnitController.GunIndex.FIRST_GUN);
            }

            if (hud.isKeyDown(GameUserInterface.Buttons.SECOND_FIRE)) {
                controller.fireByGun(UnitController.GunIndex.SECOND_GUN);
            }

            if (hud.isKeyDown(GameUserInterface.Buttons.JUMP)) {
                controller.jump();
            }
        }
    }

    private void checkGameOverConditions() {

        switch (gameOverCondition) {

            case NEVER_ENDING:
                // None
                break;

            case PLAYER_DEATH:
                Unit player = units.getFirstUnitWithId(PLAYER_UNIT_ID);

                if (player == null) {
                    // None. No player - no death.
                } else {

                    UnitModel playerModel = player.getModel();
                    if (playerModel.isDied() || playerModel.getHealth() <= 0) {
                        playerModel.setHealth(0);
                        Log.i(MainActivity.DEBUG_ID, "Player died!");
                        if (playerModel.getBright() < 0.25f) {
                            Log.i(MainActivity.DEBUG_ID, "Player died! end game!");
                            endGame();
                        }
                    }
                }
                break;

            default:
                // None
                break;
        }
    }

    private void setGameOverCondition(String conditionName) {
        try {
            gameOverCondition = GameOverCondition.valueOf(conditionName);
        } catch (IllegalArgumentException e) {
            gameOverCondition = GameOverCondition.NEVER_ENDING;
        }
    }

    public void endGame() {
        MainActivity.resources.stopMusic("rain");
        MainActivity.resources.stopMusic("sea");
        MainActivity.getRootScene().SetState(SceneStates.EndGame);
    }

    public Level getLevel() {
        return level;
    }

    @Override
    public void playerDeadEvent() {
        if (gameOverCondition == GameOverCondition.PLAYER_DEATH) {
            endGame();
        }
    }
}
