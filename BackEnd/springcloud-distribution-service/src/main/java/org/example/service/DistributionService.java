package org.example.service;

import org.example.model.GraphMatch;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @description:
 * @author: LuBixing
 * @date: 2021/12/20 21:14
 */
@Service
public class DistributionService {
    private final int count;
    private GraphMatch graphMatch;

    public DistributionService(){
        count = 0;
    }
    public DistributionService(int count){
        this.count = count;
        init();
    }
    /**
     * 初始化数据
     */
    private void init(){
        graphMatch = new GraphMatch();
        int[][] edges = new int[count][count];
        edges[0][2] = 1;
        edges[1][0] = 1;
        edges[1][1] = 1;
        edges[2][2] = 1;
        edges[2][1] = 1;
        edges[2][3] = 1;
        edges[3][1] = 1;
        edges[3][2] = 1;
        edges[4][2] = 1;
        edges[4][4] = 1;
        edges[4][3] = 1;
        graphMatch.setEdges(edges);
        graphMatch.setOnPath(new boolean[count]);
        int[] pathAry = new int[count];
        Arrays.fill(pathAry, -1);
        graphMatch.setPath(pathAry);
    }
    /*
     * @description: a method to expose entry to external world
     * @author: LuBixing
     * @date: 2021/12/21 14:14
     */
    public void distribute(){
        for(int i = 0 ; i < count ; i ++) {
            search(graphMatch, i);
            clearOnPathSign(graphMatch);
        }
    }

    /**
     * 输出日志
     */
    public int[] getValue(){
        return graphMatch.getPath();
        /*
        for(int i = 0 ; i < graphMatch.getPath().length ; i ++){
            System.out.println(graphMatch.getPath()[i] + "<--->" + i);
        }*/
    }

    /**
     * 清空当前路径上遍历过的Y点
     */
    private void clearOnPathSign(GraphMatch graphMatch){
        graphMatch.setOnPath(new boolean[count]);
    }

    /**
     * 对于某左侧节点X的查找
     * @param graphMatch 图的对象
     * @param xIndex X的索引位置
     * @return 是否成功
     */
    private boolean search(GraphMatch graphMatch, Integer xIndex){
        for(int yIndex = 0 ; yIndex < count ; yIndex ++){
            //没有连线
            if(graphMatch.getEdges()[xIndex][yIndex] != 1 ){
                continue;
            }
            //已经检测过该节点
            if(graphMatch.getOnPath()[yIndex]){
                continue;
            }
            //设置该节点已经检测过
            graphMatch.getOnPath()[yIndex] = true;
            //当前Y节点是否是未覆盖点 或 是否在增广路径上
            //递归查找
            if(graphMatch.getPath()[yIndex] == -1 || search(graphMatch , graphMatch.getPath()[yIndex])){
                //设置增广路径
                graphMatch.getPath()[yIndex] = xIndex;
                return true;
            }
        }
        return false;
    }
}
