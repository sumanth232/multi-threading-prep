package com.interview;

import java.util.concurrent.Future;

public class Main {

    public static void main (String[] args) throws java.lang.Exception
    {
        FuturesExample eg1 = new FuturesExample(30);
        eg1.invokeAll();
    }
}

