package com.ayakovlev.sandbox;

/*
* У меня есть комнатный термометр, который показывает температуру в цельсиях и фаренгейтах.
* Но, к сожалению, разряд единиц не работает. То есть я могу видеть температуру так: 1_,7 C и 6_,6 F.
* Это программа на джаве, которая на основе этих данных восстановит точную температуру.
*
* */
public class KitchenTemperature {

    public static void main(String [] args){
        String data = "1066"; // =>
//        String data = "1862"; // => Temperature is 16,80 °C or 62,24 °F
//        String data = "1169"; // =>
//        String data = "1567"; // => Temperature is 16,50 °C or 61,70 °F
//        String data = "1760"; // => Temperature is 16,70 °C or 62,06 °F
//        String data = "1964"; // => Temperature is 17,20 °C or 62,96 °F
//        String data = "1269"; // => Temperature is 17,20 °C or 62,96 °F
//        String data = "1361"; // => Temperature is 17,30 °C or 63,14 °F
//        String data = "1565"; // => Temperature is 17,50 °C or 63,50 °F
//        String data = "1860"; // =>
//        String data = "1064"; // =>
//        String data = "1563"; // => Temperature is 18,50 °C or 65,30 °F
//        String data = "1165"; // => Temperature is 18,10 °C or 64,58 °F
//        String data = "1267"; // => Temperature is 18,20 °C or 64,76 °F
//        String data = "1369"; // => Temperature is 18,30 °C or 64,94 °F
//        String data = "2966"; // => Temperature is 23,60 °C or 74,48 °F
//        String data = "2674"; // => Temperature is 23,60 °C or 74,48 °F
//        String data = "2575"; // => Temperature is 22,50 °C or 72,50 °F
//        String data = "2177"; // => Temperature is 22,10 °C or 71,78 °F
//        String data = "2670"; // => ??? Temperature is 25,60 °C or 78,08 °F
//        String data = "2872"; // => Temperature is 21,80 °C or 71,24 °F
//        String data = "2271"; // => Temperature is 21,20 °C or 70,16 °F
                                //Temperature is 26,20 °C or 79,16 °F
//        String data = "1766"; // => Temperature is 18,70 °C or 65,66 °F
        int cd = data.charAt(0) - '0'; // цельсий, десятки
        int cf = data.charAt(1) - '0'; // цельсий, дробная часть
        int fd = data.charAt(2) - '0'; // фаренгейт, десятки
        int ff = data.charAt(3) - '0'; // фаренгейт, дробная часть

        double celsiusDecimals = (double)cf/10; // Известная дробная часть
        double fahrenheitdecimals = (double)ff/10;
//        System.out.println("celsiusDecimals: " + celsiusDecimals);
//        System.out.println("fahrenheitdecimals: " + fahrenheitdecimals);

//        for (int cOne = 8; cOne < 9; cOne++){
        for (int cOne = 0; cOne < 10; cOne++){
            double c = cd*10 + cOne + celsiusDecimals;
            double f = c * 9.0 / 5.0 + 32.0;
//            System.out.println("c: " + c);
//            System.out.println("f: " + f);

            // Проверяем, что f ~ 6Y.6
            int fTens = (int)(f / 10) % 10;
            int fOnes = (int)f % 10;
            double fFrac = Math.floor((f + 0.01 - (int)f) * 10 ) / 10.0; // Округлённая дробная часть
//            System.out.println("fTens: " + fTens);
//            System.out.println("fOnes: " + fOnes);
//            System.out.println("fFrac: " + fFrac);
//            System.out.println("fahrenheitdecimals: " + fahrenheitdecimals);

            double eps = 0.1;
//            System.out.println("fTens == fd: " + (fTens == fd));
//            System.out.println("fFrac - fahrenheitdecimals: " + (fFrac - fahrenheitdecimals));
//            System.out.println("Math.abs(fFrac - fahrenheitdecimals) < eps: " + (Math.abs(fFrac - fahrenheitdecimals) < eps));
            if(fTens == fd && Math.abs(fFrac - fahrenheitdecimals) < eps){
                System.out.printf("Temperature is %.2f °C or %.2f °F%n", c, f);
            }
        }
    }
}
