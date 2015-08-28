/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game;

import android.util.Log;

import org.andengine.entity.sprite.batch.SpriteBatch;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.util.ArrayList;
import java.util.List;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.levels.events.Event;
import mhyhre.lightrabbit.game.levels.events.EventType;
import mhyhre.lightrabbit.game.units.Unit;
import mhyhre.lightrabbit.game.units.UnitGenerator;
import mhyhre.lightrabbit.game.units.UnitIdeology;
import mhyhre.lightrabbit.game.units.UnitMoveDirection;
import mhyhre.lightrabbit.game.units.UnitType;
import mhyhre.lightrabbit.game.units.agents.UnitDieAgent;
import mhyhre.lightrabbit.game.units.agents.UnitFireAgent;
import mhyhre.lightrabbit.game.units.agents.UnitStopAgent;
import mhyhre.lightrabbit.game.units.controller.NPCController;
import mhyhre.lightrabbit.game.units.models.UnitModel;
import mhyhre.lightrabbit.game.weapons.GunType;
import mhyhre.lightrabbit.game.weapons.guns.AutoGun;
import mhyhre.lightrabbit.game.weapons.guns.BarrelsKicker;
import mhyhre.lightrabbit.game.weapons.guns.Bomber;
import mhyhre.lightrabbit.game.weapons.guns.FastBomber;
import mhyhre.lightrabbit.game.weapons.guns.Gun;
import mhyhre.lightrabbit.game.weapons.guns.Gun150;
import mhyhre.lightrabbit.game.weapons.guns.Gun90;
import mhyhre.lightrabbit.scenes.SceneGame;
import mhyhre.lightrabbit.scenes.utils.PlayerDeadEventListener;

public class UnitsManager extends SpriteBatch {

    private static final float COLLISIONS_DAMAGE_FACTOR = 0.1f;
    public static final int UNITS_MAX_COUNT = 100;
    private static final int MINIMAX_UNIT_X = -3000;
    private int diePositionXForEveryone;

    private WaterPolygon water;
    private List<Unit> units;
    private static UnitsManager instance = null;

    public static UnitsManager getInstance() {
        if(instance == null) {
            Log.e(MainActivity.DEBUG_ID, "UnitsManager::getInstance(): null instance!");
        }
        return instance;
    }
    
    private PlayerDeadEventListener playerDeadListener;
    

    public UnitsManager(WaterPolygon pWater, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(MainActivity.resources.getTextureAtlas("texture01"), UNITS_MAX_COUNT, pVertexBufferObjectManager);
        instance = this;
        water = pWater;
        units = new ArrayList<Unit>(UNITS_MAX_COUNT);
        diePositionXForEveryone = MINIMAX_UNIT_X;
    }
    

    public void setPlayerDeadListener(PlayerDeadEventListener playerDeadListener) {
        this.playerDeadListener = playerDeadListener;
    }


    private void calculateCollisionsBetweenUnits() {

        List<Unit> units = getUnitsList();

        for (int targetIndex = 0; targetIndex < units.size(); targetIndex++) {
            for (int otherIndex = targetIndex + 1; otherIndex < units.size(); otherIndex++) {

                UnitModel targetModel = units.get(targetIndex).getModel();
                UnitModel otherModel = units.get(otherIndex).getModel();

                if (targetModel.getIdeology() != otherModel.getIdeology()) {

                    if (targetModel.isDied() || otherModel.isDied()) {
                        // Do nothing
                    } else {
                        if (Collisions.modelAndModel(targetModel, otherModel)) {
                            MainActivity.vibrate(5);
                            applyCollisionDamage(COLLISIONS_DAMAGE_FACTOR, targetModel, otherModel);
                        }
                    }
                }
            }
        }
    }

    
    private void applyCollisionDamage(float damageFactor, UnitModel first, UnitModel second) {

        int firstDamagePower = (int) Math.sqrt(first.getDamagePower() * damageFactor);
        int secondDamagePower = (int) Math.sqrt(second.getDamagePower() * damageFactor);

        first.setHealth(first.getHealth() - secondDamagePower);
        second.setHealth(second.getHealth() - firstDamagePower);

        if (first.getHealth() <= 0) {
            first.setHealth(0);
            Log.i(MainActivity.DEBUG_ID, "Unit:" + first.getType().getName() + " died by collision.");
            first.setDied(true);
        }

        if (second.getHealth() <= 0) {
            second.setHealth(0);
            Log.i(MainActivity.DEBUG_ID, "Unit:" + second.getType().getName() + " died by collision.");
            second.setDied(true);
        }

    }

    
    
