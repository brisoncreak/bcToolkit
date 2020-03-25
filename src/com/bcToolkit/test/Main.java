package com.bcToolkit.test;

import com.bcToolkit.algorithm.*;
import com.bcToolkit.tools.IPconverter;
import com.bcToolkit.tools.RandomGetter;

import java.util.Random;

public class Main {

    public static void main(String[] args) {

        String res = IPconverter.IP2BinStr("192.168.0.1");

        System.out.println(res);

    }
}

