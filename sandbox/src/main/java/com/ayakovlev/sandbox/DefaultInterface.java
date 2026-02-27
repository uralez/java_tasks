package com.ayakovlev.sandbox;

import java.util.function.Function;

public interface DefaultInterface {

    default boolean def(){
        Runnable r = () -> System.out.println("Hello");
        Function<String, Integer> f = s -> s.length();

        return this.equals("A");
    }
}
