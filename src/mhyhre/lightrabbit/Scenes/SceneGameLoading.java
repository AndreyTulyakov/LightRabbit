package mhyhre.lightrabbit.Scenes;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.MhyhreScene;
import mhyhre.lightrabbit.R;
import mhyhre.lightrabbit.game.Levels.LevelsList;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.color.Color;

import android.util.Log;


public class SceneGameLoading extends MhyhreScene {

	private Rectangle mRotationRect;
	private Text mTextPress;
	private Text mLevelNumber;

	private boolean mLoaded = false;
	

	

	public SceneGameLoading() {
		

		
		setBackgroundEnabled(false);
		
		Rectangle backRect = new Rectangle(0, 0, MainActivity.getWidth(), MainActivity.getHeight(), MainActivity.Me.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
					
					if (mLoaded) {

						MainActivity.getRootScene().SetState(SceneStates.Game);
						
						setLoaded(false);
					}
					
					Log.i(MainActivity.DEBUG_ID, "SceneGameLoading: Start game");
					MainActivity.vibrate(30);
				}
				return true;
			}
		};
		backRect.setPosition(MainActivity.getHalfWidth(), MainActivity.getHalfHeight());
		backRect.setColor(0.0f,0.0f,0.1f,0.75f);
		attachChild(backRect);
		registerTouchArea(backRect);

		mTextPress = new Text(0, 0, MainActivity.Res.getFont("White Furore 24"), MainActivity.Me.getString(R.string.tapForContinue), 100, MainActivity.Me.getVertexBufferObjectManager());
		mTextPress.setPosition(MainActivity.getHalfWidth(), 30 + mTextPress.getHeight()/2);

		mLevelNumber = new Text(0, 0, MainActivity.Res.getFont("White Furore"), "" , 100, MainActivity.Me.getVertexBufferObjectManager());
		mLevelNumber.setPosition(MainActivity.getHalfWidth(), MainActivity.getHalfHeight());
		
		float h = mTextPress.getHeight() + 40;

		mRotationRect = new Rectangle(MainActivity.getHalfWidth(), mTextPress.getY(), MainActivity.getWidth(), h, MainActivity.Me.getVertexBufferObjectManager());
		mRotationRect.setColor(Color.BLACK);

		attachChild(mRotationRect);
		attachChild(mTextPress);
		attachChild(mLevelNumber);
		
		setLoaded(false);
	}

	float rectAlpha = 0;

	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {

		if (mLoaded) {
			rectAlpha += 0.03f;
			if (rectAlpha >= Math.PI) {
				rectAlpha = 0;
			}
			mTextPress.setAlpha((float)(1.0f - Math.sin(rectAlpha)));
		}
		
		super.onManagedUpdate(pSecondsElapsed);
	}

	public boolean isLoaded() {
		return mLoaded;
	}

	public void setLoaded(boolean mLoaded) {
		this.mLoaded = mLoaded;

	}
	
	public void setShownLevelNumber(int levelNumber){
		mLevelNumber.setText(MainActivity.Me.getString(R.string.level) + ": " + levelNumber);
	}
}
