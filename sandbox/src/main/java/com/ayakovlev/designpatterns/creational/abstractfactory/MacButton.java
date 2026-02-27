package com.ayakovlev.designpatterns.creational.abstractfactory;

/*
* 3. Конкретные продукты (Mac)
* */
public class MacButton implements Button{
    @Override
    public void paint() {
        System.out.println("Rendering a macOS button");
    }
}
