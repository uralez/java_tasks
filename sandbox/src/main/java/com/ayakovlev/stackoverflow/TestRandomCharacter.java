package com.ayakovlev.stackoverflow;

import java.util.Random;

public class TestRandomCharacter {
    public static void main(String[] args) {
        final int NUMBER_OF_CHARS = 175;
        final int CHARS_PER_LINE = 25;

        for (int i = 0; i < NUMBER_OF_CHARS; i++) {
            char ch = getRandomLowerCaseLetter();
//            char ch = RandomCharacter.getRandomLowerCaseLetter();
            if ((i + 1) % CHARS_PER_LINE == 0)
                System.out.println(ch);

            else
                System.out.print(ch);
        }
    }

    private static char getRandomLowerCaseLetter() {
        Random r = new Random();
        char c = (char)(r.nextInt(256));
//        char c = (char)(r.nextInt(26) + 'a');
        return c;
    }
}
