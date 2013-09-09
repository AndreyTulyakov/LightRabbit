package mhyhre.lightrabbit.game.units;

import mhyhre.lightrabbit.game.Enemy;
import mhyhre.lightrabbit.game.EnemyType;

public class PirateBoatUnit extends Enemy {

	public static final float sSinkSpeed = 1.0f;
	public static final float sSpeed = 0.5f;
	
	float bright;
	float mWaterLevel;
	
	public PirateBoatUnit() {
		super(EnemyType.PIRATE_BOAT, 60);
		
		setSize(64, 22);
		bright=1;
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
			mY = (float) (mWaterLevel-14);
		}
	}

}
