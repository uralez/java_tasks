package com.ayakovlev.designpatterns.behavioral.strategy;

/*
Шаг 1. Интерфейс стратегии
* */
public interface PaymentStrategy {
    void pay(int amount);
}
