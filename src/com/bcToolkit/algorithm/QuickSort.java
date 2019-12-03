package com.bcToolkit.algorithm;

import com.bcToolkit.tools.RandomGetter;

public class QuickSort {
    //分治思路的快速排序 A为数组 p、r为起始结束下标
    public static void quickSort(int[] A, int p, int r){
        if(p < r) {
            int q = partition(A, p ,r);//计算中间下标

            quickSort(A, p, q-1);
            quickSort(A, q+1, r);//分而治之 无需合并
        }
    }
    public static int partition(int[] A, int p, int r){
        //取得随机元素并与最后一个元素交换位置
        //int a = RandomGetter.getRandomNum(p,r);
        //int t = A[r];
        //A[r] = A[a];
        //A[a] = t;

        int pivot = A[r]; //选最后一个元素作为pivot
        int i = p-1;//记录结果
        //如果元素小于pivot 则该元素和i处元素互换位置 i++
        for(int j=p;j<r;j++){
            if(A[j]<= pivot)
            {
                i = i+1;

                int temp = A[i];
                A[i] = A[j];
                A[j] = temp;
            }
        }
        //将pivot放在i+1位置 返回i+1
        int temp = A[i+1];
        A[i+1] = A[r];
        A[r] = temp;

        return i+1;
    }


}
//比较MergeSort和QuickSort
//        //设定随机生成数组的规模
//        int length = 1000;
//        //随机生成整数数组
//        int[] x = RandomGetter.getRandomArray(length, 0,10000);
//        int[] y = x.clone();
//        long startTime1 = System.nanoTime(); //获取开始时间
//        MergeSort.mergeSort(x,0,length-1);
//        long endTime1 = System.nanoTime(); //获取结束时间
//        System.out.println("MergeSort运行时间：" + (endTime1 - startTime1) + "ns"); //输出程序运行时间
//
//        for(int i=0;i<100;i++) System.out.print(x[i]+" ");
//        System.out.print("\n");
//
//        long startTime2 = System.nanoTime(); //获取开始时间
//        QuickSort.quickSort(y,0,length-1);
//        long endTime2 = System.nanoTime(); //获取结束时间
//        System.out.println("QuickSort运行时间：" + (endTime2 - startTime2) + "ns"); //输出程序运行时间
//        for(int i=0;i<100;i++) System.out.print(y[i]+" ");