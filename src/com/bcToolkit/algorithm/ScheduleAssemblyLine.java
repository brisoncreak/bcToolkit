package com.bcToolkit.algorithm;

import com.sun.org.apache.xpath.internal.operations.Bool;

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
        public int line1;//到line1的最小时间
        public int line2;//到line2的最小时间
        public int line1Flag;
        public int line2Flag;

        public MergeRes(int a, int b, int c, int d){
            this.line1 = a;
            this.line2 = b;
            this.line1Flag = c;
            this.line2Flag = d;
        }

    }
    //将站两两合并
    public static MergeRes mergeStation(int a1[], int a2[], int t1[], int t2[],
                                   int low, int high, int[] l1, int[] l2){

        if(low==high) return new MergeRes(a1[low], a2[low],1 ,2);

        int mid = (low+high)/2;//计算中点

        MergeRes leftRes = mergeStation(a1, a2, t1, t2, low, mid, l1, l2);
        MergeRes rightRes = mergeStation(a1, a2, t1, t2,mid+1, high, l1, l2);

        int line1to1 = leftRes.line1+rightRes.line1+t1[mid]*(rightRes.line1Flag-1);
        int line2to1 = leftRes.line2+rightRes.line1+t2[mid]*(2-rightRes.line1Flag);
        int line2to2 = leftRes.line2+rightRes.line2+t2[mid]*(2-rightRes.line2Flag);
        int line1to2 = leftRes.line1+rightRes.line2+t1[mid]*(rightRes.line2Flag-1);

        int line1Flag = 0, line2Flag = 0;
        int line1Min = 0, line2Min = 0;

        if(line1to1 <= line2to1)
        {
            line1Min = line1to1;
            line1Flag = 1;
            l1[mid+1] = 1;
        }else{
            line1Min = line2to1;
            line1Flag = 2;
            l1[mid+1] = 2;
        }
        if(line2to2 <= line1to2)
        {
            line2Min = line2to2;
            line2Flag = 2;
            l2[mid+1] = 2;
        }else{
            line2Min = line1to2;
            line2Flag = 1;
            l2[mid+1] = 1;
        }

        return new MergeRes(line1Min, line2Min,line1Flag, line2Flag);
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
        if(res.line1 <= res.line2)
        {
            fstar = res.line1;
            lstar = res.line1Flag;
        }else{
            fstar = res.line2;
            lstar = res.line2Flag;
        }

        return new Result(fstar, lstar, l1, l2);
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
