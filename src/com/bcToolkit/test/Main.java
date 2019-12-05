package com.bcToolkit.test;

import com.bcToolkit.algorithm.*;
import com.bcToolkit.tools.RandomGetter;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        int[] a1 = {7, 9, 3, 4, 8, 4};
        int[] a2 = {8, 5, 6, 4, 5, 7};
        int[] t1 = {2, 3, 1, 3, 4};
        int[] t2 = {2, 1, 2, 2, 1};
        int e1 = 2, e2 = 4, x1 = 3, x2 = 2;
        int n = 6;

        ScheduleAssemblyLine.Result res = ScheduleAssemblyLine.fastestWay(a1.clone(), a2.clone(),
                t1.clone(), t2.clone(), e1, e2, x1, x2, n);
        System.out.println("最终结果："+res.fstar+" "+res.lstar);

        ScheduleAssemblyLine.Result res0 = ScheduleAssemblyLine.fastestWayDC(a1.clone(), a2.clone(),
                t1.clone(), t2.clone(), e1, e2, x1, x2, n);
        System.out.println("最终结果："+res0.fstar+" "+res0.lstar);


/*        //比较动态规划算法和分治算法
        //设定随机生成数组的规模
        int length = 10;
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


        for(int i=0;i<n;i++)
        {
            System.out.print(a1[i]+ " ");
        }
        System.out.print("\n");
        for(int i=0;i<n;i++)
        {
            System.out.print(a2[i]+ " ");
        }
        System.out.print("\n");
        for(int i=0;i<n-1;i++)
        {
            System.out.print(t1[i]+ " ");
        }
        System.out.print("\n");
        for(int i=0;i<n-1;i++)
        {
            System.out.print(t2[i]+ " ");
        }
        System.out.print("\n");
        System.out.println(e1+" "+e2+" "+x1+" "+x2);




        long startTime1 = System.nanoTime(); //获取开始时间
        ScheduleAssemblyLine.Result res = ScheduleAssemblyLine.fastestWay(a1.clone(), a2.clone(),
                t1.clone(), t2.clone(), e1, e2, x1, x2, n);
        long endTime1 = System.nanoTime(); //获取结束时间
        System.out.println("动态规划算法运行时间：" + (endTime1 - startTime1) + "ns"); //输出程序运行时间
        System.out.println(res.fstar+" "+res.lstar);

        for(int i=0;i<n;i++)
        {
            System.out.print(res.l1[i]+" ");
        }
        System.out.print("\n");
        for(int i=0;i<n;i++)
        {
            System.out.print(res.l2[i]+" ");
        }
        System.out.print("\n");

        long startTime2 = System.nanoTime(); //获取开始时间
        ScheduleAssemblyLine.Result res0 = ScheduleAssemblyLine.fastestWayDC(a1.clone(), a2.clone(),
                t1.clone(), t2.clone(), e1, e2, x1, x2, n);
        long endTime2 = System.nanoTime(); //获取结束时间
        System.out.println("分治算法运行时间：" + (endTime2 - startTime2) + "ns"); //输出程序运行时间
        System.out.println(res0.fstar+" "+res0.lstar);

        for(int i=0;i<n;i++)
        {
            System.out.print(res0.l1[i]+" ");
        }
        System.out.print("\n");
        for(int i=0;i<n;i++)
        {
            System.out.print(res0.l2[i]+" ");
        }
        System.out.print("\n");
*/
    }

}

