package mhyhre.lightrabbit.game;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.Scenes.WaterPolygon;

import org.andengine.entity.Entity;
import org.andengine.entity.scene.background.IBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import android.util.Log;

public class SkyManager extends Entity {

	// Per second
	public final float mTimeSpeed = 0.04f;
	float mTime;

	Sprite spriteSun;
	Sprite spriteMoon;

	Color skyColor;
	Color daySkyColor, nightSkyColor;

	boolean mNight;
	IBackground mBackground;
	WaterPolygon mWater;

	public SkyManager(IBackground pBackground, WaterPolygon pWater, VertexBufferObjectManager vertexBufferObjectManager) {

		this.setPosition(0, 0);

		nightSkyColor = new Color(0.1f, 0.1f, 0.3f);
		daySkyColor = new Color(0.40f, 0.88f, 0.99f);
		skyColor = new Color(daySkyColor);

		mBackground = pBackground;
		mWater = pWater;

		mTime = (float) (Math.PI * 1.7f);
		mNight = false;

		spriteSun = new Sprite(10, 10, MainActivity.Res.getTextureRegion("sun"), vertexBufferObjectManager);
		spriteSun.setScale(2);
		this.attachChild(spriteSun);

		spriteMoon = new Sprite(10, 10, MainActivity.Res.getTextureRegion("moon"), vertexBufferObjectManager);
		spriteMoon.setScale(2);
		spriteMoon.setAlpha(0);
		this.attachChild(spriteMoon);

	}

	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {

		mTime += mTimeSpeed * pSecondsElapsed;
		if (mTime > Math.PI * 2) {
			mTime = 0;
		}

		if (mTime < Math.PI) {
			mNight = true;
			desireColor(skyColor, nightSkyColor);

		} else {
			mNight = false;
			desireColor(skyColor, daySkyColor);
		}
		
		mBackground.setColor(skyColor);

		float radius = 250;

		float dx = (float) (1.3f * radius * Math.cos(mTime));
		float dy = (float) (radius * Math.sin(mTime));

		spriteSun.setPosition(MainActivity.getHalfWidth() + dx, MainActivity.getHalfHeight() + dy);
		spriteMoon.setPosition(MainActivity.getHalfWidth() - dx, MainActivity.getHalfHeight() - dy);

		if (dy < 100 && dy > 0) {
			spriteMoon.setAlpha( dy / 100.0f);
		}
		
		if (dy > -100 && dy < 0) {
			spriteSun.setAlpha( -dy / 100.0f);
		}

		if (spriteSun.getAlpha() > 1)
			spriteSun.setAlpha(1);
		if (spriteMoon.getAlpha() > 1)
			spriteMoon.setAlpha(1);

		super.onManagedUpdate(pSecondsElapsed);
	}

	private void desireColor(Color current, Color target) {

		final float d = 0.005f;
		final float dgreen = 0.005f;
		
		if(isEqualColor(current, target, 0.02f)){
			return;
		}

		float cr = current.getRed(), cg = current.getGreen(), cb = current.getBlue();

		if (cr > target.getRed())
			current.setRed(cr - d);

		if (cr < target.getRed())
			current.setRed(cr + d);

		if (cg > target.getGreen())
			current.setGreen(cg - d*2);

		if (cg < target.getGreen())
			current.setGreen(cg + dgreen);

		if (cb > target.getBlue())
			current.setBlue(cb - d);

		if (cb < target.getBlue())
			current.setBlue(cb + d);
	}
	
	private boolean isEqualColor(Color c1, Color c2, float e){
		
		if( Math.abs(c1.getRed()-c2.getRed())<e){
			if( Math.abs(c1.getGreen()-c2.getGreen())<e){
				if( Math.abs(c1.getBlue()-c2.getBlue())<e){
					return true;
				}	
			}
		}
		
		return false;
	}

}
