package mhyhre.lightrabbit.game;

import java.util.ArrayList;
import java.util.List;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.events.Event;
import mhyhre.lightrabbit.game.units.PirateBoatUnit;
import mhyhre.lightrabbit.game.units.PirateShipUnit;
import mhyhre.lightrabbit.game.units.SharkUnit;

import org.andengine.entity.sprite.batch.SpriteBatch;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;

public class EnemiesManager extends SpriteBatch {

	public static final int ENEMIES_MAX_COUND = 100;
	private boolean waitState = false;

	WaterPolygon mWater;
	List<Enemy> mEnemies;

	public EnemiesManager(WaterPolygon pWater, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(MainActivity.Res.getTextureAtlas("texture01"), ENEMIES_MAX_COUND, pVertexBufferObjectManager);

		mWater = pWater;
		mEnemies = new ArrayList<Enemy>(ENEMIES_MAX_COUND);
	}

	public void update() {
		
		if(waitState == true){
			if(mEnemies.size() == 0){
				waitState = false;
			}
		}
		
		ITextureRegion sharkRegion = MainActivity.Res.getTextureRegion("shark_body");
		ITextureRegion pirateBoatRegion = MainActivity.Res.getTextureRegion("pirate_boat");
		ITextureRegion pirateShipRegion = MainActivity.Res.getTextureRegion("pirate_ship");
		
		float bright, rotation;

		for (int i=0; i<mEnemies.size(); i++) {
			
			Enemy enemy = mEnemies.get(i);
			
			switch (enemy.getEnemyType()) {

			case PIRATE_BOAT:
				PirateBoatUnit pirateBoat = (PirateBoatUnit)enemy;
				
				pirateBoat.setWaterLevel(mWater.getYPositionOnWave(pirateBoat.getX()) + 2);
				pirateBoat.update();
				
				if(pirateBoat.getY() < 0 || pirateBoat.getX()< -50){
					mEnemies.remove(i);
					i--;
					continue;
				}
				rotation = -mWater.getAngleOnWave(pirateBoat.getX()) / 2.0f;
				bright = pirateBoat.getBright();
				draw(pirateBoatRegion, pirateBoat.getX()-pirateBoat.getWidth()/2, pirateBoat.getY() - pirateBoat.getHeight()/2, pirateBoat.getWidth(), pirateBoat.getHeight(), rotation, bright, bright, bright, bright);
		
				break;

			case PIRATE_SHIP:
				PirateShipUnit pirateShip = (PirateShipUnit)enemy;
				
				pirateShip.setWaterLevel(mWater.getYPositionOnWave(pirateShip.getX()) + 20);
				pirateShip.update();
				
				if(pirateShip.getY() < 0 || pirateShip.getX()< -50){
					mEnemies.remove(i);
					i--;
					continue;
				}
				rotation = -mWater.getAngleOnWave(pirateShip.getX()) / 2.0f;
				bright = pirateShip.getBright();
				draw(pirateShipRegion, pirateShip.getX() - pirateShip.getWidth()/2, pirateShip.getY() - pirateShip.getHeight()/2, pirateShip.getWidth(), pirateShip.getHeight(), rotation, bright, bright, bright, bright);
					
				break;

			case SHARK:
				SharkUnit shark = (SharkUnit)enemy;
				
				shark.setWaterLevel(mWater.getYPositionOnWave(shark.getX()) - 40);
				shark.update();
				
				if(shark.getY() > MainActivity.getHeight() || shark.getX()< -50){
					mEnemies.remove(i);
					i--;
					continue;
				}
				
				bright = shark.getBright();
				draw(sharkRegion, shark.getX()-shark.getWidth()/2, shark.getY() - shark.getHeight()/2, shark.getWidth(), shark.getHeight(), shark.getCurrentRotation(), bright, bright, bright, bright);
							
				break;

			case UNDEFINED:
				break;
			}
		}

		submit();

	}


	public void addNewEnemy( Event event){

		
		if(mEnemies.size() < ENEMIES_MAX_COUND && waitState == false ){
			

			Log.i(MainActivity.DebugID, "addNewEnemy:" + event.getType().getValue() + " Enemy count:" + mEnemies.size());
	
/*			
			
			float xpos = event.getPosition().x + MainActivity.getWidth();
			
			switch (event.getType()) {
	
			case NEW_PIRATE_BOAT:
				PirateBoatUnit pirateBoat = new PirateBoatUnit();
				pirateBoat.setPosition(xpos,0);
				mEnemies.add(pirateBoat);
				break;
	
			case NEW_PIRATE_SHIP:
				PirateShipUnit pirateShip = new PirateShipUnit();
				pirateShip.setPosition(xpos,0);
				mEnemies.add(pirateShip);
				break;
	
			case NEW_SHARK:
				SharkUnit shark = new SharkUnit();
				shark.setPosition(xpos,0);
				mEnemies.add(shark);
				break;

			case WAIT:
				waitState = true;
				break;
				
			default:
				break;
			}
			
			*/
		}
	}

	public List<Enemy> getEnemiesList() {
		return mEnemies;
	}

	public boolean isWaitState() {
		return waitState;
	}

	public void setWaitState(boolean waitState) {
		this.waitState = waitState;
	}

}
