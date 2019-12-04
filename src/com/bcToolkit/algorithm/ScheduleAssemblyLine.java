package com.bcToolkit.algorithm;

public class ScheduleAssemblyLine {
    //流水线调度问题：两条流水线可同时混用，但切换流水线需要开销
    //a1 a2流水线每站耗时 t1每个由1切换到2耗时 t2每个由2切换到1耗时 e1 e2 启动耗时 x1 x2结束耗时 n工序数

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


    public static Result fastestWay(int a1[],int a2[],int t1[],int t2[],
                                  int e1,int e2,int x1,int x2,int n){
        int[] f1 = new int[n];
        int[] f2 = new int[n];//用于保存每一步耗时

        int[] l1 = new int[n];
        int[] l2 = new int[n];//记录每一步切换情况


        f1[0] = e1 + a1[0];
        f2[0] = e2 + a2[0];//第一站用时

        l1[0] = 1;
        l2[1] = 2;//记录初始化



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

    public static void printStationRecursive(int i, int[] l1,int[] l2, int n){
        if(n==0) return;

        int j=0;
        if(i==1) j=l1[n-1];
        else j=l2[n-1];

        printStationRecursive(j, l1, l2, n-1);
        
        if(i==1){
            System.out.println("line "+1+", station "+n+"\n");
        } else {
            System.out.println("line "+2+", station "+n+"\n");
        }


    }

}
