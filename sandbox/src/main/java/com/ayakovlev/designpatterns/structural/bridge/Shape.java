package com.ayakovlev.designpatterns.structural.bridge;

/*
* 2. Абстракция (Shape)
* */
public abstract class Shape {
    protected DrawingAPI drawingAPI;

    protected Shape(DrawingAPI drawingAPI){
        this.drawingAPI = drawingAPI;
    }

    public abstract void draw();
}
