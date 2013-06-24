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
	
	Sprite buttonStart, buttonBack, spriteInfo;
	Text textStart, textInfo;
	
	
	public SceneGameReady(SceneGame sceneGame) {
		
		this.sceneGame = sceneGame;
		
		background = new Background(0.78f, 0.78f, 0.80f
		setBackgroundEnabled(true);
		setBackground(background);

		attachChild(sceneGame.UIBatch);
	}
	
	@Override
	public boolean onSceneTouchEvent(final TouchEvent pSceneTouchEvent) {

		return super.onSceneTouchEvent(pSceneTouchEvent);
	}
}
