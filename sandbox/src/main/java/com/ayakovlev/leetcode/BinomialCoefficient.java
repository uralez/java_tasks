package com.ayakovlev.leetcode;

public class BinomialCoefficient {
    public static void main(String [] args){
        int n = 9;
        for(int k = 0; k <= n; k++) {
            int Cnk = C(n, k);
            System.out.println("C_" + n + "_" + k + ": " + Cnk);
        }
    }

    static int C (int n, int k) {
        double res = 1;
        for (int i=1; i<=k; ++i)
            res = res * (n-k+i) / i;
        return (int) (res + 0.01);
    }
}
