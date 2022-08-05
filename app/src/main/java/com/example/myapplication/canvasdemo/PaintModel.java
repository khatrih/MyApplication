package com.example.myapplication.canvasdemo;


import android.graphics.Path;

public class PaintModel {
    public int color;
    public int strokeWidth;
    public Path path;

    public PaintModel(int color, int strokeWidth, Path path) {
        this.color = color;
        this.strokeWidth = strokeWidth;
        this.path = path;
    }
}
