package com.ayakovlev.designpatterns.creational.abstractfactory;

/*
* 4. Абстрактная фабрика
* */
interface GUIFactory {
    Button createButton();
    Checkbox createCheckbox();
}
