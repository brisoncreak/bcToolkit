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
    //保存最终结果
    public static class ResultDC{
        public int fstar;//最短用时
        public int lstar;//结束时流水线
        public int[] l1, l2, l3, l4;

        public ResultDC(int mintime, int lstar, int[] l1, int[] l2, int[] l3, int[] l4)
        {
            this.fstar = mintime;
            this.lstar = lstar;
            this.l1 = l1;
            this.l2 = l2;
            this.l3 = l3;
            this.l4 = l4;
        }
    }
    //将站两两合并
    public static MergeRes mergeStation(int a1[], int a2[], int t1[], int t2[],
                                   int low, int high, int[] l1, int[] l2, int[] l3, int[] l4){

        if(low==high) return new MergeRes(a1[low], a2[low], 999, 999);

        int mid = (low+high)/2;//计算中点

        MergeRes leftRes = mergeStation(a1, a2, t1, t2, low, mid, l1, l2, l3, l4);
        MergeRes rightRes = mergeStation(a1, a2, t1, t2,mid+1, high, l1, l2, l3, l4);

        int L1 = leftRes.l1, L2 = leftRes.l2, L3 = leftRes.l3, L4 = leftRes.l4;
        int R1 = rightRes.l1, R2 = rightRes.l2, R3 = rightRes.l3, R4 = rightRes.l4;
        int T1 = t1[mid];//↘️
        int T2 = t2[mid];//↗️

        int[] new1 = {L1+R1, L3+R4, L1+R4+T1, L3+R1+T2};
        int[] new2 = {L2+R2, L4+R3, L4+R2+T1, L2+R3+T2};
        int[] new3 = {L1+R3, L3+R2, L1+R2+T1, L3+R3+T2};
        int[] new4 = {L4+R1, L2+R4, L4+R4+T1, L2+R1+T2};

        int[] line1res = getMin(new1,4);
        int[] line2res = getMin(new2,4);
        int[] line3res = getMin(new3,4);
        int[] line4res = getMin(new4,4);

        l1[mid] = line1res[1]+1;
        l2[mid] = line2res[1]+1;
        l3[mid] = line3res[1]+1;
        l4[mid] = line4res[1]+1;


        return new MergeRes(line1res[0], line2res[0],line3res[0], line4res[0]);
    }
    //处理递归结果
    public static ResultDC fastestWayDC(int a1[],int a2[],int t1[],int t2[],
                                    int e1,int e2,int x1,int x2,int n){
        //考虑进入离开流水线时间
        a1[0] += e1;
        a2[0] += e2;

        a1[n-1] += x1;
        a2[n-1] += x2;

        int[] l1 = new int[n];
        int[] l2 = new int[n];
        int[] l3 = new int[n];
        int[] l4 = new int[n];//记录每一步切换情况
        l1[0] = 1;
        l2[0] = 2;
        l3[0] = 3;
        l4[0] = 4;//记录初始化

        MergeRes res = mergeStation(a1, a2, t1, t2, 0, n-1, l1, l2, l3, l4);

        //记录最大值 最后出哪条流水线
        int fstar, lstar;

        int[] res0 = {res.l1, res.l2, res.l3, res.l4};

        int[] min = getMin(res0, 4);

        fstar = min[0];
        lstar = min[1]+1;

        return new ResultDC(fstar, lstar, l1, l2, l3, l4);
    }
    //取得数组中最小值和下标
    public static int[] getMin(int[] a,  int n) {
        int min = Integer.MAX_VALUE;
        int index = -1;

        for (int i = 0; i < n; i++) {
            if (a[i] < min) {min = a[i];
            index = i;}
        }
        int[] res = {min, index};
        return res;
    }
    //递归打印结果
    public static void printStationRecursiveDC(int i, int[] l1,int[] l2, int[] l3, int[] l4, int low, int high){
        //终止条件
        if(low==high) {
            System.out.println("line "+i+", station "+(low+1)+"\n");
            return;
        }
        int mid = (low+high)/2;//计算中点

        int a=0, b=0;

        //打印结果
        if(low+1==high)
        {
            if(i==1) {
                System.out.println("line "+1+", station "+(mid+1)+"\n");
                System.out.println("line "+1+", station "+(mid+2)+"\n");
            }
            if(i==2) {
                System.out.println("line "+2+", station "+(mid+1)+"\n");
                System.out.println("line "+2+", station "+(mid+2)+"\n");
            }
            if(i==3) {
                System.out.println("line "+1+", station "+(mid+1)+"\n");
                System.out.println("line "+2+", station "+(mid+2)+"\n");
            }
            if(i==4) {
                System.out.println("line "+2+", station "+(mid+1)+"\n");
                System.out.println("line "+1+", station "+(mid+2)+"\n");
            }

        }else {
            if(i==1){
                int temp = l1[mid];
                if(temp==1){a=1;b=1;}
                else if(temp==2){a=3;b=4;}
                else if(temp==3){a=1;b=4;}
                else{a=3;b=1;}

            }else if(i==2){
                int temp = l2[mid];
                if(temp==1){a=2;b=2;}
                else if(temp==2){a=4;b=3;}
                else if(temp==3){a=4;b=2;}
                else{a=2;b=3;}
            }else if(i==3){
                int temp = l3[mid];
                if(temp==1){a=1;b=3;}
                else if(temp==2){a=3;b=2;}
                else if(temp==3){a=1;b=2;}
                else{a=3;b=3;}
            }else{
                int temp = l4[mid];
                if(temp==1){a=4;b=1;}
                else if(temp==2){a=2;b=4;}
                else if(temp==3){a=4;b=4;}
                else{a=2;b=1;}

            }
            printStationRecursiveDC(a, l1, l2, l3, l4, low, mid);//递归打印前一半
            printStationRecursiveDC(b, l1, l2, l3, l4, mid + 1, high);//递归打印后一半

        }


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

------------------------比较分治算法和动态规划运行时间------------------------
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
        ScheduleAssemblyLine.printStationRecursive(res.lstar, res.l1, res.l2, n);

        long startTime2 = System.nanoTime(); //获取开始时间
        ScheduleAssemblyLine.ResultDC res0 = ScheduleAssemblyLine.fastestWayDC(a1.clone(), a2.clone(),
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
        for(int i=0;i<n;i++)
        {
            System.out.print(res0.l3[i]+" ");
        }
        System.out.print("\n");
        for(int i=0;i<n;i++)
        {
            System.out.print(res0.l4[i]+" ");
        }
        System.out.print("\n");
        ScheduleAssemblyLine.printStationRecursiveDC(res0.lstar, res0.l1, res0.l2, res0.l3, res0.l4,0, n-1);


*/
