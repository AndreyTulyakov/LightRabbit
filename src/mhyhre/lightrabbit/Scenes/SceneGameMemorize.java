package mhyhre.lightrabbit.Scenes;

import java.io.IOException;
import java.util.ArrayList;

import mhyhre.lightrabbit.Dictionary;
import mhyhre.lightrabbit.GameState;
import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.MhyhreScene;
import mhyhre.lightrabbit.WordItem;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.batch.SpriteBatch;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;

import android.util.Log;
import android.view.MotionEvent;

public class SceneGameMemorize extends MhyhreScene {

	SceneGame sceneGame;
	SpriteBatch UIBatch;

	private Sprite spriteToMenu, spriteNext;
	public ArrayList<Text> wordsText;
	public Dictionary dictionary;

	public ArrayList<WordItem> wordItemsLeft;
	public ArrayList<WordItem> wordItemsRight;

	public final float verticalStep = 80;
	public final float borderSize = 50;
	private float mTouchY = 0, mTouchOffsetY = 0;
	private float mTouchX = 0, mTouchOffsetX = 0;
	
	public void displayMemorizeScene() {
		this.setY(0);
		resetSpriteOffsetY();
		int count = sceneGame.getCurrentLevel() + 2;
		SetupSpriteBatch(count);
		SetupWordText(count);
	}

	public void displayRecollectScene() {
		this.setY(0);
		resetSpriteOffsetY();
		int count = sceneGame.getCurrentLevel() + 2;
		SetupSpriteBatch(count);
	}

	public SceneGameMemorize(final SceneGame sceneGame) {

		this.sceneGame = sceneGame;

		UIBatch = new SpriteBatch(MainActivity.Res.getTextureAtlas(sceneGame.uiAtlasName),  50, MainActivity.Me.getVertexBufferObjectManager());
		UIBatch.setPosition(0, 0);
		attachChild(UIBatch);

		try {
			dictionary = new Dictionary("words.txbase");
		} catch (IOException e) {
			Log.i(MainActivity.DebugID, "Dictionary::cant load: " + e.getMessage());
			e.printStackTrace();
		}

		wordItemsLeft = new ArrayList<WordItem>();
		wordItemsRight = new ArrayList<WordItem>();


		wordsText = new ArrayList<Text>();
		int maxCount = (sceneGame.getMaxLevel() + 2) * 2;

		for (int i = 0; i < maxCount; i++) {
			wordsText.add(new Text(0, 0, MainActivity.Res.getFont("Pixel"), "", 50, MainActivity.Me.getVertexBufferObjectManager()));
			attachChild(wordsText.get(i));
		}
		
		


		spriteToMenu = new Sprite(10, 10, sceneGame.TextureRegions.get(4), MainActivity.Me.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {

					Log.i(MainActivity.DebugID, "Game: clicked to menu buttons");
					SceneRoot.SetState(SceneStates.MainMenu);
					MainActivity.mVibrator.vibrate(30);
				}
				return true;
			}
		};
		attachChild(spriteToMenu);
		registerTouchArea(spriteToMenu);

