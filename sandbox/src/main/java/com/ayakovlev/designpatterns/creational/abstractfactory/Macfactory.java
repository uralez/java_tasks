package com.ayakovlev.designpatterns.creational.abstractfactory;

/*
* 5. Конкретные фабрики
* */
public class Macfactory implements GUIFactory{
    @Override
    public Button createButton() {
        return new MacButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new MacCheckbox();
    }
}
