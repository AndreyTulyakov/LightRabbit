package mhyhre.lightrabbit.game;

import java.util.ArrayList;
import java.util.List;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.levels.events.Event;
import mhyhre.lightrabbit.game.levels.events.EventType;
import mhyhre.lightrabbit.game.units.DirigibleUnit;
import mhyhre.lightrabbit.game.units.PirateBoatUnit;
import mhyhre.lightrabbit.game.units.PirateShipUnit;
import mhyhre.lightrabbit.game.units.Player;
import mhyhre.lightrabbit.game.units.SharkUnit;

import org.andengine.entity.sprite.batch.SpriteBatch;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class EnemiesManager extends SpriteBatch {

    public static final int ENEMIES_MAX_COUND = 100;
    WaterPolygon mWater;
    List<Enemy> mEnemies;

    public EnemiesManager(WaterPolygon pWater, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(MainActivity.resources.getTextureAtlas("texture01"), ENEMIES_MAX_COUND, pVertexBufferObjectManager);

        mWater = pWater;
        mEnemies = new ArrayList<Enemy>(ENEMIES_MAX_COUND);
    }
    
    private void calculateCollisionsWithEnemies(Player player) {

        for (Enemy enemy : getEnemiesList()) {

            // If player collides with enemy.
            if (Collisions.spriteByCircle(player, enemy.getX(), enemy.getY(), 36)) {

                if (enemy.isDied() == false) {
                    MainActivity.vibrate(30);
                    player.decrementHealth();
                    enemy.setDied(true);
                }
            }
        }
    }

    public void update(Player player) {
        
        
        calculateCollisionsWithEnemies(player);

        ITextureRegion sharkRegion = MainActivity.resources.getTextureRegion("shark_body");
        ITextureRegion pirateBoatRegion = MainActivity.resources.getTextureRegion("pirate_boat");
        ITextureRegion pirateShipRegion = MainActivity.resources.getTextureRegion("pirate_ship");
        ITextureRegion dirigibleRegion =  MainActivity.resources.getTextureRegion("dirigible");

        float bright, rotation;

        for (int i = 0; i < mEnemies.size(); i++) {

            Enemy enemy = mEnemies.get(i);

            switch (enemy.getEnemyType()) {

            case PIRATE_BOAT:
                PirateBoatUnit pirateBoat = (PirateBoatUnit) enemy;

                pirateBoat.setWaterLevel(mWater.getObjectYPosition(pirateBoat.getX()) + 2);
                pirateBoat.update();

                if (pirateBoat.getY() < 0 || pirateBoat.getX() < -50) {
                    mEnemies.remove(i);
                    i--;
                    continue;
                }
                rotation = -mWater.getObjectAngle(pirateBoat.getX()) / 2.0f;
                drawEnemy(pirateBoatRegion, pirateBoat, pirateBoat.getBright(), rotation);
                break;

            case PIRATE_SHIP:
                PirateShipUnit pirateShip = (PirateShipUnit) enemy;

                pirateShip.setWaterLevel(mWater.getObjectYPosition(pirateShip.getX()) + 20);
                pirateShip.update();

                if (pirateShip.getY() < 0 || pirateShip.getX() < -50) {
                    mEnemies.remove(i);
                    i--;
                    continue;
                }
                rotation = -mWater.getObjectAngle(pirateShip.getX()) / 2.0f;
                drawEnemy(pirateShipRegion, pirateShip, pirateShip.getBright(), rotation);
                break;

            case SHARK:
                SharkUnit shark = (SharkUnit) enemy;

                shark.setWaterLevel(mWater.getObjectYPosition(shark.getX()) - 40);
                shark.update();
                
                boolean needRemoveEnemy = (shark.getY() > MainActivity.getHeight() || shark.getX() < -50);
                needRemoveEnemy |= shark.isDied() && shark.getBright() == 0; 

                if (needRemoveEnemy) {
                    mEnemies.remove(i);
                    i--;
                    continue;
                }

                bright = shark.getBright();
                drawEnemy(sharkRegion, shark, bright, 0, 4);
                break;
                
            case DIRIGIBLE:
                DirigibleUnit dirigible = (DirigibleUnit) enemy;
                dirigible.update();
                
                boolean needRemoveDirigible = dirigible.getX() < -200;
                needRemoveDirigible |= dirigible.isDied(); 

                if (needRemoveDirigible) {
                    mEnemies.remove(i);
                    i--;
                    continue;
                }

                drawEnemy(dirigibleRegion, dirigible, 1, 0, 4);

            case UNDEFINED:
                break;
            }
        }

        submit();

    }
    
    
    private void drawEnemy(ITextureRegion region, Enemy enemy, float bright, float currentRotation) {
        draw(region, enemy.getX() - region.getWidth() / 2, enemy.getY() - region.getHeight() / 2, region.getWidth(), region.getHeight(),
                currentRotation, bright, bright, bright, bright);
    }
    
    private void drawEnemy(ITextureRegion region, Enemy enemy, float bright, float currentRotation, float scale) {
        draw(region, enemy.getX() - region.getWidth() / 2, enemy.getY() - region.getHeight() / 2, region.getWidth(), region.getHeight(),
                currentRotation, scale, scale, bright, bright, bright, bright);
    }

    public void addNewEnemy(Event event) {

        if (event.getType() == EventType.UNIT_ADD) {
            if (mEnemies.size() < ENEMIES_MAX_COUND) {

                float xpos = event.getIntegerArg() + MainActivity.getWidth();
                EnemyType type = EnemyType.getByName(event.getStringArg());

                switch (type) {

                case SHARK:
                    SharkUnit shark = new SharkUnit();
                    shark.setPosition(xpos, 0);
                    mEnemies.add(shark);
                    break;
                    
                case DIRIGIBLE:
                    DirigibleUnit dirigible = new DirigibleUnit();
                    dirigible.setPosition(xpos, 0);
                    mEnemies.add(dirigible);
                    break;
                
                case PIRATE_SHIP: 
                    PirateShipUnit pirateShip = new PirateShipUnit();
                    pirateShip.setPosition(xpos, 0);
                    mEnemies.add(pirateShip);
                    break;
                
                default:
                    break;
                }

            }
        }
    }

    public int getEnemyCount() {
        return mEnemies.size();
    }

    public List<Enemy> getEnemiesList() {
        return mEnemies;
    }
    /*
     * public boolean isWaitState() { return waitState; }
     * 
     * public void setWaitState(boolean waitState) { this.waitState = waitState; }
     */

}
