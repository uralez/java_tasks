package com.ayakovlev.leetcode;

import java.util.*;

public class RegularExpressionMatching10 {
    Boolean[][] memo;

    public boolean isMatch(String s, String p) {
        memo = new Boolean[s.length() + 1][p.length() + 1];
        return dynamicProgramming(0, 0, s, p);
    }

    private boolean dynamicProgramming(int i, int j, String s, String p) {
        if (memo[i][j] == null) {
            boolean ans;
            // если мы достигли конца шаблона p, то значением ans будет условие - достигли ли мы одновременно конца строки s.
            if (j == p.length()) {
                ans = (i == s.length());
            } else {
                // Рассчитываем совпадение текущего символа
                boolean first_match = (i < s.length() && (p.charAt(j) == '.' || p.charAt(j) == s.charAt(i)));
                if (j + 1 < p.length() && p.charAt(j + 1) == '*') { // Если в шаблоне имеется звёздочка
                    ans = dynamicProgramming(i, j + 2, s, p) // ноль совпадений по звёздочке - сдвигаем шаблон на два символа, а строку оставляем той же
                            || first_match && dynamicProgramming(i + 1, j, s, p);// используем одно текущее совпадение по звёздочке и эту звёздочку передаём дальше
                } else {//В шаблоне нет звёздочки, поэтому мы требуем, чтобы совпадал текущий символ и следующие подстроки строки и шаблона, сдвинутые на единицу.
                    ans = first_match && dynamicProgramming(i + 1, j + 1, s, p);
                }
            }
            memo[i][j] = ans;
        }
        return memo[i][j];
    }

    public boolean isMatch_recursion(String s, String p) {
        if (p.isEmpty()) {
            return s.isEmpty();
        }
        boolean first_match = (!s.isEmpty()) && (p.charAt(0) == s.charAt(0) || p.charAt(0) == '.');
        if (p.length() > 1 && p.charAt(1) == '*') {
            return isMatch(s, p.substring(2)) // звёздочка отвечает за ноль вхождений
                    || first_match && isMatch(s.substring(1), p);
        } else {
            return first_match && isMatch(s.substring(1), p.substring(1));
        }
    }

    public boolean isMatch_my(String s, String p) {
        if (s == null || s.isEmpty() || p == null || p.isEmpty()) {
            return false;
        }
        // Создаём набор цепочек, добавляем пустую цепочку:
        Set<Chain> chains = new TreeSet<Chain>();
        chains.add(new Chain());
        // Делим шаблон на список Элементарных шаблонов
        List<String> elementaryPatterns = getElementaryPatterns(p);
        // Для каждого Элементарного шаблона проводим сопоставление с новым единичным символом или строим цепочки, если у нас множественный элементарный шаблон
        for (int i_ep = 0; i_ep < elementaryPatterns.size(); i_ep++) {
            String ep = elementaryPatterns.get(i_ep);
//            System.out.println("i_ep:" + i_ep + ", ep:" + ep);
            Set<Chain> nextChains = new TreeSet<Chain>();

            // Для каждой цепочки проверяем, может ли она продолжиться с данным Элементарным шаблоном
            int chainCounter = 0;
            for (Chain chain : chains) {
                // Какой последний символ строки в этой цепочке? - lastLink.stringSymbolNumber
                Link lastLink = chain.getLastLink();
//                System.out.println("\tchainCounter: " + chainCounter + ", chain: " + chain + ", lastLink: " + lastLink);
                chainCounter++;
                if (chainCounter > 50) {
                    System.out.println("TOO MUCH!");
                    break;
                }
                int symbolToStartFrom = lastLink == null ? 0 : lastLink.stringSymbolNumber + 1;
                // Сколько возможных совпадений
                int n = getMatches(s.substring(symbolToStartFrom), ep);

                // Для необязательного шаблона мы должны в новый набор добавить и текущий chain, и n новых chain-ов
                // Для обязательного мы добавляем в новый набор только новые chain-s
                if (ep.length() > 1) { // Элементарный шаблон является опциональным
                    nextChains.add(chain);
                }
                for (int i = 1; i <= n; i++) {
//                    System.out.println("\t\tAdd " + i + " chain of " + n);
                    Chain newChain = new Chain(chain, i, s.substring(symbolToStartFrom), i_ep);
                    boolean res = nextChains.add(newChain);
//                    System.out.println("\t\t\tРезультат добавления: " + res);
                }
            }
            if (nextChains.size() == 0) {
                return false;
            }
            chains = nextChains;
        }
        System.out.println("Gesamt:" + chains.size() + " chains");
        // Если среди возможных цепочек есть цепочки длины строки, возвращаем true, иначе - false
        for (Chain chain : chains) {
            if (chain.links.size() == s.length()) {
                return true;
            }
        }
        return false;
    }

