package com.ayakovlev.designpatterns.structural.bridge;

public class Circle extends Shape{
    private double x, y, radius;

    public Circle(double x, double y, double radius, DrawingAPI drawingAPI){
        super(drawingAPI);
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    @Override
    public void draw() {
        drawingAPI.drawCircle(x, y, radius);
    }
}
