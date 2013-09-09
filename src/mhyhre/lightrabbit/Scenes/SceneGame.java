/*
 * Copyright (C) 2013 Andrew Tulay
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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.MhyhreScene;
import mhyhre.lightrabbit.game.BulletUnit;
import mhyhre.lightrabbit.game.CloudsManager;
import mhyhre.lightrabbit.game.EnemiesManager;
import mhyhre.lightrabbit.game.Enemy;
import mhyhre.lightrabbit.game.EnemyType;
import mhyhre.lightrabbit.game.SkyManager;
import mhyhre.lightrabbit.game.WaterPolygon;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.batch.SpriteBatch;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import android.util.Log;

public class SceneGame extends MhyhreScene {

	private Background mBackground;


	Sprite spriteMoveRight, spriteMoveLeft, spriteFire, boat, spriteGold;
	SpriteBatch healthIndicator, bulletBatch;
	
	List<BulletUnit> mBullets;
	
	Text textGold;
	
	CloudsManager mClouds;
	SkyManager mSkyes;
	EnemiesManager mEnemies;

	private WaterPolygon water;

	private int totalGold = 100;
	final int maxHealth = 3;
	int currentHealth = 3;

	private float boatSpeed = 0;
	private float boatAcseleration = 3;

	// Resources
	public static final String uiAtlasName = "User_Interface";

	public static Map<String, ITextureRegion> TextureRegions;

	
	public SceneGame() {

		mBackground = new Background(0.8f, 0.8f, 0.8f);
		setBackground(mBackground);
		setBackgroundEnabled(true);

		createTextureRegions();

		createGameObjects();
		
		createGUI();

		Log.i(MainActivity.DebugID, "Scene game created");
	}

	private static void createTextureRegions() {

		TextureRegions = new HashMap<String, ITextureRegion>();
		BitmapTextureAtlas atlas = MainActivity.Res.getTextureAtlas(uiAtlasName);

		TextureRegions.put("Button", TextureRegionFactory.extractFromTexture(atlas, 0, 0, 310, 70, false));
		TextureRegions.put("Equall", TextureRegionFactory.extractFromTexture(atlas, 325, 0, 45, 70, false));

		TextureRegions.put("Left", TextureRegionFactory.extractFromTexture(atlas, 0, 72, 80, 80, false));
		TextureRegions.put("Right", TextureRegionFactory.extractFromTexture(atlas, 80, 72, 80, 80, false));

		TextureRegions.put("Menu", TextureRegionFactory.extractFromTexture(atlas, 160, 70, 74, 74, false));

		TextureRegions.put("Fire", TextureRegionFactory.extractFromTexture(atlas, 86, 160, 64, 64, false));
	}
	
	private void createGUI(){


		
		spriteMoveLeft = new Sprite(0,0, TextureRegions.get("Left"), MainActivity.Me.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
					boatSpeed = -boatAcseleration;
				}

				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
					boatSpeed = 0;
				}

				return true;
			}
		};
		spriteMoveLeft.setPosition(40, MainActivity.getHeight() - (50 + spriteMoveLeft.getHeight()/2));
		spriteMoveLeft.setVisible(true);
		attachChild(spriteMoveLeft);
		registerTouchArea(spriteMoveLeft);

		spriteMoveRight = new Sprite(0, 0 , TextureRegions.get("Right"), MainActivity.Me.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
					boatSpeed = boatAcseleration;
				}

				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
					boatSpeed = 0;
				}

				return true;
			}
		};
		spriteMoveRight.setPosition(spriteMoveLeft.getX() + spriteMoveLeft.getWidth() + 20, MainActivity.getHeight() - (50 + spriteMoveRight.getHeight()/2));
		spriteMoveRight.setVisible(true);
		attachChild(spriteMoveRight);
		registerTouchArea(spriteMoveRight);

		spriteFire = new Sprite(0, 0, TextureRegions.get("Fire"), MainActivity.Me.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
					MainActivity.vibrate(30);

					BulletUnit bullet = new BulletUnit(boat.getX() + boat.getWidth()-15, boat.getY());
					bullet.setAccelerationByAngle(boat.getRotation()-15, 8);

					mBullets.add(bullet);
				}

				return true;
			}
		};
		
		spriteFire.setPosition(MainActivity.getWidth() - (spriteFire.getWidth()+40), MainActivity.getHeight() - (50 + spriteFire.getHeight()/2));
		spriteFire.setVisible(true);
		attachChild(spriteFire);
		registerTouchArea(spriteFire);
	}

	private void createGameObjects(){
		
		mBullets = new LinkedList<BulletUnit>();

		
		mClouds = new CloudsManager(5, MainActivity.Me.getVertexBufferObjectManager());
		
		water = new WaterPolygon(16, MainActivity.Me.getVertexBufferObjectManager());
		
		boat = new Sprite(100, 100, MainActivity.Res.getTextureRegion("boat_body"), MainActivity.Me.getVertexBufferObjectManager());
		
		
		
		healthIndicator = new SpriteBatch(MainActivity.Res.getTextureAtlas("texture01"), 10, MainActivity.Me.getVertexBufferObjectManager());
		
		mEnemies = new EnemiesManager(water ,MainActivity.Me.getVertexBufferObjectManager());

		bulletBatch = new SpriteBatch(MainActivity.Res.getTextureAtlas("texture01"), 50, MainActivity.Me.getVertexBufferObjectManager());
		
		mSkyes = new SkyManager(mBackground, water, MainActivity.Me.getVertexBufferObjectManager());
		
		spriteGold = new Sprite(300, 10, MainActivity.Res.getTextureRegion("gold"), MainActivity.Me.getVertexBufferObjectManager());
		
		textGold = new Text(340, 10, MainActivity.Res.getFont("White Furore"), String.valueOf(totalGold), 20, MainActivity.Me.getVertexBufferObjectManager());
		textGold.setPosition(340, 22 - textGold.getHeight()/2);
		
		attachChild(mSkyes);
		attachChild(mClouds);
		attachChild(mEnemies);
		attachChild(bulletBatch);
		attachChild(boat);
		attachChild(water);
		
		attachChild(healthIndicator);
		attachChild(spriteGold);
		attachChild(textGold);
		
	}
	
	@Override
	public boolean onSceneTouchEvent(final TouchEvent pSceneTouchEvent) {

		return super.onSceneTouchEvent(pSceneTouchEvent);
	}

	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {

		if (boat.getX() > (MainActivity.getWidth() - 32 - boat.getWidth()) && boatSpeed > 0)
			boatSpeed = 0;

		if (boat.getX() < 32 && boatSpeed < 0)
			boatSpeed = 0;

		boat.setX(boat.getX() + boatSpeed);
		boat.setY(water.getYPositionOnWave(boat.getX() + boat.getWidth() / 2) - boat.getHeight() / 2 - 5);
		boat.setRotation(water.getAngleOnWave(boat.getX()) / 2.0f);

		// Generate new sharks
		if(mEnemies.getEnemiesList().size() < 1){
			
			mEnemies.addNewEnemy(EnemyType.PIRATE_SHIP, 1000, 0);
			
			 Random rand = new Random();
			 float r = rand.nextInt(5)+1;
			 for(int i=0; i< r ; i++){
				 float x = MainActivity.getWidth() + 10 + rand.nextInt((int) MainActivity.getWidth());
				 mEnemies.addNewEnemy(EnemyType.SHARK, x, 0);
				 
				 x = MainActivity.getWidth() + 10 + rand.nextInt((int) MainActivity.getWidth());
				 mEnemies.addNewEnemy(EnemyType.PIRATE_BOAT, x, 0);
			 }
		}

		updateBullets();
		
		mEnemies.update();
		
		enemiesSharks();
		
		updateHealthIndicator();

		super.onManagedUpdate(pSecondsElapsed);
	}
		
	private void updateHealthIndicator(){
		for (int i = 0; i < maxHealth; i++) {
			if (i < currentHealth) {
				healthIndicator.draw(MainActivity.Res.getTextureRegion("heart"), 40 + i * 36, 10, 32, 32, 0, 1, 1, 1, 1);
			} else {
				healthIndicator.draw(MainActivity.Res.getTextureRegion("heart_died"), 40 + i * 36, 10, 32, 32, 0, 1, 1, 1, 1);
			}
		}
		healthIndicator.submit();

	}
	
	private void enemiesSharks(){
		
		for (Enemy enemy: mEnemies.getEnemiesList()) {

			
			if (collideCircleByCircle(boat, 20, enemy.getCX(), enemy.getCY(), 20)){
				
				if(enemy.isDied() == false){
					
					MainActivity.vibrate(30);
					
					if(currentHealth>0)
						currentHealth--;
					
					enemy.setDied(true);
				}	
			}
			}
	}
	
	private void updateBullets(){
		// Bullets
		ITextureRegion bulletRegion = MainActivity.Res.getTextureRegion("bullet");
		ITextureRegion bulletBoomRegion = MainActivity.Res.getTextureRegion("bullet_boom");
		ITextureRegion bulletResultRegion;
		
		for (int i = 0; i < mBullets.size(); i++) {

			BulletUnit bullet = mBullets.get(i);

			for (Enemy enemy: mEnemies.getEnemiesList()) {
				
				if (bullet.getBoom()==0 && bullet.collideWithCircle(enemy.getCX(),enemy.getCY(), 25)) {
					
					if(enemy.isDied() == false){	
						
						enemy.setHealth(enemy.getHealth() - bullet.getBoomPower());
						
						if(enemy.getHealth()<=0){
							
							enemy.setDied(true);
							
							totalGold += 50;
							textGold.setText(String.valueOf(totalGold));
						}
					}
					
					bullet.setSink(true);
					bullet.setBoom(10);
				}
			}
			

			if ( bullet.getY()-1 > water.getYPositionOnWave(bullet.getX())) {
				bullet.setSink(true);
			}

			bullet.update();

			if (bullet.getY() > MainActivity.getHeight() || bullet.getY() < 0) {
				mBullets.remove(i);
				i--;
				continue;
			}

			if (bullet.getX() > MainActivity.getWidth() || bullet.getX() < 0) {
				mBullets.remove(i);
				i--;
				continue;
			}

			if(bullet.getBoom()>0){
				bulletResultRegion = bulletBoomRegion;
			}else{
				bulletResultRegion = bulletRegion;
			}
			if(bullet.getBoom() != -1){
				bulletBatch.draw(bulletResultRegion,
						bullet.getX()-bulletResultRegion.getWidth()/2, bullet.getY()-bulletResultRegion.getHeight()/2, 
						bulletResultRegion.getWidth(), bulletResultRegion.getHeight(),
						1, 1, 1, 1, 1);
			}
		}

		bulletBatch.submit();
	}

	public static boolean collideCircleByCircle(Sprite c1, float radius1, float x1, float y1, float radius2){
		
		float dx = (x1) - (c1.getX()+c1.getWidth()/2);
		float dy = (y1) - (c1.getY()+c1.getHeight()/2);
		float dist = dx*dx + dy*dy;
		
		float radiusSum = radius1+radius2;
		if(dist <= radiusSum*radiusSum)
			return true;
		
		return false;
	}

	
}
