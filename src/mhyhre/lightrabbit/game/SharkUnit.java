package mhyhre.lightrabbit.game;

public class SharkUnit {

	public static final float sSinkSpeed = 1.0f;
	public static final float sSpeed = 1;
	
	float mX, mY;
	float width, height;
	boolean mSink;
	float bright;
	
	public SharkUnit() {
		mX = 0;
		mY = 0;
		mSink = false;
		width = 0;
		height = 0;
		bright = 1;
	}
	
	public void setSize(float w, float h){
		width = w;
		height = h;
	}
	
	// center X
	public float getCX(){
		return mX+width/2.0f;
	}
	
	// center Y
	public float getCY(){
		return mY+height/2.0f;
	}
	
	
	public float getX() {
		return mX;
	}

	public void setX(float mX) {
		this.mX = mX;
	}

	public float getY() {
		return mY;
	}

	public void setY(float mY) {
		this.mY = mY;
	}
	
	public void setPosition(float pX, float pY) {
		mX = pX;
		mY = pY;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public boolean isSink() {
		return mSink;
	}

	public void setSink(boolean mSink) {
		this.mSink = mSink;
	}
	
	public float getBright(){
		return bright;
	}

	public void Update(float waterLevel){
		
		if(mSink){
			mX -= sSpeed;
			mY += sSinkSpeed;
			if(bright>0){
				bright-=0.01f;
				if(bright<0.1f)
					bright = 0.0f;
			}
		}else{
			mX -= sSpeed;	
			mY = (float) (waterLevel + 10 + 20 * Math.sin(mX/(Math.PI*4)));

		}
	}
}
