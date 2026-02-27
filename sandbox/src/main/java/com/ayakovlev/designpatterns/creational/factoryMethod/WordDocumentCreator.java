package com.ayakovlev.designpatterns.creational.factoryMethod;

/*
* 4. Конкретные фабрики
* */
public class WordDocumentCreator extends DocumentCreator{
    @Override
    public Document createDocument() {
        return new WordDocument();
    }
}
