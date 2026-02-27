package com.ayakovlev.sandbox;

import java.util.*;

public class BracketValidator {
    /**
     * Написать решение задачи: есть строка с математическим выражением. Надо написать метод, который берёт на вход строку и возвращает boolean, сообщающий, является ли строка корректной по открывающим и закрывающим скобкам. Усложнение: скобки могут быть трёх видов — круглые, квадратные, фигурные и они должны быть друг в друга корректно вложены.
     *
     * @param str входная строка с математическим выражением.
     * @return признак является ли строка корректной по открывающимся и закрывающимся скобкам.
     */
    public boolean hasValidBrackets(String str){
        if(str == null){
            return false;
        }
        if(str.isBlank()){
            return true;
        }

        Map<Character, Integer> diff = new HashMap<>();
        diff.put(')', 1);
        diff.put(']', 2);
        diff.put('}', 2);
        Deque<Character> brackets = new ArrayDeque<>();
        for(char c : str.toCharArray()){
            if(c == '(' || c == '[' || c == '{' ){
                brackets.push(c);
            }else if (c == ')' || c == ']' || c == '}'){
                if(brackets.isEmpty()) {
                    return false;
                }else{
                    Character top = brackets.pop(); // удалить из стека
                    if(c - top != diff.get(c)){
                        return false;
                    }
                }
            }
        }
        return brackets.isEmpty();
    }

    public static void main(String[] args){
        List<Character> l = List.of('(', ')', '[', ']', '{', '}');
        l.forEach(c -> {
            System.out.println(c + " = " + (int)c);
        });
        Character c1 = (char)127;
        Character c2 = (char)127;
        Character c3 = (char)128;
        Character c4 = (char)128;
        System.out.println(c1 == c2);
        System.out.println(c1.equals(c2));
        System.out.println(c3 == c4);
        System.out.println(c3.equals(c4));
    }
}
