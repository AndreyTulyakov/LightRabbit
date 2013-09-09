package mhyhre.lightrabbit.game;

import java.util.ArrayList;
import java.util.List;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.Scenes.WaterPolygon;

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
		

		for (int i=0; i<mEnemies.size(); i++) {
			
			Enemy enemy = mEnemies.get(i);
			
			switch (enemy.getEnemyType()) {

			case PIRATE_BOAT:
				break;

			case PIRATE_SHIP:
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

				float bright = shark.getBright();
				draw(sharkRegion, shark.getX(), shark.getY(), shark.getWidth(), shark.getHeight(), 0, bright, bright, bright, bright);
							
				break;

			case UNDEFINED:
				break;
			}
		}
		
		submit();

	}


	public void addNewEnemy(EnemyType enemyType, float centerX, float centerY) {

		switch (enemyType) {

		case PIRATE_BOAT:
			break;

		case PIRATE_SHIP:
			break;

		case SHARK:
			SharkUnit shark = new SharkUnit();
			shark.setCenteredPosition(centerX, centerY);
			shark.setSize(64, 64);
			mEnemies.add(shark);
			break;

		case UNDEFINED:
			break;
		}
	}

	public List<Enemy> getEnemiesList() {
		return mEnemies;
	}

}