    public void update(float time) {

        calculateCollisionsBetweenUnits();

        for (int i = 0; i < units.size(); i++) {

            Unit unit = units.get(i);
            UnitModel model = unit.getModel();
            model.update(water);
            unit.getController().update(time);
            drawUnit(unit);

            // Die 'line' in position for all units.
            if (Math.abs(model.getX() - diePositionXForEveryone) < 10) {
                model.setDied(true);
            }

            // if out of scene
            if (model.getY() < 0 || model.getX() < MINIMAX_UNIT_X) {
                if(playerDeadListener != null && units.get(i).getModel().getId() == SceneGame.PLAYER_UNIT_ID) {
                    playerDeadListener.playerDeadEvent();
                }
                units.remove(i);
                i--;
                continue;
            }

            // for more fast unit termination
            if (model.isDied() && model.getBright() < 0.01f) {
                if(playerDeadListener != null && units.get(i).getModel().getId() == SceneGame.PLAYER_UNIT_ID) {
                    playerDeadListener.playerDeadEvent();
                }
                units.remove(i);
                Log.i(MainActivity.DEBUG_ID, "Units Manager: unit was removed:" + model.getType().getName());
                i--;
                continue;
            }
        }

        submit();

    }

    private void drawUnit(Unit unit) {

        float directionScale;

        UnitModel model = unit.getModel();

        if (model.getMoveDirection() == UnitMoveDirection.RIGHT) {
            directionScale = -MainActivity.PIXEL_MULTIPLIER;
        } else {
            directionScale = MainActivity.PIXEL_MULTIPLIER;
        }

        UnitType type = model.getType();
        ITextureRegion unitRegion = MainActivity.resources.getTextureRegion(type.getName());

        float bright = model.getBright();

        draw(unitRegion, model.getX() - unitRegion.getWidth() / 2, model.getY() - unitRegion.getHeight() / 2, unitRegion.getWidth(), unitRegion.getHeight(),
                model.getRotation(), directionScale, MainActivity.PIXEL_MULTIPLIER, bright, bright, bright, bright);
    }

    public void addUnit(Unit unit) {
        if (unit != null) {
            Log.i(MainActivity.DEBUG_ID, "UnitManager:addUnit:" + unit.getModel().getType());
            units.add(unit);
        }
    }

    public void addUnit(Event event) {

        if (event.getType() == EventType.UNIT_ADD) {
            if (units.size() < UNITS_MAX_COUNT) {

                float xpos = event.getIntegerArg() + MainActivity.getWidth();
                UnitType type = UnitType.getByName(event.getStringArg());

                UnitMoveDirection moveDirection = UnitMoveDirection.LEFT;

                try {
                    moveDirection = UnitMoveDirection.valueOf(event.getSecondStringArg());

                } catch (IllegalArgumentException e) {
                    moveDirection = UnitMoveDirection.LEFT;
                }

                boolean isPlayer = event.getId() == SceneGame.PLAYER_UNIT_ID;
                Unit unit = UnitGenerator.generate(isPlayer, type, event.getId());
                unit.getModel().setPosition(xpos, unit.getModel().getHeightLevel());
                unit.getModel().setMoveDirection(moveDirection);
                units.add(unit);

                Log.i(MainActivity.DEBUG_ID, "UnitManager:addUnit:" + unit.getModel().getType());
            }
        }
    }

    public void unitSetIdeology(Event event) {
        if (event.getType() == EventType.UNIT_SET_IDEOLOGY) {

            for (Unit unit : units) {
                UnitModel model = unit.getModel();

                if (model.getId() == event.getId()) {
                    try {
                        model.setIdeology(UnitIdeology.valueOf(event.getStringArg()));

                    } catch (IllegalArgumentException e) {
                        Log.w(MainActivity.DEBUG_ID, "UnitManager: can't set unit ideology!");
                    }

                }
            }
        }
    }

    public void unitSetStopPosition(Event event) {

        if (event.getType() == EventType.UNIT_SET_STOP_POSITION) {

            for (Unit unit : units) {
                if (unit.getModel().getId() == event.getId()) {
                    UnitStopAgent stopAgent = new UnitStopAgent(unit, event.getIntegerArg(), 17);
                    unit.getModel().addAgent(stopAgent);
                }
            }
        }
    }
    
    
    public void unitSetFirePosition(Event event) {

        if (event.getType() == EventType.UNIT_SET_FIRE_POSITION) {

            for (Unit unit : units) {
                if (unit.getModel().getId() == event.getId()) {
                    UnitFireAgent fireAgent = new UnitFireAgent(unit, event.getIntegerArg(), 8);
                    unit.getModel().addAgent(fireAgent);
                }
            }
        }
    }
    
