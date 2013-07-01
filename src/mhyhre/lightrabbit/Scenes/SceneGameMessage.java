package mhyhre.lightrabbit.Scenes;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.MhyhreScene;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

public class SceneGameMessage extends MhyhreScene {

	SceneGame sceneGame;
	
	Sprite spriteInfo;
	Text textReady, textInfo, textLevelInfo;

	
	
	public void displayStartScene(){
		textReady.setText("Готовы?");
		textReady.setPosition(MainActivity.getHalfWidth() - textReady.getWidth()/2, MainActivity.getHalfHeight() - 100);
		
		textLevelInfo.setText("Уровень: " + sceneGame.getCurrentLevel());
		textLevelInfo.setPosition(MainActivity.getHalfWidth() - 150, MainActivity.getHalfHeight() + 20);
		
		textInfo.setText("Словосочетаний: " + (sceneGame.getCurrentLevel()+2));
		textInfo.setPosition(MainActivity.getHalfWidth() - 150, MainActivity.getHalfHeight() + 100);
		textInfo.setVisible(true);
	}
	
	public void displayEndScene(int errorsCount){
		textReady.setText("Результат");
		textReady.setPosition(MainActivity.getHalfWidth() - textReady.getWidth()/2, MainActivity.getHalfHeight() - 100);
		
		textLevelInfo.setText("Ошибок: " + errorsCount);
		textLevelInfo.setPosition(MainActivity.getHalfWidth() - 150, MainActivity.getHalfHeight() + 20);

		textInfo.setVisible(false);
	}
	
	
	public SceneGameMessage(final SceneGame sceneGame) {
		
		this.sceneGame = sceneGame;
		
		Background background = new Background(0.78f, 0.78f, 0.90f);
		setBackgroundEnabled(true);
		setBackground(background);
	
		// Configure Info Message
		float horizontalSpace = 400;
		float centerW = MainActivity.getHalfWidth() - horizontalSpace/2;	
		float centerH = MainActivity.getHalfHeight() - SceneGame.TextureRegions.get(5).getHeight()/2;
		
		Sprite spriteInfoCenter = new Sprite(centerW+horizontalSpace/2 -2, centerH, SceneGame.TextureRegions.get(6), MainActivity.Me.getVertexBufferObjectManager());
		spriteInfoCenter.setScale( horizontalSpace/SceneGame.TextureRegions.get(6).getWidth(), 1);
		attachChild(spriteInfoCenter);
		
		Sprite spriteInfoLeft = new Sprite(centerW  - SceneGame.TextureRegions.get(5).getWidth(), centerH, SceneGame.TextureRegions.get(5), MainActivity.Me.getVertexBufferObjectManager());
		attachChild(spriteInfoLeft);
		
		Sprite spriteInfoRight = new Sprite(centerW+horizontalSpace, centerH, SceneGame.TextureRegions.get(5), MainActivity.Me.getVertexBufferObjectManager());
		spriteInfoRight.setFlippedHorizontal(true);
		attachChild(spriteInfoRight);
		
		
		textReady = new Text(0, 0, MainActivity.Res.getFont("Pixel"), "", 50, MainActivity.Me.getVertexBufferObjectManager());
		attachChild(textReady);
		
		textLevelInfo = new Text(0, 0, MainActivity.Res.getFont("Pixel"), "", 60, MainActivity.Me.getVertexBufferObjectManager());
		attachChild(textLevelInfo);
		
		textInfo = new Text(0, 0, MainActivity.Res.getFont("Pixel"), "", 60, MainActivity.Me.getVertexBufferObjectManager());
		attachChild(textInfo);
		

	}
	
	@Override
	public boolean onSceneTouchEvent(final TouchEvent pSceneTouchEvent) {

		return super.onSceneTouchEvent(pSceneTouchEvent);
	}
}
