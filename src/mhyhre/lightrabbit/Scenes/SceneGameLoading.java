package mhyhre.lightrabbit.Scenes;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.MhyhreScene;
import mhyhre.lightrabbit.R;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.color.Color;

public class SceneGameLoading extends MhyhreScene {

	private Rectangle mRotationRect;
	private Text mCaption;

	private boolean mTouchDown = false;
	private boolean mLoaded = false;

	public SceneGameLoading() {
		setBackgroundEnabled(false);
		
		Rectangle backRect = new Rectangle(0, 0, MainActivity.getWidth(), MainActivity.getHeight(), MainActivity.Me.getVertexBufferObjectManager());
		backRect.setColor(0.0f,0.0f,0.1f,0.5f);
		attachChild(backRect);

		mCaption = new Text(0, 0, MainActivity.Res.getFont("Furore"), "", 100, MainActivity.Me.getVertexBufferObjectManager());
		mCaption.setPosition(10, MainActivity.getHeight() - (mCaption.getHeight() + 10));

		float h = mCaption.getHeight() + 20;

		mRotationRect = new Rectangle(0, MainActivity.getHeight() - h, MainActivity.getWidth(), h, MainActivity.Me.getVertexBufferObjectManager());
		mRotationRect.setColor(Color.WHITE);

		attachChild(mRotationRect);
		attachChild(mCaption);

		setLoaded(false);
	}

	float rectAlpha = 0;

	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {

		if (mLoaded) {
			rectAlpha += 0.05f;
			if (rectAlpha >= Math.PI) {
				rectAlpha = 0;
			}
			mRotationRect.setAlpha((float) Math.cos(rectAlpha));
		} else {
			rectAlpha = 0;
			mRotationRect.setAlpha(1);
		}
		super.onManagedUpdate(pSecondsElapsed);
	}

	@Override
	public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent) {

		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			mTouchDown = true;
		}

		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
			if (mTouchDown && mLoaded) {
				mTouchDown = false;

				setLoaded(false);

				MainActivity.getRootScene().SetState(SceneStates.Game);
			}
		}

		return super.onSceneTouchEvent(pSceneTouchEvent);
	}

	public boolean isLoaded() {
		return mLoaded;
	}

	public void setLoaded(boolean mLoaded) {
		this.mLoaded = mLoaded;

		if (mLoaded) {
			mCaption.setText(MainActivity.Me.getString(R.string.tapForContinue));
		} else {
			mCaption.setText(MainActivity.Me.getString(R.string.loading));
		}
	}
}
