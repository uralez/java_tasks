package com.ayakovlev.research;

public class Qqq {
    public static void main(String[] args){
        System.out.println("qqq");
        int res = myMethod();
        System.out.println("res: " + res);
    }
    public static int myMethod(){
        System.out.println("call myMethod()");
        return 5;
    }
}
