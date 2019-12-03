package com.bcToolkit.test;

import com.bcToolkit.algorithm.*;
import com.bcToolkit.tools.RandomGetter;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        int[] a = {1, 2, 30, 4, 5, 6, 7, 8, 9, 10};
        int[] b = {4, 1, 3, 2, 16, 9, 10, 14, 8, 7};
        int[] c= {0,1,2,3,4,5};
        int[] d= {2,8,7,1,3,5,6,4};
        //MaxHeapBuilder.buildMaxHeap(b);

        //MergeSort.mergeSort(b,0,9);

        //设定随机生成数组的规模
        int length = 1000;
        //随机生成整数数组
        int[] x = RandomGetter.getRandomArray(length, 0,10000);

        long startTime1 = System.nanoTime(); //获取开始时间
        MergeSort.mergeSort(x,0,length-1);
        long endTime1 = System.nanoTime(); //获取结束时间
        System.out.println("MergeSort运行时间：" + (endTime1 - startTime1) + "ns"); //输出程序运行时间

        long startTime2 = System.nanoTime(); //获取开始时间
        QuickSort.quickSort(x,0,length-1);
        long endTime2 = System.nanoTime(); //获取结束时间
        System.out.println("QuickSort运行时间：" + (endTime2 - startTime2) + "ns"); //输出程序运行时间

    }
}
