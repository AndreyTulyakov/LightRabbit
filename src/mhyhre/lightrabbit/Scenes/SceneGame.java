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

import mhyhre.lightrabbit.GameState;
import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.MhyhreScene;

import org.andengine.entity.scene.background.Background;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;

import android.util.Log;

public class SceneGame extends MhyhreScene {

	
	private GameState mode = GameState.Ready;
	private boolean loaded = false;
	
	Background background;
	SceneGameMessage sceneMessage;
	SceneGameMemorize sceneMemorize;

	// Resources
	public String uiAtlasName = "User_Interface";

	public ArrayList<ITextureRegion> TextureRegions;

	long time = 100;
	long lasttime;
	
	private int currentLevel = 0;
	private int MaxLevel = 10;


	public SceneGame() {
		
		background = new Background(0.78f, 0.78f, 0.80f);
		setBackgroundEnabled(true);
		setBackground(background);

		CreateTextureRegions();

		sceneMessage = new SceneGameMessage(this);
		attachChild(sceneMessage);

		sceneMemorize = new SceneGameMemorize(this);
		attachChild(sceneMemorize);


		
		

		
		loaded = true;

		setGameState(GameState.Ready);
	}

	private void CreateTextureRegions() {

		TextureRegions = new ArrayList<ITextureRegion>();
		BitmapTextureAtlas atlas = MainActivity.Res.getTextureAtlas(uiAtlasName);
		TextureRegions.add(TextureRegionFactory.extractFromTexture(atlas, 0, 0, 310, 70, false));
		TextureRegions.add(TextureRegionFactory.extractFromTexture(atlas, 325, 0, 45, 70, false));

		TextureRegions.add(TextureRegionFactory.extractFromTexture(atlas, 0, 70, 74, 74, false));
		TextureRegions.add(TextureRegionFactory.extractFromTexture(atlas, 80, 70, 74, 74, false));

		TextureRegions.add(TextureRegionFactory.extractFromTexture(atlas, 160, 70, 74, 74, false));

		TextureRegions.add(TextureRegionFactory.extractFromTexture(atlas, 390, 0, 120, 384, false));
		TextureRegions.add(TextureRegionFactory.extractFromTexture(atlas, 460, 0, 4, 384, false));
	}

	@Override
	public boolean onSceneTouchEvent(final TouchEvent pSceneTouchEvent) {

		if (loaded == true) {
			
			switch (mode) {

			case Ready:
				sceneMessage.onSceneTouchEvent(pSceneTouchEvent);
				break;

			case Memorize:
				sceneMemorize.onSceneTouchEvent(pSceneTouchEvent);
				break;

			case Recollect:
				sceneMemorize.onSceneTouchEvent(pSceneTouchEvent);
				break;

			case Result:
				sceneMessage.onSceneTouchEvent(pSceneTouchEvent);
				break;
			}		
		}

		return super.onSceneTouchEvent(pSceneTouchEvent);
	}


	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {

		if (System.currentTimeMillis() - lasttime > time) {
			lasttime = System.currentTimeMillis();
		}
		super.onManagedUpdate(pSecondsElapsed);
	}

	public void setGameState(GameState mode) {

		if (loaded == false) {
			return;
		}

		Log.i(MainActivity.DebugID, "SceneGame::setGameState: " + mode.name());

		this.mode = mode;

		sceneMessage.Hide();
		sceneMemorize.Hide();

		switch (mode) {

		case Ready:
			if(currentLevel>MaxLevel){
				SceneRoot.SetState(SceneStates.MainMenu);
			}
			
			sceneMessage.displayStartScene();
			sceneMessage.Show();
			break;

		case Memorize:
			sceneMemorize.displayMemorizeScene();
			sceneMemorize.Show();
			break;

		case Recollect:
			sceneMemorize.displayRecollectScene();
			sceneMemorize.Show();
			break;

		case Result:
			sceneMessage.displayEndScene();
			sceneMessage.Show();
			break;
		}
	}

	public GameState getGameState() {
		return mode;
	}
	
	
	

	public int getCurrentLevel(){
		return currentLevel;
	}
	
	public void setCurrentLevel(int arg){
		if(arg>=0){
			currentLevel = arg;
		}
	}
	
	public int getMaxLevel(){
		return MaxLevel;
	}
	
	
}
