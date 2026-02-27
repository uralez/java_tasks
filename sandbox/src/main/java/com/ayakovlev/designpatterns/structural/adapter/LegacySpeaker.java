package com.ayakovlev.designpatterns.structural.adapter;

/*
* 2. Сторонний несовместимый класс
*
* */
// Невозможно изменить — это библиотека
public class LegacySpeaker {
    public void makeNoise(){
        System.out.println("LegacySpeaker: BEEEP!");
    }
}
