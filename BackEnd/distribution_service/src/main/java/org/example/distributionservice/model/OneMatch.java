package org.example.distributionservice.model;

/**
 * @description:
 * @author: LuBixing
 * @date: 2021/12/28 18:20
 */
public class OneMatch {
    private int[][] edges;
    private int minIndex;

    public void setEdges(int[][] edges) {
        this.edges = edges;
    }
    public int[][] getEdges() {
        return edges;
    }
    public void setMinIndex(int minIndex) {
        this.minIndex = minIndex;
    }
    public int getMinIndex() {
        return minIndex;
    }
    public int[] getPath(){
        int[] path = new int[1];
        path[0] = minIndex;
        return path;
    }
}
