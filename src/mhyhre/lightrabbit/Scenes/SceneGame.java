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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mhyhre.lightrabbit.GameState;
import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.MhyhreScene;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;

import android.util.Log;

public class SceneGame extends MhyhreScene {

	private Background mBackground;

	private static GameState mode = GameState.Ready;
	private boolean loaded = false;

	Sprite spriteMoveRight, spriteMoveLeft, spriteFire, boat;

	private WaterPolygon water;
	
	private float boatSpeed = 0;
	private float boatAcseleration = 5;

	// Resources
	public static final String uiAtlasName = "User_Interface";

	public static Map<String, ITextureRegion> TextureRegions;

	public SceneGame() {

		mBackground = new Background(0.40f, 0.88f, 0.99f);
		setBackground(mBackground);
		setBackgroundEnabled(true);

		CreateTextureRegions();

		water = new WaterPolygon(16, MainActivity.Me.getVertexBufferObjectManager());
		this.attachChild(water);
		
		
		boat = new Sprite(100, 100, MainActivity.Res.getTextureRegion("boat_body"), MainActivity.Me.getVertexBufferObjectManager());
		attachChild(boat);

		
		spriteMoveLeft = new Sprite( 10, 10, TextureRegions.get("Left"), MainActivity.Me.getVertexBufferObjectManager()) {
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
		spriteMoveLeft.setVisible(true);
		attachChild(spriteMoveLeft);
		registerTouchArea(spriteMoveLeft);
		
		spriteMoveRight = new Sprite( 10 + spriteMoveLeft.getWidth()+10, 10, TextureRegions.get("Right"), MainActivity.Me.getVertexBufferObjectManager()) {
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
		spriteMoveRight.setVisible(true);
		attachChild(spriteMoveRight);
		registerTouchArea(spriteMoveRight);
		

		
		spriteFire = new Sprite( MainActivity.getWidth()-100, 10, TextureRegions.get("Fire"), MainActivity.Me.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
					MainActivity.vibrate(30);
				}
				
				return true;
			}
		};
		spriteFire.setVisible(true);
		attachChild(spriteFire);
		registerTouchArea(spriteFire);
		

		loaded = true;

		Log.i(MainActivity.DebugID, "Scene game created");
	}

	private static void CreateTextureRegions() {

		TextureRegions = new HashMap<String, ITextureRegion>();
		BitmapTextureAtlas atlas = MainActivity.Res.getTextureAtlas(uiAtlasName);
		
		
		
		TextureRegions.put("Button",TextureRegionFactory.extractFromTexture(atlas, 0, 0, 310, 70, false));
		TextureRegions.put("Equall",TextureRegionFactory.extractFromTexture(atlas, 325, 0, 45, 70, false));

		TextureRegions.put("Left",TextureRegionFactory.extractFromTexture(atlas, 0, 70, 74, 74, false));
		TextureRegions.put("Right",TextureRegionFactory.extractFromTexture(atlas, 80, 70, 74, 74, false));

		TextureRegions.put("Menu",TextureRegionFactory.extractFromTexture(atlas, 160, 70, 74, 74, false));

		TextureRegions.put("Fire",TextureRegionFactory.extractFromTexture(atlas, 0, 512-64, 64, 64, false));
	}

	@Override
	public boolean onSceneTouchEvent(final TouchEvent pSceneTouchEvent) {

		if (loaded == true) {

		}

		return super.onSceneTouchEvent(pSceneTouchEvent);
	}

	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {

		if(boat.getX() > (MainActivity.getWidth() - 32 - boat.getWidth()) && boatSpeed > 0)
			boatSpeed = 0;
		
		if(boat.getX() < 32 && boatSpeed<0)
			boatSpeed = 0;
		
		boat.setX(boat.getX() + boatSpeed);


		boat.setY(water.getYPositionOnWave(boat.getX() + boat.getWidth()/2)-boat.getHeight()/2 - 5);
		boat.setRotation(water.getAngleOnWave(boat.getX())/2.0f);
		
		super.onManagedUpdate(pSecondsElapsed);
	}


}
