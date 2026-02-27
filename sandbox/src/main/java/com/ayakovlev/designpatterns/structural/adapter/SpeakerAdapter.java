package com.ayakovlev.designpatterns.structural.adapter;

/*
* 3. Адаптер, который приводит LegacySpeaker к интерфейсу SoundPlayer
* */
public class SpeakerAdapter implements SoundPlayer{
    private final LegacySpeaker legacySpeaker;
    public SpeakerAdapter(LegacySpeaker legacySpeaker){
        this.legacySpeaker = legacySpeaker;
    }
    // Адаптация вызова
    @Override
    public void playSound() {
        legacySpeaker.makeNoise();
    }
}
