package com.ayakovlev.designpatterns.structural.adapter;

public class Main {
    public static void main(String[] args){
        LegacySpeaker legacySpeaker = new LegacySpeaker();
        // Подключаем адаптер
        SoundPlayer soundPlayer = new SpeakerAdapter(legacySpeaker);

        // теперь бизнес-логика обращается через единый интефейс:
        soundPlayer.playSound();
    }
}
