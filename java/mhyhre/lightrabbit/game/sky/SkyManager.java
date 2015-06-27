/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.sky;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.sky.lighting.LightingManager;
import mhyhre.lightrabbit.scenes.utils.EaseScene;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;

import android.graphics.PointF;
import android.util.Log;


public class SkyManager extends EaseScene {

    private static final int SUNRISE_START_TIME = 7;
    private static final int SUNRISE_END_TIME = 8;
    
    private static final int SUNSET_START_TIME = 18;
    private static final int SUNSET_END_TIME = 20;
    
    private final float sunriseDuration;
    private final float sunsetDuration;
    
    private SkyPalette palette;
    
    private int mTimeSpeed = 100;
    private boolean timeStopped = false;
    
    private double mTime; /* Time in range [0, 23] */

    private Background mBackground;
    
    private Sprite spriteSun;
    private Sprite spriteMoon;
    
    private LightingManager lightingManager;
    private Color skyColor;

    private final PointF radius;
    private int stopInTime = -1;
    
    private Rain rain;

    public SkyManager(VertexBufferObjectManager vertexBufferObjectManager) {
        
        mBackground = new Background(0.8f, 0.8f, 0.8f);
        setBackground(mBackground);
        setBackgroundEnabled(true);
        
        this.setPosition(0, 0);
        palette = SkyPalette.CLEAN;
        
        lightingManager = new LightingManager();
        
        sunriseDuration = SUNRISE_END_TIME - SUNRISE_START_TIME;
        sunsetDuration = SUNSET_END_TIME - SUNSET_START_TIME;

        skyColor = new Color(0,0,0);

        spriteSun = new Sprite(10, 10, MainActivity.resources.getTextureRegion("sun"), vertexBufferObjectManager);
        spriteSun.setScale(MainActivity.PIXEL_MULTIPLIER);
        this.attachChild(spriteSun);

        spriteMoon = new Sprite(10, 10, MainActivity.resources.getTextureRegion("moon"), vertexBufferObjectManager);
        spriteMoon.setScale(MainActivity.PIXEL_MULTIPLIER);
        this.attachChild(spriteMoon);
        
        radius = new PointF(300, 250);

        
        attachChild(lightingManager);
          
        setCurrentTime(0);
        show();
    }
    
    public void enableRain() {
        if(rain == null) {
            rain = new Rain();
            attachChild(rain);
            Log.i(MainActivity.DEBUG_ID, "Rain attached.");
        }
    }
    
    public void disableRain() {
        if(rain != null) {
            detachChild(rain);
            Log.i(MainActivity.DEBUG_ID, "Rain detached.");
            rain = null;
        }
    }

    public void setPalette(String stringArg) {
        try {
            setPalette(SkyPalette.valueOf(stringArg));
        } catch(IllegalArgumentException e) {
            Log.i(MainActivity.DEBUG_ID, "SkyManager: can't set palette:" + stringArg);
        }
    }

    public void setPalette(SkyPalette palette) {
        this.palette = palette;
    }

    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {

        if(stopInTime == ((int)mTime)) {
            timeStopped = true;
        }
        
        if(timeStopped == false) {
            mTime += (mTimeSpeed/1000.0) * pSecondsElapsed;
        }
        
        if (mTime >= 24.00) {
            mTime = 0;
        }
        
        if(isNightTime()) {
            desireColor(skyColor, palette.getNightColor());
        } 
        if(isDayTime()) {
            desireColor(skyColor, palette.getDayColor());
        }
        
        if(palette.isHasGradients()) {
            if(isSunriseTime()) {
                desireColor(skyColor, palette.getSunriseColor());
            }
            if(isSunsetTime()) {
                desireColor(skyColor, palette.getSunsetColor());
            } 
        }

        updateSunAndMoonAlpha();
        
        mBackground.setColor(skyColor);
        
        double offsetRad = Math.PI/2.0;
        double timeInRad = (mTime/(24.0/(Math.PI*2.0))) + offsetRad;

        float dx = (float) (1.3f * radius.x * Math.cos(timeInRad));
        float dy = -(float) (radius.y * Math.sin(timeInRad));

        float centerHeight = (MainActivity.getHeight()/7)*3;
        spriteSun.setPosition(MainActivity.getHalfWidth() + dx, centerHeight + dy);
        spriteMoon.setPosition(MainActivity.getHalfWidth() - dx, centerHeight - dy);

        super.onManagedUpdate(pSecondsElapsed);
    }

    
    private void desireColor(Color current, Color target) {

        final float d = 0.01f;

        if (isEqualColor(current, target, 0.02f)) {
            return;
        }

        float cr = current.getRed(), cg = current.getGreen(), cb = current.getBlue();

        if (cr > target.getRed())
            current.setRed(cr - d);

        if (cr < target.getRed())
            current.setRed(cr + d);

        if (cg > target.getGreen())
            current.setGreen(cg - d * 2);

        if (cg < target.getGreen())
            current.setGreen(cg + d);

        if (cb > target.getBlue())
            current.setBlue(cb - d);

        if (cb < target.getBlue())
            current.setBlue(cb + d);
    }

    
    private boolean isEqualColor(Color c1, Color c2, float e) {

        if (Math.abs(c1.getRed() - c2.getRed()) < e) {
            if (Math.abs(c1.getGreen() - c2.getGreen()) < e) {
                if (Math.abs(c1.getBlue() - c2.getBlue()) < e) {
                    return true;
                }
            }
        }

        return false;
    }

