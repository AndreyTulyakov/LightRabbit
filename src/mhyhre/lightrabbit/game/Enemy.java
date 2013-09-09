package mhyhre.lightrabbit.game;

public abstract class Enemy {


	protected EnemyType mEnemyType;
	protected int mHealth;
	protected boolean mDied;
	protected float mX, mY;
	protected float mWidth, mHeight;
	
	public Enemy(EnemyType pType, int pMaxHealth) {
		mHealth = pMaxHealth;
		mEnemyType = pType;
		
		mX = 0;
		mY = 0;
		mWidth = 0;
		mHeight = 0;
	}

	public int getHealth() {
		return mHealth;
	}

	public void setHealth(int pHealth) {
		this.mHealth = pHealth;
	}
	

	public boolean isDied() {
		return mDied;
	}

	public void setDied(boolean mDied) {
		this.mDied = mDied;
	}
	
	public abstract void update();
	
	
	public void setSize(float w, float h){
		mWidth = w;
		mHeight = h;
	}

	public float getCX(){
		return mX+mWidth/2.0f;
	}

	public float getCY(){
		return mY+mHeight/2.0f;
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
	
	public void setCenteredPosition(float pX, float pY) {
		mX = pX - mWidth/2;
		mY = pY - mHeight/2;
	}

	public float getWidth() {
		return mWidth;
	}

	public void setWidth(float width) {
		this.mWidth = width;
	}

	public float getHeight() {
		return mHeight;
	}

	public void setHeight(float height) {
		this.mHeight = height;
	}

	public EnemyType getEnemyType() {
		return mEnemyType;
	}
	
}
