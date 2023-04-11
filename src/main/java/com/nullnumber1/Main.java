package com.nullnumber1;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        SystemExpression systemExpression = new SystemExpression();
        System.out.println(systemExpression.systemSolve(-2, 0.0001));
    }
}
