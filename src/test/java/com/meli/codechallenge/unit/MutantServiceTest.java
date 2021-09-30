package com.meli.codechallenge.unit;

import java.util.Arrays;

public class MutantServiceTest {

    public static void main(String[] args) {
        String[] a = {"AAAA", "BBBB"};
        System.out.println(Arrays.hashCode(a));
        String[] b = {"AAAA", "BBBB"};
        System.out.println(Arrays.hashCode(b));
    }
}
