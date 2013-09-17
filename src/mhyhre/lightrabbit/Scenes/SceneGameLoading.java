package mhyhre.lightrabbit.Scenes;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.MhyhreScene;
import mhyhre.lightrabbit.R;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.color.Color;

import android.util.Log;


public class SceneGameLoading extends MhyhreScene {

	private Rectangle mRotationRect;
	private Text mCaption;

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
					
					Log.i(MainActivity.DebugID, "SceneGameLoading: Start game");
					MainActivity.vibrate(30);
				}
				return true;
			}
		};
		backRect.setPosition(MainActivity.getHalfWidth(), MainActivity.getHalfHeight());
		backRect.setColor(0.0f,0.0f,0.1f,0.5f);
		attachChild(backRect);
		registerTouchArea(backRect);

		mCaption = new Text(0, 0, MainActivity.Res.getFont("Furore"), MainActivity.Me.getString(R.string.tapForContinue), 100, MainActivity.Me.getVertexBufferObjectManager());
		mCaption.setPosition(10 + mCaption.getWidth()/2, 30 + mCaption.getHeight()/2);

		float h = mCaption.getHeight() + 40;

		mRotationRect = new Rectangle(MainActivity.getHalfWidth(), mCaption.getY(), MainActivity.getWidth(), h, MainActivity.Me.getVertexBufferObjectManager());
		mRotationRect.setColor(Color.WHITE);

		attachChild(mRotationRect);
		attachChild(mCaption);

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
			mRotationRect.setAlpha((float)(1.0f - Math.sin(rectAlpha)));
		} else {
			rectAlpha = 0;
			mRotationRect.setAlpha(1);
		}
		super.onManagedUpdate(pSecondsElapsed);
	}

	public boolean isLoaded() {
		return mLoaded;
	}

	public void setLoaded(boolean mLoaded) {
		this.mLoaded = mLoaded;

	}
}
