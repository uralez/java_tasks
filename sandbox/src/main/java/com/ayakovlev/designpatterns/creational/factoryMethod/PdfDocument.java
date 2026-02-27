package com.ayakovlev.designpatterns.creational.factoryMethod;

/*
* 2. Конкретные продукты
*
* */
public class PdfDocument implements Document{
    @Override
    public void open() {
        System.out.println("Opening PDF document...");
    }
}
