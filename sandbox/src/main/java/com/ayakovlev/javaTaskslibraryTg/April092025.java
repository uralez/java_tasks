package com.ayakovlev.javaTaskslibraryTg;

public class April092025 {
    public static void main (String[] args){
         double d = Math.pow(10, 100) + Math.pow(10, -100);
//        double d = 10^(100) + 10^(-100);
        System.out.println(d);
        Parent obj = new Child();
        obj.print();
    }
}
class Child extends Parent {
    @Override
    public void print (){
        System.out.println("Child");
    }
}
class Parent {
    public void print (){
        System.out.println("Parent");
    }
}