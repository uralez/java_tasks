package com.ayakovlev.designpatterns.creational.factoryMethod;

public class Main {
    public static void main(String[] args){
        DocumentCreator creator1 = new PdfDocumentCreator();
        creator1.openDocument();  // Opening Word document...

        DocumentCreator creator2 = new WordDocumentCreator();
        creator2.openDocument(); // Opening PDF document...
    }
}
