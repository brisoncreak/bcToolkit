package com.bcToolkit.tools;

import java.lang.Math;

public class RandomGetter {

    //得到随机整数
    public static int getRandomNum(int lower, int upper)
    {
        int result = (int) (lower + Math.random() * (upper - lower + 1));
        return result;
    }
    //得到随机整数数组
    public static int[] getRandomArray(int length, int lower, int upper)
    {
        int[] result = new int[length];
        for(int i=0;i<length;i++)
        {
            result[i] = getRandomNum(lower, upper);
        }
        return result;
    }

}
