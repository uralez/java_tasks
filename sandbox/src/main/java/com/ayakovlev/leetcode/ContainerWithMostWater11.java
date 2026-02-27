package com.ayakovlev.leetcode;

public class ContainerWithMostWater11 {
    public int maxArea(int[] height) {
        int len = height.length;
        int l = 0, r = len - 1;
        int h = Math.min(height[l], height[r]);
        int S = (r - l) * h;
        while(l < r){
            // примеряем новую потенциальную площадь
            h = Math.min(height[l], height[r]);
            int s = (r - l) * h;
            if(s > S){
                S = s;
            }
            // выбираем, какой поинтер двигать
            if(height[l] < height[r]){
                l += 1; // двигаемся направо
            }else{
                r -= 1; // двигаемся налево
            }
        }

        return S;
    }
}