    // Use 0-23 values to set hours
    public void setCurrentTime(int time) {
        mTime = time;
        
        updateSunAndMoonAlpha();
        
        // Set night or day state
        if(isNightTime()) {
            skyColor = palette.getNightColor();
        } 
        if(isDayTime()) {
            skyColor = palette.getDayColor();
        }
        
        if(palette.isHasGradients()) {
            if(isSunriseTime()) {
                skyColor = palette.getSunriseColor();
            }
            if(isSunsetTime()) {
                skyColor = palette.getSunsetColor();
            } 
        }
        
        mBackground.setColor(skyColor);
        onManagedUpdate(0);
    }
    
    
    public int getCurrentTime() {
        return (int)mTime;
    }
    
    
    private void updateSunAndMoonAlpha() {
        
        switch(palette) {
        
        case IMPERIAL_DARK:
            if(isNightTime()) {
                spriteSun.setAlpha(0);
                spriteMoon.setAlpha(1.0f);
            } 
            
            if(isSunriseTime()) {
                float sunriseProgress = (float)(mTime-SUNRISE_START_TIME)/sunriseDuration;
                spriteSun.setAlpha(0);
                spriteMoon.setAlpha(1.0f - sunriseProgress);
            }
            
            if(isDayTime()) {
                spriteSun.setAlpha(0.0f);
                spriteMoon.setAlpha(0.5f);
            }
            
            if(isSunsetTime()) {
                float sunsetProgress = (float)(mTime-SUNSET_START_TIME)/sunsetDuration;
                spriteSun.setAlpha(0);
                spriteMoon.setAlpha(sunsetProgress);
            }
            break;
        
        case BIOHAZARD:
            
            break;
            
        case CLEAN:
            if(isNightTime()) {
                spriteSun.setAlpha(0);
                spriteMoon.setAlpha(1.0f);
            } 
            
            if(isSunriseTime()) {
                float sunriseProgress = (float)(mTime-SUNRISE_START_TIME)/sunriseDuration;
                spriteSun.setAlpha(sunriseProgress);
                spriteMoon.setAlpha(1.0f - sunriseProgress);
            }
            
            if(isDayTime()) {
                spriteSun.setAlpha(1.0f);
                spriteMoon.setAlpha(0);
            }
            
            if(isSunsetTime()) {
                float sunsetProgress = (float)(mTime-SUNSET_START_TIME)/sunsetDuration;
                spriteSun.setAlpha(1.0f - sunsetProgress);
                spriteMoon.setAlpha(sunsetProgress);
            }
            
            break;
            
        case RAINY:
            if(spriteSun.getAlpha() > 0.01f) {
                spriteSun.setAlpha(spriteSun.getAlpha() - 0.005f);
            } else {
                spriteSun.setAlpha(0);
            }
            
            if(spriteMoon.getAlpha() > 0.0f) {
                spriteMoon.setAlpha(spriteMoon.getAlpha() - 0.005f);
            } else {
                spriteMoon.setAlpha(0);
            }
            break;
        default:
            break;
        
        }    
    }
    
    
    public void stopTimeIn(int time) {
        stopInTime = time;
    }
    
    public void stopTime() {
        timeStopped = true;
    }
    
    public void startTime() {
        timeStopped = false;
    }

    public int getTimeSpeed() {
        return mTimeSpeed;
    }

    /**
     * Default value 100%
     * @param mTimeSpeed integer in range [0, ..], recomended maximum 1000
     */
    public void setTimeSpeed(int mTimeSpeed) {
        this.mTimeSpeed = mTimeSpeed;
    }

    private boolean isSunsetTime() {
        if(mTime > SUNSET_START_TIME && mTime <= SUNSET_END_TIME) {
            return true;
        }
        return false;
    }
    
    private boolean isSunriseTime() {
        if(mTime > SUNRISE_START_TIME && mTime <= SUNRISE_END_TIME) {
            return true;
        }
        return false;
    }
    
    private boolean isDayTime() {
        if(mTime > SUNRISE_END_TIME && mTime <= SUNSET_END_TIME) {
            return true;
        }
        return false;
    }
    
    private boolean isNightTime() {
        if(mTime > SUNSET_END_TIME || mTime <= SUNRISE_START_TIME) {
            return true;
        }
        return false;
    }
    

    public LightingManager getLightingManager() {
        return lightingManager;
    }



}
