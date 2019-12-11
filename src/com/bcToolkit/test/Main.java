package com.bcToolkit.test;

import com.bcToolkit.algorithm.*;
import com.bcToolkit.tools.RandomGetter;

import java.util.Random;

public class Main {

    public static void main(String[] args) {

        /*
        int vertex = 16;
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

        for(int i=0;i<w.length;i++) for(int j=i+1;j<w.length;j++) w[j][i] = w[i][j];

*/

        int vertex = 9;
        int edge = 5;
        int[][] w = new int[vertex][vertex];

        for(int i=0;i<w.length;i++) for(int j=0;j<w.length;j++) w[i][j] = 9999;
        //初始化
        w[0][1]=5;w[0][2]=3;
        w[1][3]=1;w[1][4]=3;w[1][5]=6;
        w[2][4]=8;w[2][5]=7;w[2][6]=6;
        w[3][7]=6;w[3][8]=8;
        w[4][7]=3;w[4][8]=5;
        w[5][8]=3;
        w[6][8]=8;

        for(int i=0;i<w.length;i++) for(int j=i+1;j<w.length;j++) w[j][i] = w[i][j];

        for(int i=0;i<w.length;i++) {
            for (int j = 0; j < w.length; j++)
                System.out.print(w[i][j]+"\t");
            System.out.println();
        }



/*
        int vertex = 5;
        int edge = 9;
        int[][] w = new int[5][5];

        w[0][1] = 10;
        w[0][2] = 3;
        w[1][2] = 1;
        w[2][1] = 4;
        w[1][3] = 2;
        w[2][3] = 8;
        w[2][4] = 2;
        w[3][4] = 7;
        w[4][3] = 9;
*/
        ShortestPaths.Graph G = new ShortestPaths.Graph(vertex, edge, w);

        String path[] = new String[vertex];
        int start = 0;
        int end = 4;
        int res = ShortestPaths.dijkstra(G, start, end, path);

        System.out.println(res);

        System.out.println("最短路径为：");

        System.out.println(path[end]);

    }
}

