package com.ayakovlev.designpatterns.creational.abstractfactory;

public class WinCheckbox implements Checkbox{
    @Override
    public void draw() {
        System.out.println("Drawing a Windows checkbox");
    }
}
