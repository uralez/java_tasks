package com.ayakovlev.sandbox;

import java.util.*;
import java.util.function.Function;

public class CheckCode {
    public static void main(String [] args){
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(1, "One");
        map.put(2, "Two");
        map.put(3, "Three");
        System.out.println("map.size(): " + map.size());
        Iterator iter = map.keySet().iterator();
        while(iter.hasNext()){
            iter.next();
            iter.remove();
            System.out.println("map.size(): " + map.size());
        }
        System.out.println("map.size(): " + map.size());
        if(1<2)return;

        Optional<String> op = Optional.of("hello");
        if(1<2)return;
        Double d1 = Double.NaN;
        Double d2 = Double.NaN;
        boolean bd1 = d1 == d2;
        System.out.println("bd1: " + bd1);
        if(1<2)return;

        Comparator<Person> cmp1 = Comparator.comparing(p -> p.name);
        Comparator<Person> cmp2 = new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                if(o1 == null && o2 == null) return 0;
                if(o1 == null) return -1;
                if(o2 == null) return 1;
                return o1.name.compareTo(o2.name);
            }
        };
        Comparator<Person> cmp3 = Comparator.comparing(new Function<Person, String>() {
            @Override
            public String apply(Person person) {
                return person.name;
            }
        });
        if(!(cmp1 instanceof Cloneable)) return;
        if(1<2)return;

        Person p1 = new Person("Ivan");
        Person p2 = new Person("Ivan");

        boolean b1 = p1.equals(p2); // true
        System.out.println("b1: " + b1);
        int h1 = p1.hashCode();
        int h2 = p2.hashCode(); // ❌ нарушение контракта
        System.out.println("h1: " + h1);
        System.out.println("h2: " + h2);

        Set<Person> set = new HashSet<>();
        set.add(p1);
        boolean res = set.contains(p2); // false 😱
        System.out.println("res: " + res);
        if(1<2)return;
        String a = "Ivan";
        String b = new String("Ivan");
        res = Objects.equals(a, b);
        h1 = a.hashCode();
        h2 = b.hashCode();
        System.out.println("h1: " + h1);
        System.out.println("h2: " + h2);
        if(1<2)return;
//        int n = 125;
        int[] NN = new int[]{45, 49, 50, 51, 99, 100, 101, 125};
        int PORTION = 50;
        for(int n : NN){
            for(int portion = 0; portion < (double) n/PORTION; portion++){
                System.out.println(n + " :: portion " + portion);
            }
            System.out.println("---" );
        }
        if(1<2)return;
        List<String> list = Arrays.asList("A", "B", "C");
        System.out.println("1: " + list);
        list.set(0, "X");
        System.out.println("2: " + list);
        list.add("D");
        System.out.println("3: " + list);
    }
}

class Person {
    String name;

    public Person(String ivan) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Person)) return false;
        return Objects.equals(name, ((Person) o).name);
    }

    // hashCode НЕ переопределён ❌
}