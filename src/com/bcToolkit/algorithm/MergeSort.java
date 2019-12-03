package com.bcToolkit.algorithm;

public class MergeSort {
    //归并排序 A为数组 p、r为起始结束下标
    public static void mergeSort(int[] A,int p,int r)
    {
        if(p < r) {
            int q = (p + r) / 2;//计算中间下标

            mergeSort(A, p, q);
            mergeSort(A, q + 1, r);//分而治之

            merge(A, p, q, r);//合并
        }
    }
    //合并
    public static void merge(int[] A,int p,int q,int r){
        //将分开的左右两部分 分别申请空间存放
        int n1 = q-p+1;
        int n2 = r-q;

        int[] L = new int[n1+1];
        int[] R = new int[n2+1];

        for(int i=0;i<n1;i++) L[i]=A[p+i];
        for(int i=0;i<n2;i++) R[i]=A[q+i+1];

        L[n1] = Integer.MAX_VALUE;
        R[n2] = Integer.MAX_VALUE; //防止数组越界

        //从左右部分依次挑最小的放回原数组
        int i=0,j=0;
        for(int k=p;k<r+1;k++) {
            if(L[i] <= R[j]) {
                A[k] = L[i];
                i++;
            }
            else{
                A[k] = R[j];
                j++;
            }
        }
    }
}
