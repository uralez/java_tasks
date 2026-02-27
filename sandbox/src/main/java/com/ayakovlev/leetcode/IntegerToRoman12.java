package com.ayakovlev.leetcode;

public class IntegerToRoman12 {

    public String intToRoman(int num) {
        int[]       thresholds = {1000, 900, 500, 400, 100, 90,     50,     40,     10, 9,      5,      4,      1};
        String[]    romSymbols = {"M",  "CM","D", "CD","C", "XC",   "L",    "XL",   "X","IX",   "V",    "IV",   "I"};
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < romSymbols.length; i++){
            while(num >= thresholds[i]){
                sb.append(romSymbols[i]);
                num -= thresholds[i];
            }
        }
        return sb.toString();
    }

    public String intToRoman_0(int num) {
        // Число надо разбить на единицы, десятки, сотни и тысячи:
        int[] chunks = getChunks(num);
//        System.out.println(Arrays.toString(chunks));
        int ones = chunks[0];
        int tens = chunks[1];
        int hundreds = chunks[2];
        int thousands = chunks[3];
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < thousands; i++){
            sb.append("M");
        }

        if(hundreds == 9){
            sb.append("CM");
            hundreds = 0;
        } else if(hundreds == 4){
            sb.append("CD");
            hundreds = 0;
        } else if(hundreds >= 5){
            sb.append("D");
            hundreds -= 5;
        }
        for(int i = 0; i < hundreds; i++){
            sb.append("C");
        }

        if (tens == 9){
            sb.append("XC");
            tens = 0;
        }else if(tens == 4){
            sb.append("XL");
            tens = 0;
        }else if(tens >= 5){
            sb.append("L");
            tens -= 5;
        }
        for(int i = 0; i < tens; i++){
            sb.append("X");
        }

        if(ones == 9){
            sb.append("IX");
            ones = 0;
        }else if(ones == 4){
            sb.append("IV");
            ones = 0;
        }else if(ones >= 5){
            sb.append("V");
            ones -= 5;
        }
        for (int i = 0; i < ones; i++){
            sb.append("I");
        }

        return sb.toString();
    }

    private int[] getChunks(int num) {
        int[] chunks = new int[4];
//        System.out.println("num: " + num);
        for(int i = 0; i < 4 && num > 0; i++){
            int d = num % 10;
//            System.out.println(i + ". d: " + d);
            chunks[i] = d;
            num /= 10;
        }
        return chunks;
    }
}