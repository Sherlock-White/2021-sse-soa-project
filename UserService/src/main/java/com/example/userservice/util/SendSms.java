package com.example.userservice.util;
//import okhttp3.FormBody;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;



import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Component
public class SendSms {
//    //对应阿里云accessKeyId
//    private static final String accessKeyId="";
//    private static final String accessKeySecert="";
//    private static final String signName="";
//    private static final String templateCode="";
//
//
//    public static void messagePost(String u_phone, String code){
//        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecert);
//        IAcsClient client = new DefaultAcsClient(profile);
//
//        CommonRequest request = new CommonRequest();
//        request.setSysMethod(MethodType.POST);
//        request.setSysDomain("dysmsapi.aliyuncs.com");
//        request.setSysVersion("2017-05-25");
//        request.setSysAction("SendSms");
////        request.putQueryParameter("RegionId", "cn-hangzhou");
//        request.putQueryParameter("PhoneNumbers",u_phone);
//        request.putQueryParameter("SignName", signName);
//        request.putQueryParameter("TemplateCode", templateCode);
//        request.putQueryParameter("TemplateParam", "{\"code\":"+code+"}");
//        try {
//            CommonResponse response = client.getCommonResponse(request);
//            System.out.println(response.getData());
//        } catch (ServerException e) {
//            e.printStackTrace();
//        } catch (ClientException e) {
//            e.printStackTrace();
//        }
//    }

    public static void messagePost1(String u_phone,String verifyCode) {   //1代表注册
        String host = "https://gyytz.market.alicloudapi.com";
        String path = "/sms/smsSend";
        String method = "POST";
        String appcode = "0e8f4286f5254b2bbe3419b3b6a89d87";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", u_phone);
        String content="**code**:"+verifyCode+",**minute**:3";
        System.out.println(content);
        querys.put("param", content);
        querys.put("smsSignId", "2e65b1bb3d054466b82f0c9d125465e2");
        querys.put("templateId", "a09602b817fd47e59e7c6e603d3f088d");
        Map<String, String> bodys = new HashMap<String, String>();


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void messagePost2(String u_phone,String verifyCode) {   //2代表重置密码
        String host = "https://gyytz.market.alicloudapi.com";
        String path = "/sms/smsSend";
        String method = "POST";
        String appcode = "0e8f4286f5254b2bbe3419b3b6a89d87";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", u_phone);
        String content="**code**:"+verifyCode+",**minute**:3";
        System.out.println(content);
        querys.put("param", content);
//        querys.put("param", "**code**:，**minute**:3分钟内有效，您正在进行注册，若非本人操作，请勿泄露。");
        querys.put("smsSignId", "2e65b1bb3d054466b82f0c9d125465e2");
        querys.put("templateId", "29833afb9ae94f21a3f66af908d54627");
        Map<String, String> bodys = new HashMap<String, String>();


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void messagePost3(String u_phone,String verifyCode) {   //3代表重置手机号
        String host = "https://gyytz.market.alicloudapi.com";
        String path = "/sms/smsSend";
        String method = "POST";
        String appcode = "0e8f4286f5254b2bbe3419b3b6a89d87";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", u_phone);
        String content="**code**:"+verifyCode+",**minute**:3";
        System.out.println(content);
        querys.put("param", content);
//        querys.put("param", "**code**:，**minute**:3分钟内有效，您正在进行注册，若非本人操作，请勿泄露。");
        querys.put("smsSignId", "2e65b1bb3d054466b82f0c9d125465e2");
        querys.put("templateId", "ea66d14c664649a69a19a6b47ba028db");
        Map<String, String> bodys = new HashMap<String, String>();


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void messagePost4(String truename,String identitynum) {   //3代表重置手机号
//        String url = "https://eid.shumaidata.com/eid/check";
//        String appCode = "你的AppCode";
//
//        Map<String, String> params = new HashMap<>();
//        params.put("idcard", "42112******82416");
//        params.put("name", "张*");
//        String result = postForm(appCode, url, params);
//        System.out.println(result);
//    }
//    public static String postForm(String appCode, String url, Map<String, String> params) throws IOException {
//        OkHttpClient client = new OkHttpClient.Builder().build();
//        FormBody.Builder formbuilder = new FormBody.Builder();
//        Iterator<String> it = params.keySet().iterator();
//        while (it.hasNext()) {
//            String key = it.next();
//            formbuilder.add(key, params.get(key));
//        }
//        FormBody body = formbuilder.build();
//        Request request = new Request.Builder().url(url).addHeader("Authorization", "APPCODE " + appCode).post(body).build();
//        Response response = client.newCall(request).execute();
//        System.out.println("返回状态码" + response.code() + ",message:" + response.message());
//        String result = response.body().string();
//        return result;
//    }

    public static synchronized String zcCode() {
        int max = 10000;
        int min = 1000;
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return "" + s;
    }

}