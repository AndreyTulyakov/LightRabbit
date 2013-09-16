package mhyhre.lightrabbit.game;


public class LevelItem {
	
	float mX, mY;
	float mWidth, mHeight;
	boolean mLocked;
	int mLevelNumber;
	
	public int getLevelNumber() {
		return mLevelNumber;
	}

	public LevelItem(float x, float y, float width, float height, int levelNumber) {
		mX = x;
		mY = y;
		mWidth = width;
		mHeight = height;
		mLevelNumber = levelNumber;
		mLocked = false;
	}
	
	public float getLeftTopX(){
		return mX - mWidth/2;
	}
	
	public float getLeftTopY(){
		return mY - mHeight/2;
	}

	public float getX() {
		return mX;
	}

	public void setX(float pX) {
		this.mX = pX;
	}

	public float getY() {
		return mY;
	}

	public void setY(float pY) {
		this.mY = pY;
	}

	public float getWidth() {
		return mWidth;
	}

	public void setWidth(float pWidth) {
		this.mWidth = pWidth;
	}

	public float getHeight() {
		return mHeight;
	}

	public void setHeight(float pHeight) {
		this.mHeight = pHeight;
	}

	public boolean isLocked() {
		return mLocked;
	}

	public void setLocked(boolean locked) {
		this.mLocked = locked;
	}
	
	public boolean isCollided(float x, float y){
		
		float dx = mX - mWidth/2;
		float dy = mY - mHeight/2;
		
		if(x >= dx && x <= dx+mWidth ){
			if(y >= dy && y <= dy+mHeight ){
				return true;
			}
		}
		return false;
	}
}
