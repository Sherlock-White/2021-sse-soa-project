package com.example.onlinecarhailing;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OrderFragment extends Fragment {

    private ListView mListView;
    private List<Object> mObjectList;
    private ArrayAdapter<Object> mArrayAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_order, container, false);


        Intent intenttest = getActivity().getIntent();
        String _username = intenttest.getStringExtra("name");
        mObjectList = new ArrayList<>();

        mListView = root.findViewById(R.id.lv);

        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "http://106.15.3.13:9003/v1/passengers/" + _username + "/orders";
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                Request request = new Request.Builder()
                        .url("http://106.15.3.13:9003/v1/passengers/client1/orders")
                        .method("GET", null)
                        .build();


                try {
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseData);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String orderid=jsonObject.getString("order_id");
                        String driver=jsonObject.getString("driver_id");
                        String driver_phone=jsonObject.getString("driver_phone");
                        String state=jsonObject.getString("order_state");
                        String departure=jsonObject.getString("departure");
                        String destination=jsonObject.getString("destination");



                        mObjectList.add("订单编号："+orderid+"\n"+"司机姓名："+driver+ " "+"司机手机："+driver_phone+"\n"+"订单状态："+state+"\n"+"出发地："+departure+"\n"+"目的地："+departure);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mObjectList);
                                mListView.setAdapter(mArrayAdapter);
                                Toast toastCenter = Toast.makeText(getActivity(), "获取订单信息成功", Toast.LENGTH_LONG);
                                //确定Toast显示位置，并显示
                                toastCenter.setGravity(Gravity.CENTER, 0, 0);
                                toastCenter.show();
                            }
                        });
                    }

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();

//        for(int i=0;i<50;i++){
//            mObjectList.add("sfd  "+i);
//        }



        return root;
    }
}
