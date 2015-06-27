/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.utils;

public final class Vector2i implements Cloneable {
    
    /** static temporary vector **/
    private final static Vector2i tmp = new Vector2i();

    /** the x-component of this vector **/
    public int x;
    /** the y-component of this vector **/
    public int y;

    /**
     * Constructs a new vector at (0,0)
     */
    public Vector2i () {

    }
    
    

    @Override
    public Vector2 clone() {
        return new Vector2(x, y);
    }



    /**
     * Constructs a vector with the given components
     * @param x The x-component
     * @param y The y-component
     */
    public Vector2i (int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constructs a vector from the given vector
     * @param v The vector
     */
    public Vector2i(Vector2i v) {
        set(v);
    }

    /**
     * @return a copy of this vector
     */
    public Vector2i cpy () {
        return new Vector2i(this);
    }

    /**
     * @return The euclidian length
     */
    public float len () {
        return (int)Math.sqrt(x * x + y * y);
    }

    /**
     * @return The squared euclidian length
     */
    public float len2 () {
        return x * x + y * y;
    }

    /**
     * Sets this vector from the given vector
     * @param v The vector
     * @return This vector for chaining
     */
    public Vector2i set (Vector2i v) {
        x = v.x;
        y = v.y;
        return this;
    }

    /**
     * Sets the components of this vector
     * @param x The x-component
     * @param y The y-component
     * @return This vector for chaining
     */
    public Vector2i set (int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    /**
     * Substracts the given vector from this vector.
     * @param v The vector
     * @return This vector for chaining
     */
    public Vector2i sub (Vector2i v) {
        x -= v.x;
        y -= v.y;
        return this;
    }

    /**
     * Normalizes this vector
     * @return This vector for chaining
     */
    public Vector2i nor () {
        float len = len();
        if (len != 0) {
            x /= len;
            y /= len;
        }
        return this;
    }

    /**
     * Adds the given vector to this vector
     * @param v The vector
     * @return This vector for chaining
     */
    public Vector2i add (Vector2i v) {
        x += v.x;
        y += v.y;
        return this;
    }

    /**
     * Adds the given components to this vector
     * @param x The x-component
     * @param y The y-component
     * @return This vector for chaining
     */
    public Vector2i add (int x, int y) {
        this.x += x;
        this.y += y;
        return this;
    }

    /**
     * @param v The other vector
     * @return The dot product between this and the other vector
     */
    public int dot (Vector2 v) {
        return (int) (x * v.x + y * v.y);
    }

    /**
     * Multiplies this vector by a scalar
     * @param scalar The scalar
     * @return This vector for chaining
     */
    public Vector2i mul (int scalar) {
        x *= scalar;
        y *= scalar;
        return this;
    }

    /**
     * @param v The other vector
     * @return the distance between this and the other vector
     */
    public int dst (Vector2i v) {
        float x_d = v.x - x;
        float y_d = v.y - y;
        return (int)Math.sqrt(x_d * x_d + y_d * y_d);
    }

    /**
     * @param x The x-component of the other vector
     * @param y The y-component of the other vector
     * @return the distance between this and the other vector
     */
    public int dst (int x, int y) {
        float x_d = x - this.x;
        float y_d = y - this.y;
        return (int)Math.sqrt(x_d * x_d + y_d * y_d);
    }

    /**
     * @param v The other vector
     * @return the squared distance between this and the other vector
     */
    public int dst2 (Vector2i v) {
        int x_d = (int) (v.x - x);
        int y_d = (int) (v.y - y);
        return x_d * x_d + y_d * y_d;
    }

    public String toString () {
        return "[" + x + ":" + y + "]";
    }

    /**
     * Substracts the other vector from this vector.
     * @param x The x-component of the other vector
     * @param y The y-component of the other vector
     * @return This vector for chaining
     */
    public Vector2i sub (int x, int y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    /**
     * @return a temporary copy of this vector. Use with care as this is backed by a single static Vector2 instance. v1.tmp().add(
     *         v2.tmp() ) will not work!
     */
    public Vector2i tmp () {
        return tmp.set(this);
    }
    
    /**
     * @param v the other vector
     * @return The cross product between this and the other vector
     */
    public int cross(final Vector2i v) {
        return this.x * v.y - v.x * this.y;
    }
    /**
     * @return The manhattan length
     */
    public int lenManhattan() {
        return Math.abs(this.x) + Math.abs(this.y);
    }
}
