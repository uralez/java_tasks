package com.ayakovlev.thread;

/**
 * https://www.w3resource.com/java-exercises/thread/index.php
 * Write a Java program to create a basic Java thread that prints " Hello, World!" when executed.
 */
public class ThreadExample1HelloWorld extends Thread{
    @Override
    public void run(){
        System.out.println("Hello, World!");
    }

    public static void main(String[] args){
        ThreadExample1HelloWorld thread = new ThreadExample1HelloWorld();
        thread.start();
    }
}
