package com.ayakovlev.contest437;

import java.util.Arrays;

public class Contest437 {

    public static void main(String[] args){
        int[] pizzas = new int[]{1,2,3,4,5,6,7,8};
//        int[] pizzas = new int[]{1,2,3,4,5,6,7,8,5,6,7,8};
        long sum = new Contest437().maxWeight(pizzas);
        System.out.println(sum);
    }
    public long maxWeight(int[] pizzas) {
        Arrays.sort(pizzas);
        System.out.println(Arrays.toString(pizzas));
        int n = pizzas.length / 4;
        int oddCount = (n+1)/2;
        int evenCount = n/2;
        long sum = 0;
        for (int i = 0; i < oddCount; i++ ){
            sum += pizzas[4*n-1-i];
        }
        for(int i = 0; i < evenCount; i++){
            sum += pizzas[4*n-oddCount-2*(i+1)];
        }
        return sum;
    }

}
