package com.example.onlinecarhailing;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DcarHailingFragment extends Fragment {


    Button djidao,wancheng;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_dcar_hailing, container, false);
//        return inflater.inflate(R.layout.fragment_dcar_hailing,container,false);

        djidao=root.findViewById(R.id.djidao);
        wancheng=root.findViewById(R.id.wancheng);

        final String[] orderid = {null};

        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "http://106.15.3.13:9003/v1/passengers/client9/orders/current";
//                String url = "http://127.0.0.1:9003/v1/passengers/" + _username + "/orders";

                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                Request request = new Request.Builder()
                        .url(url)
                        .method("GET", null)
                        .build();


                try {
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    JSONObject jsonObject = new JSONObject(responseData);
                    orderid[0] =jsonObject.getString("order_id");
//                    JSONArray jsonArray = new JSONArray(responseData);

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        djidao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String url="http://139.224.251.185:8084/ordertaking-service/api/v1/pickingup?order_id="+orderid[0];
                        OkHttpClient client = new OkHttpClient().newBuilder()
                                .build();
                        MediaType mediaType = MediaType.parse("text/plain");
                        RequestBody body = RequestBody.create(mediaType, "");
                        Request request = new Request.Builder()
                                .url(url)
                                .method("POST", body)
                                .build();
                        try {
                            Response response = client.newCall(request).execute();
                            System.out.println(response);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();


            }
        });
        wancheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String url="http://139.224.251.185:8084/ordertaking-service/api/v1/arrival?order_id="+orderid[0];
                        OkHttpClient client = new OkHttpClient().newBuilder()
                                .build();
                        MediaType mediaType = MediaType.parse("text/plain");
                        RequestBody body = RequestBody.create(mediaType, "");
                        Request request = new Request.Builder()
                                .url(url)
                                .method("POST", body)
                                .build();
                        try {
                            Response response = client.newCall(request).execute();
                            System.out.println(response);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });




        return root;
    }
}
