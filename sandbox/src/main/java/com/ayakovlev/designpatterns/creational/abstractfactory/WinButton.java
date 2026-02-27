package com.ayakovlev.designpatterns.creational.abstractfactory;

/*
* 2. Конкретные продукты (Windows)
* */
public class WinButton implements Button{
    @Override
    public void paint() {
        System.out.println("Rendering a Windows button");
    }
}
