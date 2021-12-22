package org.example.distributionservice.service;

import org.example.distributionservice.model.GraphMatch;
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
    private final String[][] passenger;
    private final String[][] driver;
    private int[][] edges;

    public DistributionService(){
        this.graphMatch = null;
        this.passengerCount = 0;
        this.driverCount = 0;
        this.passenger = null;
        this.driver = null;
    }
    public DistributionService(int passengerCount,int driverCount,String[][] passenger,String[][] driver){
        this.graphMatch = new GraphMatch();
        this.passengerCount = passengerCount;
        this.driverCount = driverCount;
        this.passenger = passenger;
        this.driver = driver;
    }
    /*
     * @description: 实例化距离微服务获取距离关系
     * @author: LuBixing
     * @date: 2021/12/23 2:40
     */
    private int[][] getDistance(){
        DistanceService distanceService = new DistanceService(this.passengerCount,this.driverCount,this.passenger,this.driver);
        return distanceService.calculateDistance();
    }

    /*
     * @description:实例化信用度微服务获取司机信用度列表
     * @author: LuBixing
     * @date: 2021/12/23 2:43
     */
    private int[] getCredit(){
        CreditService creditService = new CreditService(this.driverCount);
        return creditService.getCredit();
    }
    /*
     * @description:计算二分图的权值（目前根据距离和信用度）
     * @author: LuBixing
     * @date: 2021/12/23 2:45
     */
    private int[][] calculateEdges(){
        int[][] distance = getDistance();
        int[] credit = getCredit();
        /////////////////////////////////////计算放入edges
        return null;
    }

    /*
     * @description: 等初始化图完毕之后，由外部调用接口进行分配
     * @author: LuBixing
     * @date: 2021/12/21 14:14
     */
    public int[] distribute(){
        assert graphMatch != null;
        graphMatch.setEdges(edges);
        graphMatch.setOnPath(new boolean[this.driverCount]);
        int[] pathAry = new int[this.driverCount];
        Arrays.fill(pathAry, -1);
        graphMatch.setPath(pathAry);

        for(int i = 0 ; i < this.passengerCount; i ++) {
            search(graphMatch, i);
            clearOnPathSign(graphMatch);
        }
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
            if(graphMatch.getEdges()[xIndex][yIndex] <= 0 ){
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
