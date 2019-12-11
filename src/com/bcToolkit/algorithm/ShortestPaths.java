package com.bcToolkit.algorithm;

import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;

public class ShortestPaths {
    //图
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
    //优先队列中存放的元素
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

        //实现Comparable接口 用于优先队列中判断次序
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

            node u = q.poll();
            //记录到每个点最短距离
            res[u.index] = u.n;

            //遍历优先队列
            Iterator<node> iter = q.iterator();

            Queue<node> temp = new PriorityQueue<>();

            while(iter.hasNext()){
                node n = iter.next();
                //由n出发有通路 && 距离比已有小
                if(G.weights[u.index][n.index] != 0 && n.n>u.n+G.weights[u.index][n.index])
                {
                    //更新结果
                    n.changeValue(u.n+G.weights[u.index][n.index], n.index);
                    path[n.index]=path[u.index]+"->"+n.index;
                }
                //迭代过程中不允许修改原优先队列 故将内容copy至temp 使用temp代替p
                temp.add(new node(n.n, n.index));
            }
            q = temp;

        }
        //返回最短路径长度
        return res[end];
    }
}
/*        int vertex = 16;
        int edge = 30;
        int[][] w = new int[16][16];

        for(int i=0;i<w.length;i++) for(int j=0;j<w.length;j++) w[i][j] = 9999;
        //初始化
        w[0][1]=5;w[0][2]=3;
        w[1][3]=1;w[1][4]=3;w[1][5]=6;
        w[2][4]=8;w[2][5]=7;w[2][6]=6;
        w[3][7]=6;w[3][8]=8;
        w[4][7]=3;w[4][8]=5;
        w[5][8]=3;w[5][9]=3;
        w[6][8]=8;w[6][9]=4;
        w[7][10]=2;w[7][11]=2;
        w[8][11]=1;w[8][12]=2;
        w[9][11]=3;w[9][12]=3;
        w[10][13]=2;w[10][14]=5;
        w[11][13]=5;w[11][14]=2;
        w[12][13]=6;w[12][14]=6;
        w[13][15]=4;w[14][15]=3;

        //无向图 反方向赋予权值
        for(int i=0;i<w.length;i++) for(int j=i+1;j<w.length;j++) w[j][i] = w[i][j];

        ShortestPaths.Graph G = new ShortestPaths.Graph(vertex, edge, w);

        String path[] = new String[vertex];
        int start = 0;
        int end = 15;
        int res = ShortestPaths.dijkstra(G, start, end, path);

        System.out.println("最短路径长度："+res);

        System.out.print("最短路径为：");

        System.out.println(path[end]);
*/
