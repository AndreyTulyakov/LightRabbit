package mhyhre.lightrabbit.game;

public class SharkUnit extends Enemy {

	public static final float sSinkSpeed = 1.0f;
	public static final float sSpeed = 1;
	
	float bright;
	float mWaterLevel;
	
	
	public SharkUnit() {
		super(EnemyType.SHARK, 150);
		

		bright = 1;
	}
	
	public float getBright(){
		return bright;
	}

	public float getWaterLevel() {
		return mWaterLevel;
	}

	public void setWaterLevel(float mWaterLevel) {
		this.mWaterLevel = mWaterLevel;
	}

	@Override
	public void update() {
		if(mDied == true){
			mX -= sSpeed;
			mY += sSinkSpeed;
			if(bright>0){
				bright-=0.01f;
				if(bright<0.1f)
					bright = 0.0f;
			}
		}else{
			mX -= sSpeed;	
			mY = (float) (mWaterLevel + 20 * Math.sin(mX/(Math.PI*4)));
		}
	}

	
}
