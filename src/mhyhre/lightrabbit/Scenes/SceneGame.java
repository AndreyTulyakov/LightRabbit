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

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;

import android.util.Log;

public class SceneGame extends MhyhreScene {

	public Dictionary dictionary = Dictionary.Instance();

	private static GameState mode = GameState.Ready;
	private boolean loaded = false;
	
	Sprite spriteNext;
	
	int globalErrorCount = 0;
	final int globalErrorMaxCount = 3;


	SceneGameMemorize sceneMemorize;
	SceneGameMessage sceneMessage;

	// Resources
	public static final String uiAtlasName = "User_Interface";

	public static ArrayList<ITextureRegion> TextureRegions;
	
	private int currentLevel = 0;
	final private int maxLevel = 10;
	final private int maxItemCount = (maxLevel+2) * 2;

	public SceneGame() {
		
		CreateTextureRegions();

		// Memorize Scene
		sceneMemorize = new SceneGameMemorize(this, maxItemCount);
		attachChild(sceneMemorize);
		
		// Scene message
		sceneMessage = new SceneGameMessage(this);
		attachChild(sceneMessage);
		
		

		spriteNext = new Sprite(MainActivity.getWidth() - (10 + TextureRegions.get(3).getWidth()), 10, TextureRegions.get(3), MainActivity.Me.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				
				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
					
					
					MainActivity.vibrate(30);
					
					switch(mode){
					
					case Ready:
						setGameState(GameState.Memorize);
						break;
						
					case Memorize:
						setGameState(GameState.Recollect);
						break;
						
					case Recollect:
						setGameState(GameState.Result);
						break;
						
					case Result:
						nextLevel();
						setGameState(GameState.Ready);
						break;
						
					case Loss:
						SceneRoot.SetState(SceneStates.MainMenu);
						break;
					}		
				}
				return true;
			}
		};
		spriteNext.setVisible(false);
		attachChild(spriteNext);
		enableNextButton(true);
		
		loaded = true;
		
		setGameState(GameState.Ready);
	}
	
	private void nextLevel(){
		currentLevel++;

		ArrayList<String> words = getNewWordsList();
		
		sceneMemorize.setWordsList(words);
		sceneMemorize.update();
		
		if(getCurrentLevel()>getMaxLevel()){
			SceneRoot.SetState(SceneStates.MainMenu);
		}
	}
	
	public void enableNextButton(boolean arg){
		if(arg==true && spriteNext.isVisible() == false){
			spriteNext.setVisible(true);
			spriteNext.setIgnoreUpdate(false);
			registerTouchArea(spriteNext);
			
		}else if(arg==false && spriteNext.isVisible() == true){
			
			spriteNext.setVisible(false);
			spriteNext.setIgnoreUpdate(true);
			unregisterTouchArea(spriteNext);
			
		}
	}
	
	
	private ArrayList<String> getNewWordsList(){
		
		int currentItem = (getCurrentLevel() + 2) * 2;
		
		if(dictionary.size() <= currentItem){
			Log.e(MainActivity.DebugID, "SceneGame: dictionary is too small");
			return null;
		}
		
		ArrayList<String> wordsList = new ArrayList<String>();
			
		boolean wordIsExist;
		int i = 0;
		
		while( i<currentItem ){
			String word = dictionary.getRandomWord();
			wordIsExist = false;

			for(String str: wordsList){
				if(str.equals(word)){
					wordIsExist = true;
					break;
				}
			}
			
			if(wordIsExist == false){
				wordsList.add(word);
				i++;
			}
		}
		
		return wordsList;
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
			case Loss:
				break;
				
			default:
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

		SceneGame.mode = mode;

		sceneMemorize.Hide();
		sceneMessage.Hide();

		switch (mode) {

		case Ready:
			sceneMessage.displayStartScene();
			sceneMessage.Show();
			enableNextButton(true);
			break;

		case Memorize:
			sceneMemorize.Show();
			enableNextButton(true);
			break;

		case Recollect:
			enableNextButton(false);
			sceneMemorize.shuffleWords();
			sceneMemorize.Show();
			break;

		case Result:
			enableNextButton(true);
			sceneMessage.displayEndScene(getErrorCount());
			sceneMessage.Show();
			break;
			
		case Loss:
			enableNextButton(true);
			sceneMessage.displayLosingScene(getErrorCount());
			sceneMessage.Show();
			break;
		}
	}

	public static GameState getMode() {
		return mode;
	}

	public int getCurrentLevel(){
		return currentLevel;
	}
	
	public void setCurrentLevel(int arg){
		if(arg>0){
			currentLevel = arg-1;
		} else {
			currentLevel = 0;
		}
		nextLevel();
	}
	
	public int getMaxLevel(){
		return maxLevel;
	}

	public int getErrorCount() {
		return globalErrorCount;
	}

	public void setErrorCount(int errorCount) {
		globalErrorCount = errorCount;
	}
	
	public void addError() {
		globalErrorCount++;
	}
	
	public boolean isOverErrorsLimit(){
		if(globalErrorCount >= globalErrorMaxCount){
			return true;
		} else {
			return false;
		}
	}
	
	public int getErrorMaxCount() {
		return globalErrorMaxCount;
	}
}
