package com.ayakovlev.designpatterns.behavioral.observer;

/*
* 5. Использование
* */
public class Main {
    public static void main(String [] args){
        TemperatureSensor sensor = new TemperatureSensor();

        Observer display1 = new Display("Display 1");
        Observer display2 = new Display("Display 2");

        sensor.addObserver(display1);
        sensor.addObserver(display2);

        sensor.setTemperature(25);
        sensor.setTemperature(30);
    }
}
