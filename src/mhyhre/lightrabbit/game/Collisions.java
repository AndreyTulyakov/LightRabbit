package mhyhre.lightrabbit.game;

import org.andengine.entity.sprite.Sprite;

public class Collisions {

    public static boolean spriteByCircle(Sprite spr, float x2, float y2, float radius) {

        float dx = spr.getX() - x2;
        float dy = spr.getY() - y2;
        float dist = dx * dx + dy * dy;

        if (dist <= radius * radius)
            return true;

        return false;
    }

    public static boolean sptireCircleByCircle(Sprite c1, float radius1, float x1, float y1, float radius2) {

        float dx = (x1) - (c1.getX() + c1.getWidth() / 2);
        float dy = (y1) - (c1.getY() + c1.getHeight() / 2);
        float dist = dx * dx + dy * dy;

        float radiusSum = radius1 + radius2;
        if (dist <= radiusSum * radiusSum)
            return true;

        return false;
    }

}
