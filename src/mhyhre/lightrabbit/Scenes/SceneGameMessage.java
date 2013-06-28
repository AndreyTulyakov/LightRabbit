package mhyhre.lightrabbit.Scenes;

import mhyhre.lightrabbit.GameState;
import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.MhyhreScene;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

public class SceneGameMessage extends MhyhreScene {

	SceneGame sceneGame;

	
	Background background;
	
	Sprite buttonStart, buttonBack, spriteInfo;
	Text textReady, textInfo, textLevelInfo;

	
	
	public void displayStartScene(){
		textLevelInfo.setText("Уровень: " + sceneGame.getCurrentLevel());
		textLevelInfo.setPosition(MainActivity.getHalfWidth() - 150, MainActivity.getHalfHeight() + 20);
		
		textInfo.setText("Словосочетаний: " + (sceneGame.getCurrentLevel()+2));
		textInfo.setPosition(MainActivity.getHalfWidth() - 150, MainActivity.getHalfHeight() + 100);
	}
	
	public void displayEndScene(){
		
	}
	
	
	public SceneGameMessage(final SceneGame sceneGame) {
		
		this.sceneGame = sceneGame;
		
		background = new Background(0.78f, 0.78f, 0.80f);
		setBackgroundEnabled(true);
		setBackground(background);
		
	
		// Configure Info Message
		float horizontalSpace = 400;
		float centerW = MainActivity.getHalfWidth() - horizontalSpace/2;	
		float centerH = MainActivity.getHalfHeight() - sceneGame.TextureRegions.get(5).getHeight()/2;
		
		Sprite spriteInfoCenter = new Sprite(centerW+horizontalSpace/2 -2, centerH, sceneGame.TextureRegions.get(6), MainActivity.Me.getVertexBufferObjectManager());
		spriteInfoCenter.setScale( horizontalSpace/sceneGame.TextureRegions.get(6).getWidth(), 1);
		attachChild(spriteInfoCenter);
		
		Sprite spriteInfoLeft = new Sprite(centerW  - sceneGame.TextureRegions.get(5).getWidth(), centerH, sceneGame.TextureRegions.get(5), MainActivity.Me.getVertexBufferObjectManager());
		attachChild(spriteInfoLeft);
		
		Sprite spriteInfoRight = new Sprite(centerW+horizontalSpace, centerH, sceneGame.TextureRegions.get(5), MainActivity.Me.getVertexBufferObjectManager());
		spriteInfoRight.setFlippedHorizontal(true);
		attachChild(spriteInfoRight);
		
		
		textReady = new Text(0, 0, MainActivity.Res.getFont("Pixel"), "Готовы?", MainActivity.Me.getVertexBufferObjectManager());
		textReady.setPosition(MainActivity.getHalfWidth() - textReady.getWidth()/2, MainActivity.getHalfHeight() - spriteInfoCenter.getHeight()/3.5f);
		attachChild(textReady);
		
		
		// Задавать текст не в конструкторе!!!
		
		textLevelInfo = new Text(0, 0, MainActivity.Res.getFont("Pixel"), "", 60, MainActivity.Me.getVertexBufferObjectManager());
		attachChild(textLevelInfo);
		
		textInfo = new Text(0, 0, MainActivity.Res.getFont("Pixel"), "", 60, MainActivity.Me.getVertexBufferObjectManager());
		attachChild(textInfo);
		

		Sprite spriteNext = new Sprite(MainActivity.getWidth() - (10+sceneGame.TextureRegions.get(3).getWidth()), 10, sceneGame.TextureRegions.get(3), MainActivity.Me.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {

					sceneGame.setGameState(GameState.Memorize);
					MainActivity.mVibrator.vibrate(30);
				}
				return true;
			}
		};
		attachChild(spriteNext);
		registerTouchArea(spriteNext);
	}
	
	@Override
	public boolean onSceneTouchEvent(final TouchEvent pSceneTouchEvent) {

		return super.onSceneTouchEvent(pSceneTouchEvent);
	}
}
