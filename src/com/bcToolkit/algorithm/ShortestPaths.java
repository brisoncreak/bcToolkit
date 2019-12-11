package com.bcToolkit.algorithm;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;

public class ShortestPaths {
    public static class Graph{
        int vertexNum;//顶点数
        int edgeNum;//边数
        int[][] weights;//邻接矩阵

        public Graph(int a, int b, int[][] w){
            this.vertexNum = a;
            this.edgeNum = b;
            w = weights = w;
        }
    }
    public static class node implements Comparable<node>{
        int n;
        int index;
        public node(int n, int i)
        {
            this.n = n;
            this.index = i;
        }
        public void changeValue(int n, int i)
        {
            this.n = n;
            this.index = i;
        }

        @Override
        public int compareTo(node o) {
            if(this.n > o.n) return 1;
            else if(this.n==o.n) return 0;
            else return -1;
        }
    }

    public static int dijkstra(Graph G, int start, int end, String[] path){
        //初始化
        int MAX_VALUE = 9999;
        //记录到每个顶点的最短距离
        int res[] = new int[G.vertexNum];


        Queue<node> q = new PriorityQueue<>();
        //初始化
        for(int i=0;i<G.vertexNum;i++){
            if(i==start) q.add(new node(0, i));
            else q.add(new node(MAX_VALUE, i));
        }
        //记录最短路径
        for(int i = 0; i < G.vertexNum; i++){
            path[i] = String.valueOf(start);
        }

        while(!q.isEmpty()){

            //执行一次插入操作 使得优先队列重新有序 否则顺序不更新得到错误结果
            node a = new node(0,0);
            q.add(a);
            q.remove(a);

            node u = q.poll();
            //记录到每个点最短距离
            res[u.index] = u.n;

            //遍历优先队列
            Iterator<node> iter = q.iterator();
            while(iter.hasNext()){
                node n = iter.next();
                //由n出发有通路
                if(G.weights[u.index][n.index] != 0)
                {
                    //距离比已有小
                    if(n.n>u.n+G.weights[u.index][n.index]){
                        //更新结果
                        n.changeValue(u.n+G.weights[u.index][n.index], n.index);
                        path[n.index]=path[u.index]+"->"+n.index;
                    }
                }

            }
            //执行一次插入操作 使得优先队列重新有序 否则顺序不更新得到错误结果
            node a0 = new node(0,0);
            q.add(a0);
            q.remove(a0);

        }
        for(int i=0;i<G.vertexNum;i++)
            System.out.print(res[i]+" ");
        System.out.println();
        return res[end];

    }
}
