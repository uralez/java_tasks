package com.ayakovlev.sandbox;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
Пользователь вводит любую строку, а программа должна вытащить из неё число.
 */
public class ExtractNumberFromString {
    static Integer extractFirstIntegerFromString(String in){
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(in);
        if(matcher.find()){
            String numberStr = matcher.group();
            int number = Integer.parseInt(numberStr);
            return number;
        }else{
            return null;
        }
    }

    static Long extractAllIntegersFromString(String in){
        String numberStr = in.replaceAll("\\D", "");
        Long res = Long.parseLong(numberStr);
        return res;
    }

    static Double extractDoubleFromString(String in){
        String numberStr = in.replaceAll("[^0-9.,]", "");
//        System.out.println("1. numberStr: " + numberStr);
        Pattern pattern = Pattern.compile("[.,]");
        Matcher matcher = pattern.matcher(numberStr);
        if(matcher.find()){
            // Получаем индекс первого десятичного разделителя:
            int indx = matcher.start();
            // Удаляем все десятичные разделители:
            numberStr = numberStr.replaceAll("[.,]", "");
//            System.out.println("2. numberStr: " + numberStr);
            // Вставляем на месте первого десятичного разделителя точку:
            StringBuilder sb = new StringBuilder(numberStr);
            sb.insert(indx, '.');
            numberStr = sb.toString();
//            System.out.println("3. numberStr: " + numberStr);
        }
        Double res = Double.parseDouble(numberStr);
        return res;
    }
}
