package com.bcToolkit.algorithm;

import java.lang.Math;


public class FindMaximum {
    //分治法查找数组中的最大值 nums为数组 p、r为起始结束下标
    public static int findMaximum(int[] nums, int p, int r) {
        int left = 0, right = 0;
        int q = (p+r)/2;//计算中间下标

        if(p==r) return nums[p];//子数组长度为1直接返回

        if(p==r-1) return Math.max(nums[p], nums[r]);//长度为2子数组比较

        if(p < r) {
            left = findMaximum(nums, p, q);
            right = findMaximum(nums, q+1, r);//递归计算左右子数组
        }
        return Math.max(left,right);
    }
    //输入数据：a={1, 2, 30, 4, 5, 6, 7, 8, 9, 10} p=0 r=9;
    //输出结果：30
}
