package com.example.lightship_tablet.Models;

import android.graphics.PointF;

public class Item {
    public PointF point;
    public String title;
    public float weight;

    @Override
    public String toString() {
        return title + ' ' + weight;
    }

    // constructor
    public Item(PointF point, String title, float weight) {
        this.point = point;
        this.title = title;
        this.weight = weight;
    }

    //getter setter
    public PointF getPoint() {
        return point;
    }

    public void setPoint(PointF point) {
        this.point = point;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}
