package com.bcToolkit.algorithm;

public class MaximumSubarray {
    //数据结构用于保存结果
    public static class MSres{
        int low;
        int high;
        int sum;

        public MSres(int low, int high, int sum){
            this.low = low;
            this.high = high;
            this.sum = sum;
        }
        public int getLow(){return this.low;}
        public int getHigh(){return this.high;}
        public int getSum(){return this.sum;}

    }
    //查找最大子数组 分治思路
    public static MSres findMaximumSubarray (int[] A, int low, int high)
    {
        if(low == high) return new MSres(low, high, A[low]);//仅有一个元素情况
        else {
            int mid = (low+high)/2;
            MSres leftRes = findMaximumSubarray(A, low, mid);//查找左半部分
            MSres rightRes = findMaximumSubarray(A, mid+1,high);//查找右半部分
            MSres crossRes = findMaxCrossingSubarray(A, low, mid, high);//查找跨过中点的情况

            //返回sum最大的子数组
            if(leftRes.sum>=rightRes.sum && leftRes.sum>=crossRes.sum) return leftRes;
            else if(rightRes.sum>=leftRes.sum && rightRes.sum>=crossRes.sum) return rightRes;
            else return crossRes;
        }
    }
    //查找跨过中点的情况
    public static MSres findMaxCrossingSubarray(int[] A, int low, int mid, int high)
    {
        int leftSum = Integer.MIN_VALUE, RightSum = Integer.MIN_VALUE;
        int sum = 0;
        int maxLeftIndex = mid, maxRightIndex = mid;
        //向左试探 并记录最大值和下标
        for(int i=mid;i>=low;i--)
        {
            sum = sum + A[i];
            if(sum > leftSum)
            {
                leftSum = sum;
                maxLeftIndex = i;
            }
        }
        sum = 0;
        //向右试探 并记录最大值和下标
        for(int j=mid+1;j<=high;j++)
        {
            sum = sum + A[j];
            if(sum > RightSum)
            {
                RightSum = sum;
                maxRightIndex = j;
            }
        }
        return new MSres(maxLeftIndex, maxRightIndex, leftSum+RightSum);
    }

    //蛮力算法
    public static MSres findMSBrute(int[] A){
        int sum = -1;
        int besti = 0, bestj = 0;
        for(int i=0;i<A.length;i++)
        {
            int thisSum = 0;
            for(int j=i;j<A.length;j++)
            {
                thisSum += A[j];
                if(thisSum > sum)
                {
                    sum = thisSum;
                    besti = i;
                    bestj = j;
                }
            }
        }
        return new MSres(besti, bestj, sum);
    }
}
//输入数据：e= {-2,11,-4,13,-5,-2}
//输出结果：1,3 20
//
//        int[] e= {-2,11,-4,13,-5,-2};
//        MaximumSubarray.MSres result = MaximumSubarray.findMaximumSubarray(e, 0, 5);
//
//        System.out.println(result.getLow() + "," + result.getHigh() + " " + result.getSum());
//
// 分治算法与蛮力算法差异测试：
//        int length = 10000;
//        int[] x = RandomGetter.getRandomArray(length, -5000,5000);
//        int[] y = x.clone();
//
//        long startTime1 = System.nanoTime(); //获取开始时间
//        MaximumSubarray.MSres result = MaximumSubarray.findMaximumSubarray(x, 0, length-1);
//        long endTime1 = System.nanoTime(); //获取结束时间
//        System.out.println("分治算法运行时间：" + (endTime1 - startTime1) + "ns"); //输出程序运行时间
//        System.out.println(result.getLow() + "," + result.getHigh() + " " + result.getSum());
//
//        long startTime2 = System.nanoTime(); //获取开始时间
//        MaximumSubarray.MSres result2 = MaximumSubarray.findMSBrute(y);
//        long endTime2 = System.nanoTime(); //获取结束时间
//        System.out.println("蛮力算法运行时间：" + (endTime2 - startTime2) + "ns"); //输出程序运行时间
//        System.out.println(result2.getLow() + "," + result2.getHigh() + " " + result2.getSum());