		spriteNext = new Sprite(MainActivity.getWidth() - (10 + sceneGame.TextureRegions.get(3).getWidth()), 10, sceneGame.TextureRegions.get(3), MainActivity.Me.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {

					Log.i(MainActivity.DebugID, "Game: clicked next buttons");
					MainActivity.mVibrator.vibrate(30);

					if (sceneGame.getGameState() == GameState.Memorize) {
						sceneGame.setGameState(GameState.Recollect);
					} else {
						sceneGame.setGameState(GameState.Result);
					}
				}
				return true;
			}
		};
		attachChild(spriteNext);
		registerTouchArea(spriteNext);
		
		
	}

	private void SetupSpriteBatch(int count) {

		ArrayList<ITextureRegion> TextureRegions = sceneGame.TextureRegions;

		float xOffset = 10 + (TextureRegions.get(1).getWidth() / 2) + (TextureRegions.get(0).getWidth() / 2);
		float xLeftColumnOffset = (MainActivity.getHalfWidth() - xOffset) - TextureRegions.get(0).getWidth() / 2;
		float xRightColumnOffset = (MainActivity.getHalfWidth() + xOffset) - TextureRegions.get(0).getWidth() / 2;
		float xEqualOffset = MainActivity.getHalfWidth() - TextureRegions.get(1).getWidth() / 2;

		// Vertucal start offset
		float verticalStart = borderSize;

		// First Column
		for (int i = 0; i < count; i++) {
			UIBatch.draw(TextureRegions.get(0), xLeftColumnOffset, verticalStart + i * verticalStep, TextureRegions.get(0).getWidth(), TextureRegions.get(0).getHeight(), 0, 1, 1, 1, 1, 1, 1);
		}

		// Second Column
		for (int i = 0; i < count; i++) {
			UIBatch.draw(TextureRegions.get(0), xRightColumnOffset, verticalStart + i * verticalStep, TextureRegions.get(0).getWidth(), TextureRegions.get(0).getHeight(), 0, 1, 1, 1, 1, 1, 1);
		}

		if (sceneGame.getGameState() == GameState.Memorize) {
			for (int i = 0; i < count; i++) {
				UIBatch.draw(TextureRegions.get(1), xEqualOffset, verticalStart + i * verticalStep, TextureRegions.get(1).getWidth(), TextureRegions.get(1).getHeight(), 0, 1, 1, 1, 1, 1, 1);
			}
		}

		UIBatch.submit();

	}

	private void SetupWordText(int count) {

		ArrayList<ITextureRegion> TextureRegions = sceneGame.TextureRegions;

		float verticalMult = borderSize + (TextureRegions.get(0).getHeight()/2 - MainActivity.Res.getFont("Pixel").getLineHeight()/2);
		float xOffset = 10 + (TextureRegions.get(1).getWidth() / 2) + (TextureRegions.get(0).getWidth() / 2);
		float xLeftColumnOffset = (MainActivity.getHalfWidth() - xOffset);
		float xRightColumnOffset = (MainActivity.getHalfWidth() + xOffset);

		for (int i = 0; i < count; i++) {
			wordsText.get(i).setText(dictionary.getRandomWord());
			wordsText.get(i).setVisible(true);
			wordsText.get(i).setPosition(xLeftColumnOffset - wordsText.get(i).getWidth() / 2, verticalMult + i * verticalStep);
		}

		for (int i = count; i < (count * 2); i++) {
			wordsText.get(i).setText(dictionary.getRandomWord());
			wordsText.get(i).setVisible(true);
			wordsText.get(i).setPosition(xRightColumnOffset - wordsText.get(i).getWidth() / 2, verticalMult + (i - count) * verticalStep);
		}

		for (int i = (count * 2); i < wordsText.size(); i++) {
			wordsText.get(i).setVisible(false);
		}

	}

	boolean isSlideAction = false;
	boolean isFirstDown = false;
	static TouchEvent lastDownEvent;

	@Override
	public boolean onSceneTouchEvent(final TouchEvent pTouchEvent) {

		float sensitivity = 5.0f;
		
		if (pTouchEvent.getAction() == MotionEvent.ACTION_UP) {

			Log.i(MainActivity.DebugID, "GameMemorize: up");

			if(isSlideAction == false && isFirstDown){
				isSlideAction = false;
				Log.i(MainActivity.DebugID, "GameMemorize: click");

			}		
			isSlideAction = false;
		}
		
		if (pTouchEvent.getAction() == MotionEvent.ACTION_DOWN) {
			Log.i(MainActivity.DebugID, "GameMemorize: down");
			isFirstDown = true;
			lastDownEvent = pTouchEvent;
			mTouchY = pTouchEvent.getMotionEvent().getY();
			
		} else {
			if (pTouchEvent.getAction() == MotionEvent.ACTION_MOVE) {

				float newX = pTouchEvent.getMotionEvent().getX();
				float newY = pTouchEvent.getMotionEvent().getY();
				
				mTouchOffsetX = (newX - mTouchX);
				mTouchOffsetY = (newY - mTouchY);
				
				if(isFirstDown){
					float myOffsetX = lastDownEvent.getX() - newX;
					float myOffsetY = lastDownEvent.getY() - newY;
					
					if(myOffsetX > sensitivity || myOffsetX < -sensitivity){
						isSlideAction = true;
					}
		
					if(myOffsetY > sensitivity || myOffsetY < -sensitivity){
						isSlideAction = true;
					}
						
				}

				if(mTouchOffsetY > sensitivity || mTouchOffsetY < -sensitivity){
					isSlideAction = true;
					this.setPosition(getX() , getY() + mTouchOffsetY);
					if(this.getY() > 0) this.setY(0);
					
					float maxY =  MainActivity.getHeight() - (borderSize * 2 + verticalStep * (sceneGame.getCurrentLevel()+2));
					maxY += verticalStep - sceneGame.TextureRegions.get(0).getHeight();
					if(this.getY() < maxY){
						this.setY(maxY);
					}
					
					resetSpriteOffsetY();
				}
				mTouchX = newX;
				mTouchY = newY;
			}
		}	

		return super.onSceneTouchEvent(pTouchEvent);
	}
	
	private void resetSpriteOffsetY(){
		spriteToMenu.setY(10 - this.getY());
		spriteNext.setY(10 - this.getY());
	}

}
