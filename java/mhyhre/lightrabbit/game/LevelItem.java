/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game;

import mhyhre.lightrabbit.MainActivity;
import android.util.Log;

public class LevelItem {

    private float mX, mY;
    private float mWidth, mHeight;

    private final int id;
    private final String filename;
    private final String label;
    
    public LevelItem(int id, String filename,String label) {
        this.id = id;
        this.filename = filename;
        this.label = label;
    }
    
    public String getFilename() {
        return filename;
    }

    public String getLabel() {
        return label;
    }

    public int getId() {
        return id;
    }

    public boolean isCorrect() {
        
        if(filename == null || label == null) {
            return false;
        }
        
        if(filename.isEmpty() || label.isEmpty()) {
            return false;
        }
        
        return true;
    }

    public float getLeftX() {
        return mX - mWidth / 2;
    }

    public float getDownY() {
        return mY - mHeight / 2;
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

    public boolean isCollided(float x, float y) {

        float dx = mX - mWidth / 2;
        float dy = mY - mHeight / 2;

        if (x >= dx && x <= dx + mWidth) {
            if (y >= dy && y <= dy + mHeight) {
                return true;
            }
        }
        return false;
    }

    public void print() {
        Log.i(MainActivity.DEBUG_ID, "Level:" + label + "filename:" + filename);
    }
}
