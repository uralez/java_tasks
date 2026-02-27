package com.ayakovlev.designpatterns.creational.abstractfactory;

/*
* 3. Конкретные продукты (Mac)
* */
public class MacCheckbox implements Checkbox{
    @Override
    public void draw() {
        System.out.println("Drawing a macOS checkbox");
    }
}
