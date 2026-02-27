package com.ayakovlev.designpatterns.structural.bridge;

/*
* ✔️ Что демонстрирует этот пример

Абстракция (Shape) не зависит от того, как рисуется фигура.

Реализация (DrawingAPI) не зависит от того, какая фигура её использует.

Можно добавлять новые фигуры не меняя API.

Можно добавлять новые API не меняя фигур.

Это и есть паттерн Bridge — разделение и соединение через “мост”.
* */
public class Main {
    public static void main(String[] args){
        Shape circle1 = new Circle(1, 2, 3, new DrawingAPI1());
        Shape circle2 = new Circle(5, 7, 11, new DrawingAPI2());

        circle1.draw();
        circle2.draw();
    }
}
