package com.ayakovlev.designpatterns.creational.factoryMethod;

/*
* 4. Конкретные фабрики
*
* */
public class PdfDocumentCreator extends DocumentCreator{
    @Override
    public Document createDocument() {
        return new PdfDocument();
    }
}
