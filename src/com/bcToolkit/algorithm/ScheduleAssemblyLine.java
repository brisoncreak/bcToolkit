package com.bcToolkit.algorithm;

import java.util.Arrays;
import java.util.Collections;

public class ScheduleAssemblyLine {
    //流水线调度问题：两条流水线可同时混用，但切换流水线需要开销
    //a1 a2流水线每站耗时 t1每个由1切换到2耗时 t2每个由2切换到1耗时 e1 e2 启动耗时 x1 x2结束耗时 n工序数

    //保存最终结果
    public static class Result{
        public int fstar;//最短用时
        public int lstar;//结束时流水线
        public int[] l1, l2;

        public Result(int mintime, int lstar, int[] l1, int[] l2)
        {
            this.fstar = mintime;
            this.lstar = lstar;
            this.l1 = l1;
            this.l2 = l2;
        }
    }
    //动态规划求解
    public static Result fastestWay(int a1[],int a2[],int t1[],int t2[],
                                  int e1,int e2,int x1,int x2,int n){
        int[] f1 = new int[n];
        int[] f2 = new int[n];//用于保存每一步耗时

        int[] l1 = new int[n];
        int[] l2 = new int[n];//记录每一步切换情况


        f1[0] = e1 + a1[0];
        f2[0] = e2 + a2[0];//第一站用时

        l1[0] = 1;
        l2[0] = 2;//记录初始化
        
        for(int j=1;j<n;j++)
        {
            //流水线1
            if(f1[j-1]+a1[j] <= f2[j-1]+t2[j-1]+a1[j])//从1不切换到j <= 从2切换到j
            {
                f1[j] = f1[j-1]+a1[j];
                l1[j] = 1;
            }else{
                f1[j] = f2[j-1]+t2[j-1]+a1[j];
                l1[j] = 2;
            }
            //流水线2
            if(f2[j-1]+a2[j] <= f1[j-1]+t1[j-1]+a2[j])//从2不切换到j <= 从1切换到j
            {
                f2[j] = f2[j-1]+a2[j];
                l2[j] = 2;
            }else{
                f2[j] = f1[j-1]+t1[j-1]+a2[j];
                l2[j] = 1;
            }
        }

        int fstar = 0;//保存最短耗时
        int lstar = 0;//记录最后一站位于哪个流水线

        if(f1[n-1]+x1 <= f2[n-1]+x2)//结合出流水线时间得到最终时间
        {
            fstar = f1[n-1]+x1;
            lstar = 1;
        } else{
            fstar = f2[n-1]+x2;
            lstar = 2;
        }

        return new Result(fstar,lstar, l1, l2);
    }

    //递归打印结果
    public static void printStationRecursive(int i, int[] l1,int[] l2, int n){
        if(n==0) return;//终止条件

        int j=0;
        if(i==1) j=l1[n-1];
        else j=l2[n-1];//计算前一个站在哪条线上

        printStationRecursive(j, l1, l2, n-1);//递归打印前一个站

        //打印结果
        if(i==1){
            System.out.println("line "+1+", station "+n+"\n");
        } else {
            System.out.println("line "+2+", station "+n+"\n");
        }
    }



    //====分治========================================================================//
    //数据结构用于保存递归结果
    public static class MergeRes{
        public int l1;//➡️
        public int l2;//⬇️
        public int l3;//↘️
        public int l4;//↗️


        public MergeRes(int a, int b, int c, int d){
            this.l1 = a;
            this.l2 = b;
            this.l3 = c;
            this.l4 = d;

        }

    }
    //将站两两合并
    public static MergeRes mergeStation(int a1[], int a2[], int t1[], int t2[],
                                   int low, int high, int[] l1, int[] l2){

        if(low==high) return new MergeRes(a1[low], a2[low], 999, 999);

        int mid = (low+high)/2;//计算中点

        MergeRes leftRes = mergeStation(a1, a2, t1, t2, low, mid, l1, l2);
        MergeRes rightRes = mergeStation(a1, a2, t1, t2,mid+1, high, l1, l2);

        int L1 = leftRes.l1, L2 = leftRes.l2, L3 = leftRes.l3, L4 = leftRes.l4;
        int R1 = rightRes.l1, R2 = rightRes.l2, R3 = rightRes.l3, R4 = rightRes.l4;
        int T1 = t1[mid];//↘️
        int T2 = t2[mid];//↗️

        int[] new1 = {L1+R1, L3+R4, L1+R4+T1, L3+R1+T2};
        int[] new2 = {L2+R2, L4+R3, L4+R2+T1, L2+R3+T2};
        int[] new3 = {L1+R3, L3+R2, L1+R2+T1, L3+R3+T2};
        int[] new4 = {L4+R1, L2+R4, L4+R4+T1, L2+R1+T2};

        int line1Min = getMin(new1,4);
        int line2Min = getMin(new2,4);
        int line3Min = getMin(new3,4);
        int line4Min = getMin(new4,4);

        return new MergeRes(line1Min, line2Min,line3Min, line4Min);
    }
    //处理递归结果
    public static Result fastestWayDC(int a1[],int a2[],int t1[],int t2[],
                                    int e1,int e2,int x1,int x2,int n){
        //考虑进入离开流水线时间
        a1[0] += e1;
        a2[0] += e2;

        a1[n-1] += x1;
        a2[n-1] += x2;

        int[] l1 = new int[n];
        int[] l2 = new int[n];//记录每一步切换情况
        l1[0] = 1;
        l2[0] = 2;//记录初始化

        MergeRes res = mergeStation(a1, a2, t1, t2, 0, n-1, l1, l2);

        //记录最大值 最后出哪条流水线
        int fstar, lstar;

        int[] res0 = {res.l1, res.l2, res.l3, res.l4};

        fstar = getMin(res0, 4);

        return new Result(fstar, 0, l1, l2);
    }

    public static int getMin(int[] a,  int n)
    {
        int min = Integer.MAX_VALUE;
        for(int i=0;i<n;i++)
        {
            if(a[i]<=min) min = a[i];
        }
        return min;
    }


}

/*   测试代码
        int[] a1 = {7, 9, 3, 4, 8, 4};
        int[] a2 = {8, 5, 6, 4, 5, 7};
        int[] t1 = {2, 3, 1, 3, 4};
        int[] t2 = {2, 1, 2, 2, 1};
        int e1 = 2, e2 = 4, x1 = 3, x2 = 2;
        int n = 6;
        ScheduleAssemblyLine.Result res = ScheduleAssemblyLine.fastestWay(a1, a2, t1, t2,
                e1, e2, x1, x2, n);


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

        ScheduleAssemblyLine.Result res0 =ScheduleAssemblyLine.fastestWayDC(a1, a2, t1, t2,
                e1, e2, x1, x2, n);

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
        System.out.println(res0.fstar);
*/
