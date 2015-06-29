/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.sky;

/**
 * Cloud, simple model
 */

public class CloudUnit {

    public int type = 0;
    public float sizeX = 0, sizeY = 0;
    public float posX = 0, posY = 0;
    public float speedX = 0, speedY = 0;
    public float scale = 1;
    public float rotation = 0;

    // Color modulation
    public float Red = 1, Green = 1, Blue = 1;

    public void setSize(float px, float py) {
        sizeX = px;
        sizeY = py;
    }

    public void setColor(float r, float g, float b) {
        Red = r;
        Green = g;
        Blue = b;
    }

    public void setScale(float ps) {
        scale = ps;
    }

    public float getScale() {
        return scale;
    }


    public void setRotation(float pr) {
        rotation = pr;
    }

    public void setMoveSpeed(float px, float py) {
        speedX = px;
        speedY = py;
    }

    public void setPosition(float px, float py) {
        posX = px;
        posY = py;
    }

    public void update(float px1, float px2, float py1, float py2) {

        if ((posX + sizeX > px2) || (posX < px1)) {
            speedX *= -1.0f;
        }
        if ((posY + sizeY > py2) || (posY < py1)) {
            speedY *= -1.0f;
        }

        posX += speedX;
        posY += speedY;
    }

}
