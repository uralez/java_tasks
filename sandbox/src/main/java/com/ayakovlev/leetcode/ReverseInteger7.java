package com.ayakovlev.leetcode;

public class ReverseInteger7 {
    public int reverse(int x) {
//        System.out.println("x1: " + x);
        boolean positive = true;
        if(x < 0){
            positive = false;
            x = -x;
        }
        int out = 0;
        while(x > 0) {
            // Берём последнюю цифру
            int d = x % 10;
            if(out > Integer.MAX_VALUE / 10){
                return 0;
            }
//            int temp = out * 10;
//            System.out.println("    out: " + out);
//            if(temp / 10 != out){
//                return 0;
//            }else{
//                out = temp;
//            }
            out = out*10 + d;
//            out = out + d;
//            System.out.println("        out: " + out);
            if(out < 0) return 0;
            x = x / 10;
        }
//        System.out.println("out: " + out);
        if(!positive){
            out = -out;
        }
        return out;
    }
}
