package com.ayakovlev.designpatterns.behavioral.strategy;

/*
* Шаг 2. Конкретные стратегии
* */
public class PaypalPayment implements PaymentStrategy{
    @Override
    public void pay(int amount) {
        System.out.println("Оплата через Paypal: " + amount);
    }
}
