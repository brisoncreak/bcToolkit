package com.bcToolkit.algorithm;

import java.util.Arrays;
import java.util.ArrayList;

public class MatrixChainMulOrder {

    //动态规划算法求矩阵链乘顺序
    public static int getOrder(int[] p, int[][] m, int[][] s){
       //矩阵个数
       int n = p.length-1;

       //初始化为0
       for(int i=0;i<n;i++) m[i][i] = 0;

       for(int l=1;l<n;l++)
       {
           for(int i=0;i<n-l;i++)
           {
               int j = i+l;
               m[i][j] = Integer.MAX_VALUE;
               int q=0;
               for(int k=i;k<j;k++) {
                   q = m[i][k] + m[k+1][j] + p[i] * p[k+1] * p[j+1];
                   if (q < m[i][j]) {
                       m[i][j] = q;
                       s[i][j] = k;
                   }
               }
           }
       }
       return m[0][n-1];
    }
    //输出结果
    public static void printOrder(int[][] s,int i, int j){
        if(i==j) System.out.print("A"+(i+1));
        else{
            System.out.print("(");
            printOrder(s,i,s[i][j]);
            printOrder(s,s[i][j]+1,j);
            System.out.print(")");
        }
    }

    //贪心法（结果不正确）
    public static int getOrderGreedy(int[] p){

        int res = 0;
        int n = p.length;

        while(n>3) {

            //查找最大值下标
            int max_index = 1;

            for (int i = 2; i < n - 1; i++) {
                if (p[i] > p[max_index]) max_index = i;
            }
            res += p[max_index - 1] * p[max_index] * p[max_index + 1];

            //删除该元素
            for(int i=max_index;i<n-1;i++)
            {
                p[i]=p[i+1];
            }
            n -=1;
        }
        res += p[0]*p[1]*p[2];

        return res;
    }
}

/*
        //测试用例
        int[] p = {30, 35, 15, 5, 10, 20, 25};
        int n=p.length-1;
        int[][] m = new int[n][n];
        int[][] s = new int[n][n];
        MatrixChainMulOrder.getOrder(p, m, s);
        MatrixChainMulOrder.printOrder(s, 0,5);

        //打印二维数组
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                System.out.print(s[i][j]+" ");
            }
            System.out.println();
        }

========比较贪心法与动态规划============
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


*/
