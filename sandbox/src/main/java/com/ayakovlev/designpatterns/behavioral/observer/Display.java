package com.ayakovlev.designpatterns.behavioral.observer;

/*
* 4. Конкретные наблюдатели
*
* */
public class Display implements Observer{
    private String name;

    public Display(String name){
        this.name = name;
    }

    @Override
    public void update(int newTemperature) {
        System.out.println(name + " got update: " + newTemperature + "°С");
    }
}
