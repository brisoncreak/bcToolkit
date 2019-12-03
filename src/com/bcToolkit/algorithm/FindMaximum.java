package com.bcToolkit.algorithm;

import java.lang.Math;


public class FindMaximum {
    public static int findMaximum(int[] nums, int p, int r) {
        int left = 0, right = 0;
        int q = (r+p)/2;

        if(p==r) return nums[p];

        if(p==r-1) return Math.max(nums[p], nums[r]);

        if(p < r) {
            left = findMaximum(nums, p, q);
            right = findMaximum(nums, q+1, r);
        }

        return Math.max(left,right);
    }
}
