package com.bcToolkit.test;

import com.bcToolkit.algorithm.*;
import com.bcToolkit.tools.RandomGetter;

import java.util.Random;

public class Main {

    public static void main(String[] args) {

        //设定随机生成数组的规模
        int length = 5;
        //随机生成整数数组
        int[] a1 = RandomGetter.getRandomArray(length, 0, 10);
        int[] a2 = RandomGetter.getRandomArray(length, 0, 10);
        int[] t1 = RandomGetter.getRandomArray(length - 1, 0, 10);
        int[] t2 = RandomGetter.getRandomArray(length - 1, 0, 10);
        int e1 = RandomGetter.getRandomNum(0, 10);
        int e2 = RandomGetter.getRandomNum(0, 10);
        int x1 = RandomGetter.getRandomNum(0, 10);
        int x2 = RandomGetter.getRandomNum(0, 10);
        int n = length;


        long startTime1 = System.nanoTime(); //获取开始时间
        ScheduleAssemblyLine.Result res = ScheduleAssemblyLine.fastestWay(a1.clone(), a2.clone(),
                t1.clone(), t2.clone(), e1, e2, x1, x2, n);
        long endTime1 = System.nanoTime(); //获取结束时间
        System.out.println("动态规划算法运行时间：" + (endTime1 - startTime1) + "ns"); //输出程序运行时间
        System.out.println("动态规划结果："+" "+res.fstar);


        System.out.print("\n");

        long startTime2 = System.nanoTime(); //获取开始时间
        ScheduleAssemblyLine.ResultDC res0 = ScheduleAssemblyLine.fastestWayDC(a1.clone(), a2.clone(),
                t1.clone(), t2.clone(), e1, e2, x1, x2, n);
        long endTime2 = System.nanoTime(); //获取结束时间
        System.out.println("分治算法运行时间：" + (endTime2 - startTime2) + "ns"); //输出程序运行时间
        System.out.println("分治算法结果"+" "+res0.fstar);

    }

}

