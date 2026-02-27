package com.ayakovlev.designpatterns.creational.factoryMethod;

/*
* 2. Конкретные продукты
* */
public class WordDocument implements Document{
    @Override
    public void open() {
        System.out.println("Opening word document...");
    }
}
