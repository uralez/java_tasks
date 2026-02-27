package com.ayakovlev.leetcode;

/**
 * 8. String to Integer (atoi)
 * https://leetcode.com/problems/string-to-integer-atoi/description/
 */
public class StringToIntegerAtoi8 {
    public int myAtoi(String s) {
        if(s == null) return 0;
        s = s.trim();
        if(s.length() == 0) return 0;
        boolean positive = true;
        int pointer = 0;
        if(s.charAt(0) == '-'){
            positive = false;
            pointer = 1;
        }else if(s.charAt(0) == '+'){
            pointer = 1;
        }
        int res = 0;
        while(pointer < s.length()){
            char c = s.charAt(pointer++);
            if('0' <= c && c <= '9'){
                int temp = res;
                res = 10*res + (c - '0');
                if(res / 10 != temp){
                    if(positive){
                        res = Integer.MAX_VALUE;
                    }else{
                        res = Integer.MIN_VALUE;
                    }
                    break;
                }
            }else{
                break;
            }
        }
        if(!positive && res > 0){
            res *= -1;
        }
        return res;
    }
}
