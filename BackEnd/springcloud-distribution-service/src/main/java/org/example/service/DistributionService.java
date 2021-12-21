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
    private final GraphMatch graphMatch;
    private final int passengerCount;
    private final int driverCount;

    public DistributionService(){
        this.graphMatch = null;
        this.passengerCount = 0;
        this.driverCount = 0;
    }
    public DistributionService(int passengerCount,int driverCount){
        this.graphMatch = new GraphMatch();
        this.passengerCount = passengerCount;
        this.driverCount = driverCount;

        String[][] passenger ={
                {"1","31.283036","121.501564"},
                {"2","31.249582","121.455752"}};
        String[][] driver ={
                {"1","31.286428","121.212090"},
                {"2","31.194202","121.320655"}};

        //获取距离信息
        DistanceService distanceService = new DistanceService(2,2,passenger,driver);
        distanceService.calculateDistance();
        int[][] edges = distanceService.getResult();

        graphMatch.setEdges(edges);
        graphMatch.setOnPath(new boolean[this.driverCount]);
        int[] pathAry = new int[this.driverCount];
        Arrays.fill(pathAry, -1);
        graphMatch.setPath(pathAry);
    }

    /*
     * @description: 等初始化图完毕之后，由外部调用接口进行分配
     * @author: LuBixing
     * @date: 2021/12/21 14:14
     */
    public void distribute(){
        for(int i = 0 ; i < this.passengerCount; i ++) {
            search(graphMatch, i);
            clearOnPathSign(graphMatch);
        }
    }

    /*
     * @description:由外部调用获取匹配结果
     * @author: LuBixing
     * @date: 2021/12/21 22:20
     * @param: null
     * @return: 配对结果
     */
    public int[] getResult(){
        return graphMatch.getPath();
    }

    /**
     * 清空当前路径上遍历过的Y点
     */
    private void clearOnPathSign(GraphMatch graphMatch){
        graphMatch.setOnPath(new boolean[this.driverCount]);
    }
    /**
     * 对于某左侧节点X的查找
     * @param graphMatch 图的对象
     * @param xIndex X的索引位置
     * @return 是否成功
     */
    private boolean search(GraphMatch graphMatch, Integer xIndex){
        for(int yIndex = 0 ; yIndex < this.driverCount ; yIndex ++){
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
