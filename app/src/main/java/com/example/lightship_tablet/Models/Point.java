package com.example.lightship_tablet.Models;

import android.graphics.PointF;

public class Point {
    public PointF point;
    public String title;

    // constructor
    public Point(PointF point, String title) {
        this.point = point;
        this.title = title;
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
}
