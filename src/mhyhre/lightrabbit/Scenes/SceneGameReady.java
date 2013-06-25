package mhyhre.lightrabbit.Scenes;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.MhyhreScene;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

public class SceneGameReady extends MhyhreScene {

	SceneGame sceneGame;
	
	Background background;
	
	Sprite buttonStart, buttonBack, spriteInfo;
	Text textStart, textInfo;
	
	
	public SceneGameReady(SceneGame sceneGame) {
		
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
		
		textInfo = new Text(0, 0, MainActivity.Res.getFont("Pixel"), "Pairs of words: " + sceneGame.ItemCount, MainActivity.Me.getVertexBufferObjectManager());
		textInfo.setPosition(MainActivity.getHalfWidth() - textInfo.getWidth()/2, MainActivity.getHalfHeight() - 100);
		attachChild(textInfo);
	}
	
	@Override
	public boolean onSceneTouchEvent(final TouchEvent pSceneTouchEvent) {

		return super.onSceneTouchEvent(pSceneTouchEvent);
	}
}
