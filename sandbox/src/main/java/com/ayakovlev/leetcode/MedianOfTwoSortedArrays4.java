package com.ayakovlev.leetcode;

import java.util.*;

public class MedianOfTwoSortedArrays4 {
    public static void main(String[] args){
        Object[] arr = new Object[2];
        int rowNum = 5;
        Integer io = Integer.valueOf(25);
        arr[0] = rowNum;
        arr[1] = io;
        int r1 = (int)arr[0];
        Integer r2 = (Integer)arr[0];
        int b1 = (int)arr[1];
        Integer b2 = (Integer)arr[1];

    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        if(m == 0){
            if(n % 2 == 0){ // Чётный размер
                return ((double)nums2[n/2-1] + nums2[n/2])/2;
            }else{// Нечётный размер
                return nums2[n/2];
            }
        }
        if(n == 0){
            if(m % 2 == 0){ // Чётный размер
                return ((double)nums1[m/2-1] + nums1[m/2])/2;
            }else{// Нечётный размер
                return nums1[m/2];
            }
        }

        int l1 = 0, l2 = 0, r1 = m-1, r2 = n-1;
        // Сколько элементов строго меньше nums1[m1] ? - (m1 + f(nums2, m1))

        try {
            // Подсчитать количество элементов в массиве, строго меньших K
            while (r1 - l1 > 1 || r2 - l2 > 1) {
                int m1 = (l1 + r1) / 2, m2 = (l2 + r2) / 2;
                // Если мы ещё не достигли финала в первом массиве:
                if(r1 - l1 > 1) {
                    // подсчитать количество элементов в объединённом массиве, полустрого меньших зажжённого элемента в первом масиве
                    int nk1 = m1 + getCountLessThanK(nums2, nums1[m1]);
                    if (nk1 < (m + n) / 2) {
                        // Сдвигаемся вправо
                        l1 = m1;
                    } else {
                        // Сдвигаемся влево
                        r1 = m1;
                    }
                }
                // Если мы ещё не достигли финала во втором массиве:
                if(r2 - l2 > 1) {
                    // подсчитать количество элементов в объединённом массиве, полустрого меньших зажжённого элемента во втором масиве
                    int nk2 = m2 + getCountLessThanK(nums1, nums2[m2]);
                    if (nk2 < (m + n) / 2) {
                        // Сдвигаемся вправо
                        l2 = m2;
                    } else {
                        // Сдвигаемся влево
                        r2 = m2;
                    }
                }
            }
            // В этом месте у нас должно быть две пары соседних индексов и две пары соседних элементов
//            System.out.println("nums1[" + l1 + "]=" + nums1[l1] + ", " + "nums1[" + r1 + "]=" + nums1[r1]);
//            System.out.println("nums2[" + l2 + "]=" + nums2[l2] + ", " + "nums2[" + r2 + "]=" + nums2[r2]);

            // для каждой точки надо получить количество элементов полустрого меньших её
            // Мы образуем ассоциированный массив из 4 элементов:
            // val1 - col1
            // val2 - col2
            // val3 - col3
            // val4 - col4
            // мы должны упорядочить его по столбцу col
            // а затем взять максимальный из столбца val, для которого col не превышает полусуммы (m+m)/2
            int nl1 = l1 + getCountLessThanK(nums2, nums1[l1]);
            int nr1 = r1 + getCountLessThanK(nums2, nums1[r1]);
            int nl2 = l2 + getCountLessThanK(nums1, nums2[l2]);
            int nr2 = r2 + getCountLessThanK(nums1, nums2[r2]);
            int[][] data = {
                    {nums1[l1], nl1},
                    {nums1[r1], nr1},
                    {nums2[l2], nl2},
                    {nums2[r2], nr2},
            };
            Arrays.sort(data, new Comparator<int[]>(){
                @Override
                public int compare(int[] o1, int[] o2) {
                    if(o1[1] < o2[1]){
                        return -1;
                    }else if(o1[1] > o2[1]){
                        return 1;
                    }else if(o1[0] < o2[0]){
                        return -1;
                    }else if(o1[0] > o2[0]){
                        return 1;
                    }else {
                        return 0;
                    }
                }
            });
            if ((m + n) % 2 == 1) {// количество элементов в обоих массивах нечётно => надо вернуть один элемент
                for(int i = 3; i >= 0; i--){
                    if(data[i][1] <= (m+n)/2){
                        return data[i][0];
                    }
                }
                throw new Exception("ДЛЯ НЕЧЁТНОГО СЛУЧАЯ НЕ НАШЛОСЬ ПРАВИЛЬНОЙ СЕРЕДИНЫ");
            } else {
                for(int i = 3; i > 0; i--){
                    if(((double)data[i][1] + data[i-1][1])/2 < (m+n)/2){
                        return ((double)data[i][0] + data[i-1][0])/2;
                    }
                }
                throw new Exception("ДЛЯ ЧЁТНОГО СЛУЧАЯ НЕ НАШЛОСЬ ПРАВИЛЬНОЙ СЕРЕДИНЫ");
            }
        }catch(Exception ex){
            ex.printStackTrace();
            return -5;
        }
    }

    /*
    Получить количество элементов массива, строго меньших K.
    * */
    public int getCountLessThanK(int[] arr, int K) throws Exception {
        if(arr == null || arr.length == 0 || K <= arr[0]){
            return 0;
        }
        int len = arr.length;
        if(K > arr[len-1]) return len;

        int l = 0, r = len-1;
        int m = (l+r)/2;
        // Мы должны найти самый правый индекс m, элемент в котором строго меньше K, а следующий элемент больше либо равен ему.
        while(l < r-1){
            m = (l+r)/2;
            int curr = arr[m];
            if(curr >= K){
                // Сдвигаемся влево
                r = m;
            }else{
                // Сдвигаемся вправо
                l = m;
            }
        }
        // Предполагаем, что в этом месте у нас есть два числа - l и r - которые либо совпадают, либо отличаются на единицу
//        System.out.println("arr[" + l + "]=" + arr[l] + ", " + "arr[" + r + "]=" + arr[r]);
        if(l == 0){
            if(arr[l] == K){
                return 0;
            }else{
                return 1;
            }
        }else { // l > 0
            if(arr[l] == K){
                return l;
            }else{
                return l+1;
            }
        }
    }


/////////////////////////////////////////
    public double findMedianSortedArrays_0(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        if(m == 0){
            if(n % 2 == 0){ // Чётный размер
                return ((double)nums2[n/2-1] + nums2[n/2])/2;
            }else{// Нечётный размер
                return nums2[n/2];
            }
        }
        if(n == 0){
            if(m % 2 == 0){ // Чётный размер
                return ((double)nums1[m/2-1] + nums1[m/2])/2;
            }else{// Нечётный размер
                return nums1[m/2];
            }
        }

        // Сливаем два массива в один
        int[] all = new int[m+n];
        int p1 = 0, p2 = 0, p = 0;
        while(p1 < m || p2 < n){
            if(p1 == m){// мы уже выбрали все элементы первого массива
                all[p] = nums2[p2];
                p2++;
            }else if(p2 == n){// мы уже выбрали все элементы второго массива
                all[p] = nums1[p1];
                p1++;
            }else if(nums1[p1] <= nums2[p2]){
                all[p] = nums1[p1];
                p1++;
            }else{
                all[p] = nums2[p2];
                p2++;
            }
            p++;
        }
        if((m+n) % 2 == 0){ // Чётный размер
            return ((double)all[(m+n)/2-1] + all[(m+n)/2])/2;
        }else{// Нечётный размер
            return all[(m+n)/2];
        }
    }


}
