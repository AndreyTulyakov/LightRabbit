package mhyhre.lightrabbit.game;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.MhyhreScene;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.batch.SpriteBatch;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

public class GameHUD extends MhyhreScene {
	
	public enum Buttons{
		LEFT, RIGHT, JUMP, FIRE;
	}

	Sprite spriteGold;
	SpriteBatch healthIndicator;
	Sprite spriteMoveRight, spriteMoveLeft, spriteFire;
	Text textGold;
	
	boolean states[];
	private static final float BUTTON_RADIUS = 50;
	
	
	public GameHUD() {
		setBackgroundEnabled(false);
		Show();
		
		states = new boolean[16];
			
		healthIndicator = new SpriteBatch(MainActivity.Res.getTextureAtlas("texture01"), 10, MainActivity.Me.getVertexBufferObjectManager());

		spriteGold = new Sprite(0, 0, MainActivity.Res.getTextureRegion("gold"), MainActivity.Me.getVertexBufferObjectManager());
		spriteGold.setPosition(250, MainActivity.getHeight() - 28);
		
		textGold = new Text(340, 10, MainActivity.Res.getFont("White Furore"), String.valueOf(0), 20, MainActivity.Me.getVertexBufferObjectManager());
		updateGoldIndicator(0);

		attachChild(healthIndicator);
		attachChild(spriteGold);
		attachChild(textGold);

		spriteMoveLeft = new Sprite(0, 0, MainActivity.Res.getTextureRegion("Left"), MainActivity.Me.getVertexBufferObjectManager());
		spriteMoveLeft.setPosition(60, 50);
		spriteMoveLeft.setVisible(true);
		attachChild(spriteMoveLeft);

		spriteMoveRight = new Sprite(0, 0, MainActivity.Res.getTextureRegion("Right"), MainActivity.Me.getVertexBufferObjectManager());
		spriteMoveRight.setPosition(spriteMoveLeft.getX() + spriteMoveLeft.getWidth() + 20, 50);
		spriteMoveRight.setVisible(true);
		attachChild(spriteMoveRight);

		spriteFire = new Sprite(0, 0, MainActivity.Res.getTextureRegion("Fire"), MainActivity.Me.getVertexBufferObjectManager());
		spriteFire.setPosition(MainActivity.getWidth() - (spriteFire.getWidth() + 40), 50);
		spriteFire.setVisible(true);
		attachChild(spriteFire);
		
	}
	
	public void updateGoldIndicator(int value){
		textGold.setText(String.valueOf(value));
		textGold.setPosition(spriteGold.getX() + spriteGold.getWidth()/2 + 10 + textGold.getWidth()/2, MainActivity.getHeight() - 24);
	}
	

	public void updateHealthIndicator(int currentHealth, int maxHealth) {

		healthIndicator.setY(MainActivity.getHeight());
		float height = - (10 + MainActivity.Res.getTextureRegion("heart").getHeight());
		
		for (int i = 0; i < maxHealth; i++) {
			if (i < currentHealth) {
				healthIndicator.draw(MainActivity.Res.getTextureRegion("heart"), 40 + i * 36, height, 32, 32, 0, 1, 1, 1, 1);
			} else {
				healthIndicator.draw(MainActivity.Res.getTextureRegion("heart_died"), 40 + i * 36, height, 32, 32, 0, 1, 1, 1, 1);
			}
		}
		healthIndicator.submit();
	}
	
	
	
	@Override
	public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent) {
		

		if(pSceneTouchEvent.isActionCancel() || pSceneTouchEvent.isActionOutside() || pSceneTouchEvent.isActionUp()){
			resetStates();
		}
		
		if(pSceneTouchEvent.isActionMove() || pSceneTouchEvent.isActionDown()){
			
			resetStates();
			
			// Check radial collisions
			if(collideCircleByPoint(spriteMoveLeft, BUTTON_RADIUS, pSceneTouchEvent.getX(), pSceneTouchEvent.getY())){
				states[Buttons.LEFT.ordinal()] = true;
			}
			
			if(collideCircleByPoint(spriteMoveRight, BUTTON_RADIUS, pSceneTouchEvent.getX(), pSceneTouchEvent.getY())){
				states[Buttons.RIGHT.ordinal()] = true;
			}
			
			if(collideCircleByPoint(spriteFire, BUTTON_RADIUS, pSceneTouchEvent.getX(), pSceneTouchEvent.getY())){
				states[Buttons.FIRE.ordinal()] = true;
			}
			
		}
		
		
		return super.onSceneTouchEvent(pSceneTouchEvent);
	}
	
	public void resetStates(){
		for(int i=0; i<states.length; i++){
			states[i] = false;
		}
	}
	
	public boolean isKeyDown(Buttons code){
		return states[code.ordinal()];
	}
	
	public static boolean collideCircleByPoint(Sprite spr, float radius, float x2, float y2) {

		float dx = spr.getX() - x2;
		float dy = spr.getY() - y2;
		float dist = dx * dx + dy * dy;

		if (dist <= radius * radius)
			return true;

		return false;
	}
	
	
}
