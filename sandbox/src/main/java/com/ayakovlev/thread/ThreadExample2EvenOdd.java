package com.ayakovlev.thread;

/**
 * https://www.w3resource.com/java-exercises/thread/index.php
 * 2. Write a Java program that creates two threads to find and print even and odd numbers from 1 to 20.
 *
 * Два потока - чётный и нечётный - попеременно печатают каждый своё число
 * и затем передают управление своему собрату.
 * Two threads - Even and Odd - alternately each prints their own number
 * and then pass control to their fellow.
 */
public class ThreadExample2EvenOdd {
    private static int MAX_NUMBER = 20;
    // object by which the threads will be synchronized
    private static Object lock = new Object();
    private static boolean isEvenTurn = true;

    public static void main(String[] args){
        Thread evenThread = new Thread(() ->{
            // class Thread has method run(), the body of which we implement here
            for(int i = 0; i <= MAX_NUMBER; i += 2){
                synchronized(lock){
                    // wait until other thread works
                    // we wrap lock.wait() in while-loop and try-catch block
                    while(!isEvenTurn){
                        try{
                            lock.wait();
                        }catch(InterruptedException ex){
                            ex.printStackTrace();
                        }// end try-catch
                    }// end while
                    System.out.println("Even number from EvenThread: " + i);
                    isEvenTurn = false;
                    lock.notify();
                }// end synchronized (lock)
            }// end for 1...MAX_NUMBER
            System.out.println("Even thread finished.");
        });

        Thread oddThread = new Thread(() -> {
            for(int i = 1; i <= MAX_NUMBER; i += 2){
                synchronized(lock){
                    // wait until other thread works
                    // we wrap lock.wait() in while-loop and try-catch block
                    while(isEvenTurn){
                        try{
                            lock.wait();
                        }catch(InterruptedException ex){
                            ex.printStackTrace();
                        }
                    }// end while
                    System.out.println("Odd number from oddThread: " + i);
                    isEvenTurn = true;
                    lock.notify();
                }// end synchronized
            }// end for 1 ... MAX_NUMBER
            System.out.println("Odd thread finished.");
        });

        evenThread.start();
        oddThread.start();
    }
}

//////

