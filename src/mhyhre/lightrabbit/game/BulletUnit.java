package mhyhre.lightrabbit.game;

import org.andengine.entity.primitive.Vector2;
import org.andengine.entity.shape.RectangularShape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.vbo.IVertexBufferObject;

public class BulletUnit extends RectangularShape{

	public static final float sSinkSpeed = 1.0f;
	public static final float sGravity = -0.15f;
	
	private Vector2 mAcceleration;
	boolean mSink;

	public BulletUnit(float pX, float pY, float size) {
		super(pX, pY, size, size, null);

		mAcceleration = new Vector2(0, 0);
		mSink = false;
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
		
		if(mSink){
			mY += sSinkSpeed;
		}else{
			mX += mAcceleration.x;
			mY += mAcceleration.y;
			
			mAcceleration.y -= sGravity;
		}
	}

	@Override
	public IVertexBufferObject getVertexBufferObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onUpdateVertices() {
		// TODO Auto-generated method stub
		
	}
	
	public boolean collideWithSpriteByCircle(Sprite spr, float radius){
		
		float dx = getX() - (spr.getX() + spr.getWidth()/2);
		float dy = getY() - (spr.getY() + spr.getHeight()/2);
		
		float c = (dx*dx) + (dy*dy);
		
		if(c <= radius*radius)
			return true;
		return false;
	}
}
