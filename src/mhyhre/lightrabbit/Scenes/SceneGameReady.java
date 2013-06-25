package mhyhre.lightrabbit.Scenes;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.MhyhreScene;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

public class SceneGameReady extends MhyhreScene {

	SceneGame sceneGame;
	
	Background background;
	
	Sprite buttonStart, buttonBack;
	Text textStart, textInfo;
	
	
	public SceneGameReady(SceneGame sceneGame) {
		
		this.sceneGame = sceneGame;
		
		background = new Background(0.78f, 0.78f, 0.80f);
		setBackgroundEnabled(true);
		setBackground(background);
		
		float centerW = MainActivity.getHalfWidth();	
		float centerH = MainActivity.getHalfHeight();
		
		float rw = 600, rh = 400; 
		Rectangle rectInfo = new Rectangle(centerW - rw/2, centerH - rh/2 , rw, rh, MainActivity.Me.getVertexBufferObjectManager());
		rectInfo.setColor(0.9f, 0.9f, 1.0f);
		attachChild(rectInfo);
		
		textInfo = new Text(0, 0, MainActivity.Res.getFont("Pixel"), "Pairs of words: " + sceneGame.ItemCount, MainActivity.Me.getVertexBufferObjectManager());
		textInfo.setPosition(centerW - textInfo.getWidth()/2, centerH - 100);
		attachChild(textInfo);
	}
	
	@Override
	public boolean onSceneTouchEvent(final TouchEvent pSceneTouchEvent) {

		return super.onSceneTouchEvent(pSceneTouchEvent);
	}
}
