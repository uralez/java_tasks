package com.ayakovlev.designpatterns.behavioral.observer;

import java.util.ArrayList;
import java.util.List;

/*
* 3. Конкретный издатель — датчик температуры
*
* */
public class TemperatureSensor implements Subject{
    private List<Observer> observers = new ArrayList<Observer>();
    private int temperature;

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notufyObservers() {
        for(Observer o : observers){
            o.update(temperature);
        }
    }

    public void setTemperature(int newTemp){
        this.temperature = newTemp;
        notufyObservers();
    }
}
