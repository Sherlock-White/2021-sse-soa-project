package org.example.model;

/**
 * @description:
 * @author: LuBixing
 * @date: 2021/12/21 10:02
 */
public class GraphMatch {

    private int[][] edges; //所有的连接边
    private boolean[] onPath; //当前查找路径的某节点是否已经被扫描过
    private int[] path; //增广路

    public int[][] getEdges() {
        return edges;
    }

    public void setEdges(int[][] edges) {
        this.edges = edges;
    }

    public boolean[] getOnPath() {
        return onPath;
    }

    public void setOnPath(boolean[] onPath) {
        this.onPath = onPath;
    }

    public int[] getPath() {
        return path;
    }

    public void setPath(int[] path) {
        this.path = path;
    }

}