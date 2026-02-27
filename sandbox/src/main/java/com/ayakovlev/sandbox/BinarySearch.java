package com.ayakovlev.sandbox;

public class BinarySearch {
    /*
    Функция ищет индекс числа A в отсортированном массиве a.

    Если массив не отсортирован в возрастающем порядке, метод выбрасывает исключение new Exception("Array is not sorted asc").
    Если A меньше минимального элемента массива, возвращается индекс -1.
    Если A больше максимального элемента массива, возвращается индекс len + 1
    Если A лежит между двумя элементами (a[k], a[k+1]), возвращается число k+0.5
    Если в массиве несколько элементов A, возвращается индекс самого левого элемента (Левее либо нет элементов, либо там элемент, строго меньший, чем A)
    Если в массиве ровно один элемент A, именно его индекс и будет возвращён.
     */
    public Number search(int[] a, int A) throws Exception {
        if (a == null || a.length == 0)return null;
        int len = a.length;
        // Check if array is sorted asc - Проверим, отсортирован ли массив по возрастанию
        for(int i = 0; i < len - 1; i++){
            if(a[i+1] < a[i]){
                throw new Exception("Array is not sorted asc");
            }
        }
        if(A < a[0]){
            return -1;
        }
        if(A == a[0]){
            return 0;
        }
        if(A > a[len-1]){
            return len + 1;
        }
        int l = 1;
        int r = len-1;
        double p;
        int counter = 0;
        while(true && counter++ < 10){
//            System.out.println("l: " + l);
//            System.out.println("r: " + r);
            p = (r+l)/2;
//            System.out.println("p: " + p);
            if(a[(int)Math.floor(p)] == A && a[(int)Math.floor(p)-1] < A){
                System.out.println("return QQQ");
                return (int)Math.floor(p);
            }
            if(a[(int)Math.ceil(p)] == A && a[(int)Math.floor(p)] < A){
                System.out.println("return WWW");
                return (int)Math.ceil(p);
            }
            if(a[(int)Math.floor(p)] < A && a[(int)Math.ceil(p)] > A){
                System.out.println("return EEE");
                return p;
            }
            if(a[l] < A && a[(int)p] >= A){
                // Move left - Двигаемся налево
                r = (int)p;
                System.out.println("Move r to the left: " + r);
                System.out.println("l is the same: " + l);
            }else{
                // Move right - Двигаемся направо
                l = (int)p;
                System.out.println("Move l to the right: " + l);
                System.out.println("r is the same: " + r);
            }
        }
        System.out.println("return FFF");
        return null;
    }
}
