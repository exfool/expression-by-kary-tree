package com.company;

public class Main {


    public static void main(String[] args) throws Exception {
        // Test
        // can use: +, -, *, /, (, ), ^.

        System.out.println(Expression.compute("20 + 15 - 10 * 29"));
        System.out.println(Expression.compute("4 ^ 2 * 3 - 3 + 8/4/(1+1)"));
        System.out.println(Expression.compute("(1 + 2)*(3 + 4)"));
        System.out.println(Expression.compute("(2 + 2) * 2"));
        System.out.println(Expression.compute("2 + 2 * 2"));
        System.out.println(Expression.compute("4^2"));
        System.out.println(Expression.compute("(()(((()(1)())))())")); //lol



    }

}
