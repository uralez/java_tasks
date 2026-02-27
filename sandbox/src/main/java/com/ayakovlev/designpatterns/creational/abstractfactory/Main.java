package com.ayakovlev.designpatterns.creational.abstractfactory;


/*
*6. Клиентский код
*
* ✔️ Что демонстрирует этот пример?

Паттерн Abstract Factory позволяет:

создавать группы связанных объектов (кнопка + чекбокс),

не завися от конкретных классов этих объектов,

переключать “семейства” объектов одним изменением настройки (Windows → Mac).
* */
public class Main {
    public static void main(String[] args){
        GUIFactory factory;
        String os = getOSName();
//        String os = "Windows";
        if(os == "Windows"){
            factory = new WindowFactory();
        }else{
            factory = new Macfactory();
        }
        Button button = factory.createButton();
        Checkbox checkbox = factory.createCheckbox();

        button.paint();
        checkbox.draw();
    }

    private static String getOSName() {
        String os = System.getProperty("os.name");
        System.out.println("os: " + os);
        os = os.toLowerCase();
        if(os.contains("win")){
            return "Windows";
        }else if(os.contains("mac")){
            return "macOS";
        }else if(os.contains("nix") || os.contains("nux") || os.contains("aix")){
            return "linux/Unix";
        }else{
            return "Unknown OS: " + os;
        }
    }
}
