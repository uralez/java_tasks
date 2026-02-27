package com.ayakovlev.designpatterns.behavioral.strategy;

/*
* Шаг 3. Контекст, использующий стратегию
* */
public class PaymentService {
    private PaymentStrategy paymentStrategy;
    // constructor
    PaymentService(PaymentStrategy paymentStrategy){
        this.paymentStrategy = paymentStrategy;
    }
    // Замена стратегии "на лету"
    public void setPaymentStrategy(PaymentStrategy paymentStrategy){
        this.paymentStrategy = paymentStrategy;
    }
    // Выполнить платёж с помощью текущей стратегии
    public void processPayment(int amount){
        paymentStrategy.pay(amount);
    }
}