    public void unitSetDiePosition(Event event) {

        if (event.getType() == EventType.UNIT_SET_DIE_POSITION) {

            for (Unit unit : units) {
                if (unit.getModel().getId() == event.getId()) {
                    UnitDieAgent dieAgent = new UnitDieAgent(unit, event.getIntegerArg(), 25);
                    unit.getModel().addAgent(dieAgent);
                }
            }
        }
    }

    public int getEnemyCount(UnitIdeology unitIdeology) {
        return units.size();
    }

    public List<Unit> getUnitsList() {
        return units;
    }

    public boolean existEnemiesFor(Unit targetUnit) {
        
        UnitIdeology targetIdeology = targetUnit.getModel().getIdeology();

        for (Unit unit : units) {
            UnitIdeology otherIdeology = unit.getModel().getIdeology();
            
            if(unit != targetUnit) {
                if (targetIdeology.isEnemy(otherIdeology)) {
                    Log.i(MainActivity.DEBUG_ID, "existEnemies:" + unit.getModel().getType().getName());
                    return true;
                }
            }
        }
        return false;
    }

    public void changeNPCState(Event gameEvent) {

        for (Unit unit : units) {

            if (unit.getModel().getId() == gameEvent.getId()) {
                if (unit.getController().isPlayerController() == false) {

                    NPCController controller = (NPCController) unit.getController();
                    controller.setStateByEvent(gameEvent);
                }
            }
        }
    }

    public boolean hasActiveAgents(int id) {

        for (Unit unit : units) {

            if (unit.getModel().getId() == id) {
                if (unit.getModel().hasActiveAgents()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setDiePositionXForEveryone(int diePositionXForEveryone) {
        this.diePositionXForEveryone = diePositionXForEveryone;
    }

    public void setGun(int id, String stringArg, int integerArg) {

        try {
            GunType type = GunType.valueOf(stringArg);

            for (Unit unit : units) {
                UnitModel model = unit.getModel();

                if (model.getId() == id) {

                    switch (type) {

                    case BULLET_GUN_150:
                        model.setGun(new Gun150(model, integerArg));
                        break;

                    case BULLET_GUN_90:
                        model.setGun(new Gun90(model, integerArg));
                        break;
                        
                    case AUTOMATIC_GUN:
                        model.setGun(new AutoGun(model, integerArg));
                        break;
                        
                    case BARRELS_KICKER:
                        model.setGun(new BarrelsKicker(model, integerArg));
                        break;
                        
                    case BOMBER:
                        model.setGun(new Bomber(model, integerArg));
                        break;                       
                        
                    case FAST_BOMBER:
                        model.setGun(new FastBomber(model, integerArg));
                        break; 
                        
                    default:
                        Log.w(MainActivity.DEBUG_ID, "UnitsManager::setGun: not implemented gun!");
                        break;
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            Log.e(MainActivity.DEBUG_ID, "UnitsManager::setGun:wrong argument!");
        }
    }

    public void setProjectilesCount(int id, int integerArg) {

        for (Unit unit : units) {
            UnitModel model = unit.getModel();

            if (model.getId() == id) {
                Gun gun = model.getGun();
                if (gun != null) {
                    gun.setProjectilesAmount(integerArg);
                }
            }
        }
    }

    public boolean isOtherUnitsExist(int id) {

        for (Unit unit : units) {

            if (unit.getModel().getId() != id) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isUnitAlive(int id) {
        for (Unit unit : units) {
            if (unit.getModel().getId() == id) {
                if(unit.getModel().isDied() == false) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setUnitPosition(int id, int integerArg) {
        for (Unit unit : units) {
            if (unit.getModel().getId() == id) {
                unit.getModel().setX(integerArg);
            }
        }
    }
    
    
    public void setUnitVPosition(int id, int integerArg) {
        for (Unit unit : units) {
            if (unit.getModel().getId() == id) {
                unit.getModel().setHeightLevel(integerArg);
            }
        }
    }


    public Unit getFirstUnitWithId(int playerId) {
        for (Unit unit : units) {
            if (unit.getModel().getId() == playerId) {
                return unit;
            }
        }
        return null;
    }

    public void removeUnits(int id) {
        for(int index = 0; index < units.size(); index++) {
            if(units.get(index).getModel().getId() == id) {
                units.remove(index);
                index--;
            }
        }
    }

    public void killUnit(int id) {
        for (Unit unit : units) {
            if (unit.getModel().getId() == id) {
                unit.getModel().setDied(true);
                unit.getModel().setHealth(0);
            }
        }
    }

}
