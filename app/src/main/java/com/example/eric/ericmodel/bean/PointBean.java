package com.example.eric.ericmodel.bean;

/**
 * Created by Eric on 2017/9/26.
 */

public class PointBean {
    private float x;
    private float y;
    private int color;

    public float getX() {
        return x;
    }
    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public PointBean(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
