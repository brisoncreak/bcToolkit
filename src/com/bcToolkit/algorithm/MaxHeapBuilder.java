package com.bcToolkit.algorithm;

public class MaxHeapBuilder {
    //将无序数组调整成大顶堆 A为数组
    public static void buildMaxHeap(int[] A){
        for(int i=A.length/2-1;i>0;i--)//A.length/2-1是第一个有孩子的节点下标
        {
            maxHeapify(A, i);
        }
    }
    //以某一节点i作为父节点 将其子孙调整为大顶堆（分治思路）
    public static void maxHeapify(int[] A, int i)
    {
        int largest = i;
        int l = 2*i;//左孩子下标
        int r = 2*i+1;//右孩子下标 （堆的下标从1开始）

        //左右孩子大于自身
        if (l <= A.length && A[l-1] > A[largest-1])
            largest = l;
        if (r <= A.length && A[r-1] > A[largest-1])
            largest = r;

        if(largest != i)
        {
            //与大于自身的孩子交换
            int temp = A[i-1];
            A[i-1] = A[largest-1];
            A[largest-1] = temp;

            //将该孩子作为父节点 递归调整其子孙
            maxHeapify(A, largest);
        }
    }
    //输入数据：A={4, 1, 3, 2, 16, 9, 10, 14, 8, 7}
    //输出结果：A={16, 14, 10, 8, 7, 9, 3, 2, 4, 1}
}
