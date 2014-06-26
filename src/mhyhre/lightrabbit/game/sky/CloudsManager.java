package mhyhre.lightrabbit.game.sky;

import java.util.ArrayList;
import java.util.List;

import mhyhre.lightrabbit.MainActivity;

import org.andengine.entity.sprite.batch.SpriteBatch;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

final public class CloudsManager extends SpriteBatch {

    private int TYPE_COUNTER = 0;
    private static final int CLOUDS_MAXIMUM = 5;

    private List<CloudUnit> mClouds;
    private ITextureRegion[] mRegions;
    private float mCloudStep;
    private float mSkySeparator;
    
    

    public CloudsManager(VertexBufferObjectManager vertexBufferObjectManager) {
        super(0, 0, MainActivity.resources.getTextureAtlas("Clouds"), CLOUDS_MAXIMUM + 2, vertexBufferObjectManager);

        mClouds = new ArrayList<CloudUnit>();

        mRegions = new TextureRegion[4];
        mRegions[0] = MainActivity.resources.getTextureRegion("cloud1");
        mRegions[1] = MainActivity.resources.getTextureRegion("cloud2");
        mRegions[2] = MainActivity.resources.getTextureRegion("cloud3");
        mRegions[3] = MainActivity.resources.getTextureRegion("cloud4");

        mSkySeparator = MainActivity.getHeight() / 7;

        mCloudStep = MainActivity.getWidth() / (this.mCapacity - 2);

        CloudUnit cloud;

        for (int i = 0; i < this.mCapacity; i++) {

            cloud = new CloudUnit();
            cloud.type = getNextCloudType();
            cloud.SetPosition(mCloudStep * i, mSkySeparator * 5.5f - mSkySeparator * (TYPE_COUNTER % 2));
            mClouds.add(cloud);
        }
    }

    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {

        updateClouds();
        updateGraphics();

        super.onManagedUpdate(pSecondsElapsed);
    }

    private void updateClouds() {

        for (CloudUnit cloud : mClouds) {

            cloud.PosX--;

            if (cloud.PosX < -mCloudStep) {
                cloud.type = getNextCloudType();
                cloud.SetPosition(mCloudStep * (this.mCapacity - 1), mSkySeparator * 6 - mSkySeparator * (TYPE_COUNTER % 2));
            }
        }

    }

    private void updateGraphics() {

        for (CloudUnit cloud : mClouds) {

            this.draw(mRegions[cloud.type], cloud.PosX, cloud.PosY, mRegions[cloud.type].getWidth(), mRegions[cloud.type].getHeight(), 0, 2, 2, 1, 1, 1, 1);
        }

        submit();
    }

    private int getNextCloudType() {
        TYPE_COUNTER++;
        if (TYPE_COUNTER > 3)
            TYPE_COUNTER = 0;
        return TYPE_COUNTER;
    }

}