    /*
     * Метод возвращает количество совпадений символов в начале строки s Элементарному шаблону ep
     * */
    private int getMatches(String s, String ep) {
        char c_p = ep.charAt(0);
        if (s == null || s.isEmpty()) {
            return 0;
        }
        if (ep.length() == 1) {
            if (c_p == '.') {
                return 1;
            } else {
                if (s.charAt(0) == c_p) {
                    return 1;
                } else {
                    return 0;
                }
            }
        } else {// Двухсимвольный Элементрарный шаблон со звёздочкой
            if (c_p == '.') {
                return s.length();
            }
            int cntr = 0;
            while (cntr < s.length() && s.charAt(cntr) == c_p) {
                cntr++;
            }
            return cntr;
        }
    }

    private List<String> getElementaryPatterns(String p) {
        List<String> list = new ArrayList<String>();
        int l = 1;
        for (int i = 0; i < p.length(); ) {
            if (i + 1 < p.length() && p.charAt(i + 1) == '*') {
                l = 2;
            } else {
                l = 1;
            }
            list.add(p.substring(i, i + l));
            i = i + l;
        }
        return list;
    }

    class Link {
        char chr;// Символ строки s
        int stringSymbolNumber;// Номер символа в строке
        int i_ep;// ссылка на Элементарный шаблон

        /**
         * @param c                  - char
         * @param stringSymbolNumber - номер символа в строке
         * @param i_ep               - номер элементарного шаблона
         */
        public Link(char c, int stringSymbolNumber, int i_ep) {
            this.chr = c;
            this.stringSymbolNumber = stringSymbolNumber;
            this.i_ep = i_ep;
        }

        public String toString() {
            return stringSymbolNumber + ":" + chr + "(" + i_ep + ")";
        }

        ;
    }

    class Chain implements Comparable<Chain> {
        List<Link> links = new ArrayList<Link>();// Звенья
        String initialString = "";

        public Chain() {
        }

        public Chain(Chain oldChain, int n, String s, int i_ep) {
            // как скопировать старый список в новый?
            // Поверхностная копия списка - те же объекты, ссылки на них
            this.links = new ArrayList<>(oldChain.links);
            this.initialString = oldChain.initialString + s.substring(0, n);
            int n0 = this.links.size();
            // Добавляем n звеньев в цепочку
            for (int i = 0; i < n; i++) {
                add(new Link(s.charAt(i), n0 + i, i_ep));
            }
        }

        @Override
        public String toString() {
            if (links.size() == 0) {
                return "[-]:" + initialString;
            } else {
                StringBuilder sb = new StringBuilder("[");
                for (Link l : links) {
                    sb.append(l.chr);
                }
                sb.append("]:" + initialString); // ???
                return sb.toString();
            }
        }

        public void add(Link link) {
            links.add(link);
            initialString += link.chr;
        }

        public Link getLastLink() {
            if (links.size() > 0) {
                return links.get(links.size() - 1);
            } else {
                return null;
            }
        }

        @Override
        public int compareTo(Chain o) {
            int res = initialString.compareTo(o.initialString);
//            System.out.println("\t\t\t" + initialString + "==" + (o.initialString) + "-> " + res);
            return res;
        }
    }


