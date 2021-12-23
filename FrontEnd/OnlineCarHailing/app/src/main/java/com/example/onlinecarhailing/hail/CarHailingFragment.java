package com.example.onlinecarhailing.hail;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.onlinecarhailing.R;
import com.example.onlinecarhailing.hailwaiting.WaitingFragment;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CarHailingFragment extends Fragment {
    androidx.constraintlayout.utils.widget.ImageFilterButton location;
    TextView from,to;
    Button hail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.fragment_car_hailing,container,false);

        from=root.findViewById(R.id.from);
        to=root.findViewById(R.id.to);
        hail=root.findViewById(R.id.hail);
        System.out.println(hail.getText());



        hail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                MediaType mediaType = MediaType.parse("text/plain");
                RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("passenger_id","client2")
                        .addFormDataPart("from","同济大学嘉定校区")
                        .addFormDataPart("from_lng","44.3")
                        .addFormDataPart("from_lat","45.5")
                        .addFormDataPart("to","同济大学四平路校区")
                        .addFormDataPart("to_lng","23.3")
                        .addFormDataPart("to_lat","24.5")
                        .build();
                Request request = new Request.Builder()
                        .url("106.15.3.13:9001/api/v1/hailing")
                        .method("POST", body)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                } catch (IOException e) {
                    Toast toastCenter = Toast.makeText(getActivity(), "打车请求失败", Toast.LENGTH_LONG);
                    //确定Toast显示位置，并显示
                    toastCenter.setGravity(Gravity.CENTER, 0, 0);
                    toastCenter.show();
                }
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new WaitingFragment()).commit();
            }
        });

        return root;
    }
}
