package com.bcToolkit.algorithm;

import com.bcToolkit.tools.RandomGetter;

public class QuickSort {
    //分治思路的快速排序 A为数组 p、r为起始结束下标
    public static void quickSort(int[] A, int p, int r){
        if(p < r) {
            int q = partition(A, p ,r);//计算中间下标

            quickSort(A, p, q-1);
            quickSort(A, q + 1, r);//分而治之 无需合并
        }
    }
    public static int partition(int[] A, int p, int r){
        //int pivot = A[r]; //选最后一个元素作为pivot
        int pivot = A[RandomGetter.getRandomNum(p,r)];//使用随机能极大提高速度
        int i = p-1;//记录结果
        //如果元素小于pivot 则该元素和i处元素互换位置 i++
        for(int j=p;j<r-1;j++){
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
