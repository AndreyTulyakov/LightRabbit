package mhyhre.lightrabbit.utils;

public class Vector2f {
    float x, y;

    public Vector2f() {
        x = 0;
        y = 0;
    }

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2f getClone() {
        return new Vector2f(x, y);
    }

    public void add(Vector2f arg) {
        x += arg.x;
        y += arg.y;
    }

    public void mult(Vector2f arg) {
        x *= arg.x;
        y *= arg.y;
    }
}
