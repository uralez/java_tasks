package com.ayakovlev.designpatterns.creational.abstractfactory;

/*
* 5. Конкретные фабрики
* */
public class WindowFactory implements GUIFactory{
    @Override
    public Button createButton() {
        return new WinButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new WinCheckbox();
    }
}
