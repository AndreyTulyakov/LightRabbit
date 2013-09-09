package mhyhre.lightrabbit.game;

import java.util.ArrayList;
import java.util.List;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.units.PirateBoatUnit;
import mhyhre.lightrabbit.game.units.PirateShipUnit;
import mhyhre.lightrabbit.game.units.SharkUnit;

import org.andengine.entity.sprite.batch.SpriteBatch;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class EnemiesManager extends SpriteBatch {

	public static final int ENEMIES_MAX_COUND = 100;

	WaterPolygon mWater;
	List<Enemy> mEnemies;

	public EnemiesManager(WaterPolygon pWater, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(MainActivity.Res.getTextureAtlas("texture01"), ENEMIES_MAX_COUND, pVertexBufferObjectManager);

		mWater = pWater;
		mEnemies = new ArrayList<Enemy>(ENEMIES_MAX_COUND);
	}

	public void update() {
		
		ITextureRegion sharkRegion = MainActivity.Res.getTextureRegion("shark_body");
		ITextureRegion pirateBoatRegion = MainActivity.Res.getTextureRegion("pirate_boat");
		ITextureRegion pirateShipRegion = MainActivity.Res.getTextureRegion("pirate_ship");
		
		float bright, rotation;

		for (int i=0; i<mEnemies.size(); i++) {
			
			Enemy enemy = mEnemies.get(i);
			
			switch (enemy.getEnemyType()) {

			case PIRATE_BOAT:
				PirateBoatUnit pirateBoat = (PirateBoatUnit)enemy;
				
				pirateBoat.setWaterLevel(mWater.getYPositionOnWave(pirateBoat.getCX()));
				pirateBoat.update();
				
				if(pirateBoat.getY() > MainActivity.getHeight() || pirateBoat.getCX()< -50){
					mEnemies.remove(i);
					i--;
					continue;
				}
				rotation = mWater.getAngleOnWave(pirateBoat.getCX()) / 2.0f;
				bright = pirateBoat.getBright();
				draw(pirateBoatRegion, pirateBoat.getX(), pirateBoat.getY(), pirateBoat.getWidth(), pirateBoat.getHeight(), rotation, bright, bright, bright, bright);
							
				break;

			case PIRATE_SHIP:
				PirateShipUnit pirateShip = (PirateShipUnit)enemy;
				
				pirateShip.setWaterLevel(mWater.getYPositionOnWave(pirateShip.getCX()));
				pirateShip.update();
				
				if(pirateShip.getY() > MainActivity.getHeight() || pirateShip.getCX()< -50){
					mEnemies.remove(i);
					i--;
					continue;
				}
				rotation = mWater.getAngleOnWave(pirateShip.getCX()) / 2.0f;
				bright = pirateShip.getBright();
				draw(pirateShipRegion, pirateShip.getX(), pirateShip.getY(), pirateShip.getWidth(), pirateShip.getHeight(), rotation, bright, bright, bright, bright);
					
				break;

			case SHARK:
				SharkUnit shark = (SharkUnit)enemy;
				
				shark.setWaterLevel(mWater.getYPositionOnWave(shark.getCX()));
				shark.update();
				
				if(shark.getY() > MainActivity.getHeight() || shark.getCX()< -50){
					mEnemies.remove(i);
					i--;
					continue;
				}
				
				bright = shark.getBright();
				draw(sharkRegion, shark.getX(), shark.getY(), shark.getWidth(), shark.getHeight(), shark.getCurrentRotation(), bright, bright, bright, bright);
							
				break;

			case UNDEFINED:
				break;
			}
		}
		
		submit();

	}


	public void addNewEnemy(EnemyType enemyType, float centerX, float centerY) {

		if(mEnemies.size() < ENEMIES_MAX_COUND){
			
			switch (enemyType) {
	
			case PIRATE_BOAT:
				PirateBoatUnit pirateBoat = new PirateBoatUnit();
				pirateBoat.setCenteredPosition(centerX, centerY);
				mEnemies.add(pirateBoat);
				break;
	
			case PIRATE_SHIP:
				PirateShipUnit pirateShip = new PirateShipUnit();
				pirateShip.setCenteredPosition(centerX, centerY);
				mEnemies.add(pirateShip);
				break;
	
			case SHARK:
				SharkUnit shark = new SharkUnit();
				shark.setCenteredPosition(centerX, centerY);
				mEnemies.add(shark);
				break;
	
			case UNDEFINED:
				break;
			}
		}
	}

	public List<Enemy> getEnemiesList() {
		return mEnemies;
	}

}