    /*
     * Вводим понятие Элементраный шаблон:
     * единичные символы a-z - точное соответствие одной букве
     * символ точки . - одна любая буква
     * a*, ... , z* - любое количество данной буквы
     * .* - любое количество любых букв
     *
     * Каждому символу строки пытаемся сопоставить один или несколько Элементарных шаблонов.
     * Строка соответствует шаблону, если:
     * 1. Каждому символу строки сопоставлен хотя бы один элементарный шаблон
     * 2. Каждому обязательному символу шаблона (a-z.) соответствует ровно один символ строки
     *
     * Для Элементарных шаблонов переменной длины формируем цепочку допустимых символов от минимальной до максимальной.
     * Затем для каждого символа из этой цепочки пытаемся примерить следующий шаблон.
     * Оставляем только успешные цепочки.
     * Если успешных цепочек нет - возвращаем false.
     * Если строка и шаблон заканчиваются одновременно и есть хотя бы одна успешная цепочка - возвращаем true,
     * Иначе, если строка заканчивается раньше или позже шаблона - возвращаем false.
     * */
    public boolean isMatch_337(String s, String p) {
        if (s == null || s.isEmpty() || p == null || p.isEmpty()) {
            return false;
        }
        /* Успешная цепочка сопоставлений - это:
        a   b   c   a   a   a   b   c
        a   b   c   a*  .   .
        0   1   2   3   4   5

        a   b   c   a   a   a   b   c
        0   1   2   3   3   3   4   5
        0   1   2   3   3   4   5
        0   1   2   3   4   5

        Элемент успешной цепочки - это пара (Символ s; Номер Элементраного шаблона) = Link = (Character, Integer)
        Chain = List<Link> - успешная цепочка
        Set<Chain> - текущий набор успешных цепочек. Мы их можем и добавлять, и удалять, поэтому надо поискать вариант без ConcurrentModficationException
        */
        Set<Chain> chains = new HashSet<Chain>();
        int i_s = 0;
        // Делим шаблон на список Элементарных шаблонов
        List<String> elementaryPatterns = getElementaryPatterns(p);
        int n;
        // Для каждого Элементарного шаблона проводим сопоставление с новым единичным символом или строим цепочки, если у нас множественный элементарный шаблон
        for (int i_ep = 0; i_ep < elementaryPatterns.size(); i_ep++) {
            String ep = elementaryPatterns.get(i_ep);
            Set<Chain> nextChains = new HashSet<Chain>();
            if (chains.size() == 0) {
                // получить все соответствия символов строки с первым Элементарным шаблоном
                n = getMatches(s, ep);
                if (n == 0) {
                    // Проверяем, является ли Элементарный шаблон опциональным или обязательным
                    if (ep.length() == 1) { // Элементарный шаблон является обязательным
                        return false;
                    } else {// Элементарный шаблон является опциональным
                        continue;
                    }
                } else {
                    // Создаём n новых цепочек
                    for (int i = 0; i < n; i++) {
                        Chain chain = new Chain();
                        for (int j = 0; j <= i; j++) {
                            Link link = new Link(s.charAt(j), j, i_ep);
                            chain.add(link);
                        }
                        nextChains.add(chain);
                    }
                }
            } else {
                // Для каждой цепочки проверяем, может ли она продолжиться с данным Элементарным шаблоном
                for (Chain chain : chains) {
                    // Какой последний символ строки в этой цепочке? - lastLink.stringSymbolNumber
                    Link lastLink = chain.getLastLink();
                    // Сколько возможных совпадений
                    n = getMatches(s.substring(lastLink.stringSymbolNumber + 1), ep);

                    // Для необязательного шаблона мы должны в новый набор добавить и текущий chain, и n новых chain-ов
                    // Для обязательного мы добавляем в новый набор только новые chain-s
                    if (ep.length() > 1) { // Элементарный шаблон является опциональным
                        nextChains.add(chain);
                    }
                    for (int i = 1; i <= n; i++) {
                        Chain newChain = new Chain(chain, i, s.substring(lastLink.stringSymbolNumber + 1), i_ep);
                        nextChains.add(newChain);
                    }
                }
            }
            chains = nextChains;
        }
        // Если среди возможных цепочек есть цепочки длины строки, возвращаем true, иначе - false
        for (Chain chain : chains) {
            if (chain.links.size() == s.length()) {
                return true;
            }
        }
        return false;
    }


