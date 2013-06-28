package mhyhre.lightrabbit.Scenes;

import java.io.IOException;
import java.util.ArrayList;

import mhyhre.lightrabbit.Dictionary;
import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.MhyhreScene;
import mhyhre.lightrabbit.WordItem;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.batch.SpriteBatch;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;

import android.util.Log;

public class SceneGameMemorize extends MhyhreScene {

	SceneGame sceneGame;
	Background background;

	SpriteBatch UIBatch;

	public ArrayList<Text> wordsText;
	public Dictionary dictionary;
	
	public ArrayList<WordItem> wordItemsLeft;
	public ArrayList<WordItem> wordItemsRight;
	
	public final float verticalStep = 80;

	public void displayMemorizeScene() {

		int count = sceneGame.getCurrentLevel()+2;
		SetupSpriteBatch(count);
	}

	public void displayRecollectScene() {

	}

	public SceneGameMemorize(SceneGame sceneGame) {

		this.sceneGame = sceneGame;

		background = new Background(0.38f, 0.38f, 0.30f);
		setBackgroundEnabled(true);
		setBackground(background);
		
		UIBatch = new SpriteBatch(MainActivity.Res.getTextureAtlas(sceneGame.uiAtlasName), 32, MainActivity.Me.getVertexBufferObjectManager());
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
		

		
		
		//SetupWordText(count);
/*
		// Add text layout
		for (int i = 0; i < wordsText.size(); i++) {
			attachChild(wordsText.get(i));
		}
*/
		Sprite spriteToMenu = new Sprite(10, 10, sceneGame.TextureRegions.get(4), MainActivity.Me.getVertexBufferObjectManager()) {
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

		Sprite spriteNext = new Sprite(MainActivity.getWidth() - (10 + sceneGame.TextureRegions.get(3).getWidth()), 10, sceneGame.TextureRegions.get(3), MainActivity.Me.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {

					Log.i(MainActivity.DebugID, "Game: clicked next buttons");
					MainActivity.mVibrator.vibrate(30);
				}
				return true;
			}
		};
		attachChild(spriteNext);
		registerTouchArea(spriteNext);
	}

	private void SetupSpriteBatch(int count) {

		ArrayList<ITextureRegion> TextureRegions = sceneGame.TextureRegions;
		
		float xOffset = 10 + (TextureRegions.get(1).getWidth() / 2) + (TextureRegions.get(0).getWidth()/2);
		float xLeftColumnOffset = (MainActivity.getHalfWidth() - xOffset) - TextureRegions.get(0).getWidth()/2;
		float xRightColumnOffset = (MainActivity.getHalfWidth() + xOffset) - TextureRegions.get(0).getWidth()/2;
		float xEqualOffset = MainActivity.getHalfWidth() - TextureRegions.get(1).getWidth()/2;
		
		// Расчет вертикального смещения
		float verticalMult = (MainActivity.getHalfHeight()) - ((count * verticalStep) / 2 - (verticalStep - TextureRegions.get(0).getHeight()) / 2);

		// First Column
		for (int i = 0; i < count; i++) {
			UIBatch.draw(TextureRegions.get(0), xLeftColumnOffset, verticalMult + i * verticalStep, TextureRegions.get(0).getWidth(), TextureRegions.get(0).getHeight(), 0, 1, 1, 1, 1, 1, 1);
		}

		// Second Column
		for (int i = 0; i < count; i++) {
			UIBatch.draw(TextureRegions.get(0), xRightColumnOffset, verticalMult + i * verticalStep, TextureRegions.get(0).getWidth(), TextureRegions.get(0).getHeight(), 0, 1, 1, 1, 1, 1, 1);
		}

		// Equal
		for (int i = 0; i < count; i++) {
			UIBatch.draw(TextureRegions.get(1), xEqualOffset, verticalMult + i * verticalStep, TextureRegions.get(1).getWidth(), TextureRegions.get(1).getHeight(), 0, 1, 1, 1, 1, 1, 1);
		}

		UIBatch.submit();

	}
/*
	private void SetupWordText(int count) {

		wordItemsLeft.clear();
		wordItemsRight.clear();
		
		posVertical += sceneGame.TextureRegions.get(0).getHeight() / 2 - MainActivity.Res.getFont("Pixel").getLineHeight() / 2;

		wordsText = new ArrayList<Text>();

		float textHalfWidht;

		// Left column
		for (int i = 0; i < sceneGame.ItemCount; i++) {
			wordsText.add(new Text(0, 0, MainActivity.Res.getFont("Pixel"), dictionary.getRandomWord(), MainActivity.Me.getVertexBufferObjectManager()));
			textHalfWidht = wordsText.get(i).getWidth() / 2;
			wordsText.get(i).setPosition(c1Horizontal - textHalfWidht, posVertical + i * verticalStep);
		}

		// Left column
		for (int i = 0; i < sceneGame.ItemCount; i++) {
			wordsText.add(new Text(0, 0, MainActivity.Res.getFont("Pixel"), dictionary.getRandomWord(), MainActivity.Me.getVertexBufferObjectManager()));
			textHalfWidht = wordsText.get(sceneGame.ItemCount + i).getWidth() / 2;
			wordsText.get(sceneGame.ItemCount + i).setPosition(c2Horizontal - textHalfWidht, posVertical + i * verticalStep);
		}
	}
*/
	
	@Override
	public boolean onSceneTouchEvent(final TouchEvent pSceneTouchEvent) {
/*
		if (pSceneTouchEvent.isActionDown()) {
			if (checkRect(pSceneTouchEvent.getX(), pSceneTouchEvent.getY(), 100, 100, 100, 100)) {
				Log.i(MainActivity.DebugID, "Game: check test");
			}
		}
*/
		return super.onSceneTouchEvent(pSceneTouchEvent);
	}

}
