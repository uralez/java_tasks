package com.ayakovlev.leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LetterCombinationsOfAPhoneNumber_17 {
    private static final char[][] dictChars = {
            {},
            {},
            {'a','b','c'},
            {'d','e','f'},
            {'g','h','i'},
            {'j','k','l'},
            {'m','n','o'},
            {'p','q','r','s'},
            {'t','u','v'},
            {'w','x','y','z'}
    };
    public List<String> letterCombinations(String digits) {
        if(digits == null || digits.isEmpty()){
            return Collections.emptyList();
        }
        // Каждая результирующая строка будет этого размера:
        int len = digits.length();
        // Каждая строка будет формироваться в этом буфере путём заполнения или замены i-той позиции.
        char[] buffer = new char[len];
        // Список итоговых строк.
        // Посчитаем итоговую ёмкость:
        int capacity = 1;
        for(char c : digits.toCharArray()){
            capacity *= dictChars[c - '0'].length;
        }
        List<String> results = new ArrayList<>(capacity);
//        // Задаём начальную ёмкость списку, предполагая, что каждая цифра соответствует трём буквам (это первоначальная оптимизация, а не точное значение)
//        List<String> results = new ArrayList<String>((int)Math.pow(3, len));
        // Основная рекурсивная рабочая процедура
        backtracking(0, digits, buffer, results);
        return results;
    }

    /**
     * метод с возвратом.
     *
     * Объект buffer будет создан ровно один раз, на каждом вызове backtracking() будет заполняться/обновляться ровно один символ в этом буфере
     * и мы действуем в предположении, что если текущий символ не последний (pos < digits.length()) , то мы считаем, что справа от только что
     * заменённого символа находится "мусор", но мы этим не смущаемся, а продолжает рекурсивно вызывать наш метод пока не дойдём до конца буфера
     * и поставим все символы.
     *
     * @param pos текущая позиция в буфере/строке ввода
     * @param digits строка ввода
     * @param buffer многократно обновляемый буфер
     * @param results список строк-результатов
     */
    private void backtracking(int pos, String digits, char[] buffer, List<String> results) {
        // Если была достигнута последняя цифра и мы вышли за пределы, то мы говорим, что мы полностью сформировали нужный буфер - мы выгружаем его в результирующий список и выходим из метода:
        if(pos == digits.length()){
            results.add(new String(buffer));
            return;
        }
        int digit = digits.charAt(pos) - '0';
        char[] letters = dictChars[digit];
        for(char c : letters){
            buffer[pos] = c;
            backtracking(pos+1, digits, buffer, results);
        }
    }

    /*********************************************************/
    public List<String> letterCombinations_ex(String digits) {
        if (digits == null || digits.isEmpty()) {
            return Collections.emptyList();
        }
        int len = digits.length();
        List<String> result = new ArrayList<>();
        char[] buffer = new char[len];
        backtrack(0, digits, buffer, result);
        return result;
    }

    private void backtrack(int pos, String digits, char[] buffer, List<String> result) {
        if (pos == digits.length()) {
            // скопировать буфер в строку
            result.add(new String(buffer));
            return;
        }
        int digit = digits.charAt(pos) - '0';
        char[] letters = dictChars[digit];
        for (char c : letters) {
            buffer[pos] = c;
            backtrack(pos + 1, digits, buffer, result);
        }
    }

    /*********************************************************************************/
    /*********************************************************************************/
    /*********************************************************************************/
    /*
    Сколько строк будет в итоговом массиве? - мы должны пройтись по каждому сомволу в digits и умножить cnt на chars[digit[i]]
    ---
    1. Обеспечить правильную сортировку через перестановку циклов
    2. Перейти от конкатенации строк к StringBuilder
    3. Попробовать убрать лямбда-выражения и stream-ы и проверить, изменилась ли скорость выполнения
    * */
    public List<String> letterCombinations_8(String digits) {
        if (digits == null || digits.isEmpty()) {
            return Collections.emptyList();
        }
        char[][] field = new char[1][0];
        field = recur(0, digits, field);
        return convertFieldToList(field);
    }

    private List<String> convertFieldToList(char[][] field) {
        List<String> list = new ArrayList<String>();
        for(int i = 0; i < field.length; i++){
            list.add(new String(field[i]));
        }
        return list;
    }

    char[][] recur(int pos, String digits, char[][] field/*List<String> list*/){
        char nChar = digits.charAt(pos);
        int n = nChar - '0'; // 2 ... 9
        char[] letters = dictChars[n];
        char[][] newField = new char[field.length * letters.length][pos+1];
        int cntr = 0;
        for(char[] row : field){
            for (char c : letters) {
                // копируем всё из row в начало row2
                System.arraycopy(row, 0, newField[cntr], 0, row.length);
                // на последнюю позицию помещаем 'q'
                newField[cntr][row.length] = c;
                cntr++;
            }
        }
        if(pos+1 < digits.length()) {
            return recur(pos + 1, digits, newField);
        }else{
            return newField;
        }
    }
    /****************************************************************************************************/
    /****************************************************************************************************/
    /****************************************************************************************************/
    public List<String> letterCombinations_222(String digits) {
        if (digits == null || digits.isEmpty()) {
            return Collections.emptyList();
        }
        // Заводим массив итоговых строк:
        List<String> list = new ArrayList<>();
        list.add("");
        // Берём первый символ из digits и по нему множим строки из list и добавляем один символ из chars[n]
        for(int iChar = 0; iChar < digits.length(); iChar++) {
            char nChar = digits.charAt(iChar);
            int n = nChar - '0'; // 2 ... 9
            char[] letters = dictChars[n];
            List<String> newList = new ArrayList<String>();
            for (String s : list) {
                for (char c : letters) {
                    newList.add(s + c);
                }
            }
            list = newList;
        }
        return list;
    }

    private List<String> rec(int iChar, String digits, List<String> list){
        char nChar = digits.charAt(iChar);
        int n = nChar - '0'; // 2 ... 9
        char[] letters = dictChars[n];
        List<String> newList = new ArrayList<String>();
        for (String s : list) {
            for (char c : letters) {
                newList.add(s + c);
            }
        }
        return newList;
    }

    public List<String> letterCombinations_temp(String digits) {
        // Заводим массив итоговых строк:
        List<String> list = new ArrayList<>();
        list.add("");
        // Берём первый символ из digits и по нему множим строки из list и добавляем один символ из chars[n]
        char nChar = digits.charAt(0);
        int n = nChar - '0'; // 2 ... 9
        char[] letters = dictChars[n];
        List<String> newList = new ArrayList<String>();
        for (String s : list) {
            for (char c : letters) {
                newList.add(s + c);
            }
        }
        list = newList;
        return list;
    }

    //
    char[][] recursion(String digits, int position) {
        char[][] arr2d = null;
        char nChar = digits.charAt(position);
        int n = nChar - '0'; // 2 ... 9
        char[] letters = dictChars[n];
        // Если digits состоит из одной цифры, то мы должны вернуть массив строк из одного символа
        if (position == 0) {
            arr2d = new char[letters.length][1];
            for (int i = 0; i < letters.length; i++) {
                arr2d[i][0] = letters[i];
            }
        } else {
            // У нас есть 2d-массив символов, надо его умножить на все символы letters
            char[][] arr2dNew = new char[arr2d.length][position + 1];
        }
        return arr2d;
    }

    public List<String> letterCombinations_0(String digits) {
        if (digits == null || digits.isEmpty()) {
            return Collections.emptyList();
//            return toStringList(list);
        }

        List<String> list = new ArrayList<String>(digits.length());
        list.add("");
        for (int i = 0; i < digits.length(); i++) {
            char c = digits.charAt(i);
            int n = c - '0';
            List<String> newList = new ArrayList<String>(list.size() * dictChars[n].length);
//            final List<StringBuilder> newList = new ArrayList<StringBuilder>(list.size()*chars[n].length());
            for (String s : list) {
                for (int j = 0; j < dictChars[n].length; j++) {
                    newList.add(s + dictChars[n][j]);
                }

//                chars[n].codePoints().forEach(a -> {
//                    newList.add(new StringBuilder(sb.toString()).append((char)a));
//                });
            }
            list = newList;
        }
//        Collections.sort(list);
//        list.removeIf(s -> s.isEmpty());
        return list;
//        return toStringList(list);
    }

    private List<String> toStringList(List<StringBuilder> list) {
        return list.stream().map(sb -> sb.toString()).collect(Collectors.toList());
    }
}