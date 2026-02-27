package com.ayakovlev.designpatterns.creational.singleton;

public class SingletonUser {
    public static void main(String[] args){
        for(int i = 0; i < 10; i++){
            Thread thread = new Thread(() -> {
                Singleton singleton = Singleton.getInstance();
                System.out.println(Thread.currentThread().getName() + ": Instance hash: " + singleton.hashCode());
                singleton.doSomething();
            }, "num." + i);
            thread.start();
        }
    }
    /*
    public static void main(String[] args){
        Singleton s = Singleton.getInstance();
        s.doSomething();
    }
    */
}