    /*
     * {"aab", "c*a*b", true},
     * Сегмент шаблона для сопоставления с символом строки может состоять из одного (c), двух (c*) или трёх символов.
     * То есть для сопоставления мы должны сначала поучить весь список возможных шаблонов, а только потом проводить сопоставление.
     *
     * Если в шаблоне нет спецсимволов, то буква шаблона в точности соответствует букве сравниваемой строки
     * */
    public boolean isMatch_287(String s, String p) {
        if (s == null || s.isEmpty() || p == null || p.isEmpty()) {
            return false;
        }
        int i_s = 0;
        int i_p = 0;
        int used_p = 0;
        while (i_s < s.length()) {
            char c_s = s.charAt(i_s);
            int[][] patterns = getPossiblePatterns(p, i_p, i_s);
            // Пытаемся сопоставить символ строки с символом шаблона и, если есть совпадение, понять, на каком месте шаблона мы сейчас наодимся.
            boolean occurance = false;
            for (int i = 0; i < patterns.length; i++) {
                int[] e = patterns[i];
                char cand = (char) e[0];
                System.out.print("i_s=" + i_s + " ... " + (i + 1) + "/" + patterns.length + ": (p)" + cand + " & (s)" + c_s);
                if (e[0] == '.' || e[0] == c_s) {
                    occurance = true;
                    i_p = e[1];
                    used_p = e[2];
                    System.out.println(" - true");
                    break;
                }
                System.out.println(" - false");
            }
            if (!occurance) {
                System.out.println("Проблемный символ: " + c_s);
                System.out.println("Проблемная позиция: " + i_s);
                System.out.println("Возможные паттерны: ");
                for (int[] p2 : patterns) {
                    System.out.println("\t" + (char) p2[0] + "->" + "patt.pos." + p2[1]);
                }
                return false;
            }
            i_s++;
        }
        System.out.println("used_p: " + used_p);
        System.out.println("p.substring(used_p).length(): " + p.substring(used_p).length());
        if (used_p < p.length() && p.substring(used_p).replace("*", "").length() > 0) {
            return false;
        }
        return true;
    }

    private int[][] getPossiblePatterns(String p, int i_p, int i_s) {
        // В список кладём пару - допустимый шаблонный символ и место следующего символа шаблона, если сыграет это символ шаблона
        // Нам нужна тройка - сколько символов шаблона мы использовали
        List<int[]> list = new ArrayList<int[]>();
        if (i_p < p.length()) {
            char q1 = p.charAt(i_p);
            int q2 = (int) p.charAt(i_p);
            list.add(new int[]{(int) p.charAt(i_p), i_p + 1, i_p + 1});
            int n = 0;
            while (i_p + 2 * n + 1 < p.length() && p.charAt(i_p + 2 * n + 1) == '*') {
                list.add(new int[]{(int) p.charAt(i_p + 2 * n), i_p + 2 * n, i_p + 2 * n + 2});
                if (i_p + 2 * n + 2 < p.length()) {
                    list.add(new int[]{(int) p.charAt(i_p + 2 * n + 2), i_p + 2 * n + 3, i_p + 2 * n + 3});
                }
                n++;
            }
        }
        int[][] arr = convert2Array(list);
        // надо упорядочить список по второму параметру.
        Arrays.sort(arr, new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                return Integer.compare(a[1], b[1]);
            }
        });
        return arr;
    }

    private int[][] convert2Array(List<int[]> list) {
        int[][] arr = new int[list.size()][3];
        int i = 0;
        for (int[] e : list) {
            arr[i] = e;
            i++;
        }
        return arr;
    }

}