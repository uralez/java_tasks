package com.ayakovlev.designpatterns.behavioral.strategy;

public class Main {
    public static void main(String[] args){
        PaymentService paymentService = new PaymentService(new CreditCardPayment());
        paymentService.processPayment(100);

        // Меняем стратегию "на лету"
        paymentService.setPaymentStrategy(new PaypalPayment());
        paymentService.processPayment(200);

        paymentService.setPaymentStrategy(new CryptoPayment());
        paymentService.processPayment(400);
    }
}
