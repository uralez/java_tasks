package com.ayakovlev.leetcode;

import java.util.Arrays;
import java.util.Comparator;

/*
The string "PAYPALISHIRING" is written in a zigzag pattern on a given number of rows like this: (you may want to display this pattern in a fixed font for better legibility)

P   A   H   N
A P L S I I G
Y   I   R

---

Input: s = "PAYPALISHIRING", numRows = 4
Output: "PINALSIGYAHRPI"
Explanation:
P     I    N
A   L S  I G
Y A   H R
P     I

---

numRows - вниз, потом (numRows - 2) - по диагонали
=>
Один цикл забирает (2*numRows - 2) букв,
По высоте занимает numRows строк,
И по ширине 1+(numRows-2) = (numRows - 1) столбцов.
* */
public class ZigzagConversion6 {
    public String convert(String s, int numRows){
        if(numRows <= 1 || s == null || s.length() <= numRows) return s;
        // Calculate the size of one cicle - Рассчитвываем размер одного цикла:
        int C = 2*numRows -2;
        int l = s.length();
        // Form string-answer - Формируем строку-ответ:
        StringBuilder sb = new StringBuilder();
        // Iterate over chunks and take first symbols in them - Идём по кускам и берём первые символы в них:
        // Делаем итерацию по рядам. Для первого и последнего ряда берём по одному символу в куске, для всех остальных - по два.
        // Iterate over rows. For the first and the last row take by one symbol in the chunk, for all other - by two.
        for(int row = 0; row < numRows; row++) {
            for (int chunkNumber = 0; chunkNumber < (int)Math.ceil((double)l / C); chunkNumber++) {
                if(row == 0 || row == numRows-1) {
                    append(chunkNumber*C +      row, s, sb);
                }else{
                    append(chunkNumber*C +      row, s, sb);
                    append(chunkNumber*C + C -  row, s, sb);
                }
            }
        }
        return sb.toString();
    }
    private void append(int idx, String s, StringBuilder sb){
        if(idx < s.length()) {
            char c1 = s.charAt(idx);
            sb.append(c1);
        }
    }

    private String[] splitString(String s, int c) {
        int l = s.length();
        int n = (int)Math.ceil((double)l / c);
        String[] chunks = new String[n];
        for(int i = 0; i*c < l; i += 1){
            int start = i*c;
            int end = Math.min(start + c, l);
            String chunk = s.substring(start, end);
            chunks[i] = chunk;
        }
        return chunks;
    }

    public String convert_2(String s, int numRows){
        if(numRows <= 1 || s == null || s.length() <= numRows) return s;
        int len = s.length();
        // На каждый символ заводим трёхмерный массив: [символ, x, y]
        int coord[][] = new int[len][3];
        boolean mode = false; // режим: false - идём вниз, true - идём вверх по диагонали
        int x = 0; // номер текущей строки
        int y = 0; // номер текущего столбца
        for(int i = 0; i < len; i++){
            char c = s.charAt(i);
            coord[i][0] = c;
            coord[i][1] = x;
            coord[i][2] = y;
            if(mode == false) { // если до этого мы шли вниз
                if(y == numRows - 1){ // Если мы достигли низа
                    mode = true;
                    x += 1;
                    y -= 1;
                }else{
                    x = x;
                    y += 1;
                }
            }else{// если до этого мы шли вверх
                if(y == 0){ // Если мы достигли верха
                    mode = false;
                    x = x;
                    y += 1;
                }else{
                    x += 1;
                    y -= 1;
                }
            }
        }
        Arrays.sort(coord, new Comparator<int[]>(){
            @Override
            public int compare(int[] o1, int[] o2) {
                int x1 = o1[1];
                int x2 = o2[1];
                int y1 = o1[2];
                int y2 = o2[2];
                if(y1 < y2) return -1;
                else if(y1 > y2) return 1;
                else{
                    if(x1 < x2) return -1;
                    else if(x1 > x2) return 1;
                    else return 0;
                }
            }
        });
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < len; i++){
            sb.append((char)coord[i][0]);
        }
        return sb.toString();
    }
    public String convert_0(String s, int numRows) {
        if(numRows <= 1) return s;
        // Заводим двумерный массив:
        int n = (int)Math.ceil((double)s.length() / (2*numRows - 2)); // количество зигзагов
//        System.out.println("количество зигзагов: " + n);
        Character[][] field = new Character[n*(numRows-1)][numRows];
        boolean mode = false; // режим: false - идём вниз, true - идём вверх по диагонали
        int x = 0; // номер текущей строки
        int y = 0; // номер текущего столбца
        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            field[x][y] = c;
            if(mode == false) { // если до этого мы шли вниз
                if(y == numRows - 1){ // Если мы достигли низа
                    mode = true;
                    x += 1;
                    y -= 1;
                }else{
                    x = x;
                    y += 1;
                }
            }else{// если до этого мы шли вверх
                if(y == 0){ // Если мы достигли верха
                    mode = false;
                    x = x;
                    y += 1;
                }else{
                    x += 1;
                    y -= 1;
                }
            }
        }

        printField(field);
        String out = field2String(field);
        return out;
    }

    private String field2String(Character[][] field) {
        StringBuilder sb = new StringBuilder();
        int columns = field[0].length;
        int rows = field.length;

        for (int j = 0; j < columns; j++) {
            for (int i = 0; i < rows; i++) {
                Character c = field[i][j];
                if(c != null)sb.append(c);
            }
        }
        return sb.toString();
    }

    private void printField(Character[][] field) {
        for(Character[] row : field){
            System.out.println();
            for(Character c : row){
                if(c == null){
                    System.out.print("  ");
                }else{
                    System.out.print(c + " ");
                }
            }
        }
    }
}
