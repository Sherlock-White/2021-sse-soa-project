package com.example.onlinecarhailing.hailwaiting;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinecarhailing.R;
import com.example.onlinecarhailing.hail.CarHailingFragment;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class WaitingFragment extends Fragment {
    private Button cancel;
    private TextView tv_timer;
    private Handler mHandler = new Handler();

    private Integer timer = 0;
    private String timeStr = "";
    private boolean isStopCount = false;

    private Runnable TimerRunnable = new Runnable() {

        @Override
        public void run() {
            if(!isStopCount){
                timer += 1;
//                timeStr = TimeUtil.getFormatTime(timer);
//                timeStr = TimeUnit.valueOf(timer.toString());
                Integer second = timer%60;
                Integer minute = timer/60%60;
                Integer hour = timer/60/60;
                timeStr=hour.toString() + ':'+minute + ':' + second;
                tv_timer.setText(timeStr);
            }
            countTimer();
        }
    };

    private void countTimer() {
        mHandler.postDelayed(TimerRunnable, 1000);
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        countTimer();
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.fragment_waiting,container,false);

        cancel=root.findViewById(R.id.cancel);
        tv_timer=root.findViewById(R.id.tv_timer);


        Intent intenttest = getActivity().getIntent();
        String passenger_id = intenttest.getStringExtra("name");



//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println("1");
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        OkHttpClient client = new OkHttpClient().newBuilder()
//                                .build();
//                        MediaType mediaType = MediaType.parse("text/plain");
//                        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
//                                .addFormDataPart("passenger_id",passenger_id)
//                                .build();
//                        Request request = new Request.Builder()
//                                .url("http://106.15.3.13:9001:9001/api/v1/cancellation")
//                                .method("POST", body)
//                                .build();
//                        try {
//                            Response response = client.newCall(request).execute();
//                        } catch (IOException e) {
//                            Toast toastCenter = Toast.makeText(getActivity(), "取消失败失败", Toast.LENGTH_LONG);
//                            //确定Toast显示位置，并显示
//                            toastCenter.setGravity(Gravity.CENTER, 0, 0);
//                            toastCenter.show();
//                        }
//                        getFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new CarHailingFragment()).commit();
//                    }
//                }).start();
//
//            }
//        });


        // Inflate the layout for this fragment
        return root;
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mHandler.removeCallbacks(TimerRunnable);
//    }
}