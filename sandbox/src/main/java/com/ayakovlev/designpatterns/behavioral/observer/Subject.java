package com.ayakovlev.designpatterns.behavioral.observer;

/*
* 2. Интерфейс издателя (тот, за кем наблюдают)
* */
public interface Subject {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notufyObservers();
}
