package com.bcToolkit.test;

import com.bcToolkit.algorithm.*;
import com.bcToolkit.tools.RandomGetter;

import java.util.Random;

public class Main {

    public static void main(String[] args) {

        int[] a1 = {7, 9, 3, 4, 8, 4};
        int[] a2 = {8, 5, 6, 4, 5, 7};
        int[] t1 = {2, 3, 1, 4, 4};
        int[] t2 = {2, 1, 2, 2, 1};
        int e1 = 2, e2 = 4, x1 = 3, x2 = 2;
        int n = 6;
        ScheduleAssemblyLine.Result res = ScheduleAssemblyLine.fastestWay(a1, a2, t1, t2, e1, e2, x1, x2, n);


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
        System.out.println(res.fstar);

        ScheduleAssemblyLine.printStationRecursive(res.lstar, res.l1, res.l2, 6);
    }

}

