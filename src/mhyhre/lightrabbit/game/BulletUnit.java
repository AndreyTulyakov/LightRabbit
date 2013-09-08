package mhyhre.lightrabbit.game;

import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Vector2;

public class BulletUnit extends Entity {

	public static final float sSinkSpeed = 1.0f;
	public static final float sGravity = -0.15f;
	
	private Vector2 mAcceleration;
	boolean mSink;
	int mBoom;

	public BulletUnit(float pX, float pY) {
		super(pX,pY);
		
		mAcceleration = new Vector2(0, 0);
		mSink = false;
		mBoom = 0;
	}

	public Vector2 getPosition() {
		return new Vector2(mX, mY);
	}

	public Vector2 getAcceleration() {
		return new Vector2(mAcceleration);
	}


	public void setAcceleration(float pX, float pY) {
		mAcceleration.set(pX, pY);
	}

	public void setAccelerationByAngle(float angleGrad, float power) {
		float radAngle = (float) Math.toRadians(angleGrad);
		mAcceleration.set((float) Math.cos(radAngle), (float) Math.sin(radAngle));
		mAcceleration.mul(power);	
	}
	
	public boolean isSink() {
		return mSink;
	}

	public void setSink(boolean pSink) {
		this.mSink = pSink;
	}
	
	public void update(){
		
		if(mBoom > 0){
			if(mBoom == 1){
				mBoom = -1;
			}else{
				mBoom--;
			}	
		}
			
		if(mSink){
			mY += sSinkSpeed;
			if(mAcceleration.x>0.1f){
				mAcceleration.x/=1.05f;
				mX += mAcceleration.x;
			}
			mX -= 1;
			
		}else{
			mX += mAcceleration.x;
			mY += mAcceleration.y;
			
			mAcceleration.y -= sGravity;
		}
	}
	
	public boolean collideWithCircle(float x, float y, float radius){
		
		float dx = getX() - (x);
		float dy = getY() - (y);
		
		float c = (dx*dx) + (dy*dy);
		
		if(c <= radius*radius)
			return true;
		return false;
	}

	public int getBoom() {
		return mBoom;
	}

	public void setBoom(int mBoom) {
		this.mBoom = mBoom;
	}
}
