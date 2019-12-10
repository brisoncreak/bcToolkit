package com.bcToolkit.test;

import com.bcToolkit.algorithm.*;
import com.bcToolkit.tools.RandomGetter;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        //int[] p = {30, 35, 15, 5, 10, 20, 25};
        //int[] p = {10, 20, 50, 1, 100};
        //int[] p = {1,2,3,400,5,6};


        int[] p = RandomGetter.getRandomArray(5, 1, 100);

        int n=p.length-1;
        int[][] m = new int[n][n];
        int[][] s = new int[n][n];
        int res = MatrixChainMulOrder.getOrder(p, m, s);
        System.out.println("动态规划最少乘法次数："+res);

        //MatrixChainMulOrder.printOrder(s, 0,n-1);
        int res0 = MatrixChainMulOrder.getOrderGreedy(p);
        System.out.println("贪心法最少乘法次数："+res0);



    }
}

