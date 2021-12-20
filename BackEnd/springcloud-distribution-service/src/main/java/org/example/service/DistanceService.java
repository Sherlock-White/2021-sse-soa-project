package org.example.service;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;

/*
 * @description: service to aggregate some information(distance)
 * @author: zsy
 * @date: 2021/12/19 19:22
 */
@Service
public class DistanceService {
    //邻接矩阵
    public int[][] value=new int[30][30];
    private Vector<String>[] pair;

    public void distribute(String[][] passenger,String[][] driver){
        for(int i=0;i<driver.length;i++) {
            for (int j=0;j<passenger.length;j++){
                //格式：经度,纬度
                //注意：高德最多取小数点后六位
                String origin = driver[i][2]+","+driver[i][1];
                String destination = passenger[j][2]+","+passenger[j][1];
                int distance = getDistance(origin, destination);
                this.value[i][j]=distance;
            }
        }

        //输出邻接矩阵
        for(int i=0;i<driver.length;i++) {
            System.out.print("\n");
            for (int j=0;j<passenger.length;j++){
                System.out.print(value[i][j]+" ");
            }
        }
    }

    public static String loadJson (String url) {
        StringBuilder json = new StringBuilder();
        try {
            //下面那条URL请求返回结果无中文，可不转换编码格式
            URL urlObject = new URL(url);
            URLConnection uc = urlObject.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            String inputLine = null;
            while ( (inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json.toString();
    }
    /*
     * 高德地图WebAPI : 行驶距离测量
     */
    public static int getDistance(String origins,String destination) {
        int type = 1;
        String url = "http://restapi.amap.com/v3/distance?"
                + "origins="+origins
                +"&destination="+destination
                +"&type="+type
                +"&key=b8df6c58bbf46e359344bbe230ab4865";
        //调API
        JSONObject jsonobject = JSONObject.fromObject(loadJson(url));

        //输出返回结果
        System.out.println(jsonobject.toString());

        //取距离
        JSONArray resultsArray = jsonobject.getJSONArray("results");
        JSONObject distanceObject = resultsArray.getJSONObject(0);
        String distance_str = distanceObject.getString("distance");

        //把距离从String变为int
        int distance_int = Integer.parseInt(distance_str);
        return distance_int;
    }
}
