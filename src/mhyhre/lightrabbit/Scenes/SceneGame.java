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

import mhyhre.lightrabbit.Dictionary;
import mhyhre.lightrabbit.GameState;
import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.MhyhreScene;

import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;

import android.util.Log;

public class SceneGame extends MhyhreScene {

	public Dictionary dictionary = Dictionary.Instance();

	private GameState mode = GameState.Ready;
	private boolean loaded = false;
	
	SceneGameShow sceneMemorize;

	// Resources
	public static final String uiAtlasName = "User_Interface";

	public static ArrayList<ITextureRegion> TextureRegions;
	
	private int currentLevel = 0;
	final private int maxLevel = 30;
	final private int maxItemCount = (maxLevel+2) * 2;

	public SceneGame() {
		
		CreateTextureRegions();

		sceneMemorize = new SceneGameShow(maxItemCount);
		sceneMemorize.Show();
		attachChild(sceneMemorize);

		ArrayList<String> words = new ArrayList<String>();
		for(int i=0; i<maxItemCount; i++){
			words.add(dictionary.getRandomWord());
		}
		
		sceneMemorize.setWordsList(words);
		sceneMemorize.update();
		
		

		
		loaded = true;
	}

	private static void CreateTextureRegions() {

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

		sceneMemorize.onSceneTouchEvent(pSceneTouchEvent);
		if (loaded == true) {
			
			switch (mode) {

			case Ready:
				
				break;

			case Memorize:
				sceneMemorize.onSceneTouchEvent(pSceneTouchEvent);
				break;

			case Recollect:
				sceneMemorize.onSceneTouchEvent(pSceneTouchEvent);
				break;

			case Result:
				
				break;
			}		
		}

		return super.onSceneTouchEvent(pSceneTouchEvent);
	}


	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {

		super.onManagedUpdate(pSecondsElapsed);
	}

	public void setGameState(GameState mode) {

		if (loaded == false) {
			return;
		}

		Log.i(MainActivity.DebugID, "SceneGame::setGameState: " + mode.name());

		this.mode = mode;

		sceneMemorize.Show();
		//sceneMemorize.Hide();

		switch (mode) {

		case Ready:

			break;

		case Memorize:
			
			break;

		case Recollect:
			
			break;

		case Result:
			
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
		return maxLevel;
	}
	
	
}
