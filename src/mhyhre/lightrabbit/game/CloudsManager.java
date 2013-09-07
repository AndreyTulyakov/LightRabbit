package mhyhre.lightrabbit.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mhyhre.lightrabbit.MainActivity;

import org.andengine.entity.sprite.batch.SpriteBatch;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

final public class CloudsManager extends SpriteBatch {
	
	private int TYPE_COUNTER = 0; 
	
	private List<CloudUnit> mClouds;
	private ITextureRegion[] mRegions;
	private float mCloudStep;
	private float mSkySeparator;

	public CloudsManager(int pMaxCapacity, VertexBufferObjectManager vertexBufferObjectManager) {
		super(0, 0, MainActivity.Res.getTextureAtlas("Clouds"), pMaxCapacity, vertexBufferObjectManager);
		
		mClouds = new ArrayList<CloudUnit>();
		
		mRegions = new TextureRegion[4];
		mRegions[0] = MainActivity.Res.getTextureRegion("cloud1");
		mRegions[1] = MainActivity.Res.getTextureRegion("cloud2");
		mRegions[2] = MainActivity.Res.getTextureRegion("cloud3");
		mRegions[3] = MainActivity.Res.getTextureRegion("cloud4");
		
		
		mSkySeparator = MainActivity.getHeight()/7;
		
		mCloudStep = MainActivity.getWidth()/(pMaxCapacity - 1);
		
		CloudUnit cloud;
		
		for(int i=0; i < pMaxCapacity; i++){
			
			cloud = new CloudUnit();	
			cloud.type = getNextCloudType();
			cloud.SetPosition( mCloudStep * i, mSkySeparator + mSkySeparator * (TYPE_COUNTER%2));
			mClouds.add(cloud);
		}
	}
	

	
	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		
		updateClouds();
		updateGraphics();	
			
		super.onManagedUpdate(pSecondsElapsed);
	}

	private void updateClouds(){
			
		for(CloudUnit cloud: mClouds){
			
			cloud.PosX--;
			
			if(cloud.PosX < -mCloudStep){		
				cloud.type = getNextCloudType();
				cloud.SetPosition( mCloudStep * this.mCapacity, mSkySeparator + mSkySeparator * (TYPE_COUNTER%2));
			}
		}
		
		
	}
	
	private void updateGraphics(){

		for(CloudUnit cloud: mClouds){
			
			this.draw(mRegions[cloud.type],
					cloud.PosX, cloud.PosY, 
					mRegions[cloud.type].getWidth(), mRegions[cloud.type].getHeight(),
					0, 4, 4, 1, 1, 1, 1);
		}
		
		submit();
	}
	
	private int getNextCloudType(){
		TYPE_COUNTER++;
		if(TYPE_COUNTER>3) TYPE_COUNTER = 0;
		return TYPE_COUNTER;
	}

}
