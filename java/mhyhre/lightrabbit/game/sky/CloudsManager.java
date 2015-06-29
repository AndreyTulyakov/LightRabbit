/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.sky;

import org.andengine.entity.sprite.batch.SpriteBatch;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.util.ArrayList;
import java.util.List;

import mhyhre.lightrabbit.MainActivity;


final public class CloudsManager extends SpriteBatch {

    private int TYPE_COUNTER = 0;
    private static final int CLOUDS_MAXIMUM = 6;

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
            cloud.setPosition(mCloudStep * i, mSkySeparator * 5.5f - mSkySeparator * (TYPE_COUNTER % 2));
            cloud.setScale(1 - (TYPE_COUNTER % 2) * 0.5f);
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


            if(cloud.type%2 == 0)
            {
                cloud.posX -= 1.0f;
            } else {
                cloud.posX -= 0.5f;
            }

            if (cloud.posX < -mCloudStep) {
                cloud.type = getNextCloudType();
                cloud.setPosition(mCloudStep * (this.mCapacity - 1), mSkySeparator * 6 - mSkySeparator * (TYPE_COUNTER % 2));
                cloud.setScale(1 - (TYPE_COUNTER % 2) * 0.5f);
            }
        }
    }

    private void updateGraphics() {
        float c = 0.0f;

        for (CloudUnit cloud : mClouds) {

            this.draw(mRegions[cloud.type], cloud.posX, cloud.posY, mRegions[cloud.type].getWidth(), mRegions[cloud.type].getHeight(),
                    cloud.getScale()*MainActivity.PIXEL_MULTIPLIER, cloud.getScale()*MainActivity.PIXEL_MULTIPLIER, 1, 1, 1, 1);
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
