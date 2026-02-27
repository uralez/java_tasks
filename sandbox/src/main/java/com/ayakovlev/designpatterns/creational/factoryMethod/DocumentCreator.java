package com.ayakovlev.designpatterns.creational.factoryMethod;

/*
* 3. Базовая фабрика (создает документ через Factory Method)
* */
public abstract class DocumentCreator {
    // factory Method
    public abstract Document createDocument();

    public void openDocument(){
        Document doc = createDocument();
        doc.open();
    }
}
