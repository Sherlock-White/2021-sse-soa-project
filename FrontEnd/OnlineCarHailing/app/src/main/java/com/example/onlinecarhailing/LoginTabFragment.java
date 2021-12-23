package com.example.onlinecarhailing;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginTabFragment extends Fragment {

    //声明变量
    EditText username,password;
    TextView adm_entry,forgotpassword;
    Button login;

    //用来设置动画透明度的
    float v=0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.fragment_login_tab,container,false);

        //把变量和控件进行绑定
        username=root.findViewById(R.id.login0);
        password=root.findViewById(R.id.password);
        login=root.findViewById(R.id.login);
        adm_entry=root.findViewById(R.id.adm_entry);
        forgotpassword=root.findViewById(R.id.forgotpassword);

        //切换为"注册"时的动画设置
        //位置变化
        username.setTranslationY(800);
        password.setTranslationY(800);
        //透明度
        username.setAlpha(v);
        password.setAlpha(v);
        //动画
        username.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        password.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();


        //登录按钮的响应
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //捕获按键对应的文本
                String _name=username.getText().toString();
                String _password=password.getText().toString();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            FormBody.Builder params = new FormBody.Builder();
                            params.add("name", _name);
                            params.add("passwd", _password);


                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder()
                                    .url("http://47.103.9.250:9000/api/v1/userservice/login")
//                                    .url("http://127.0.0.1:9000/api/v1/userservice/registration")
                                    .post(params.build())
                                    .build();
                            Response response = client.newCall(request).execute();
                            String responseData = response.body().string();
                            JSONObject jsonObject = new JSONObject(responseData);
                            Log.d("msg", jsonObject.getString("msg"));
                            Log.d("object", jsonObject.getString("object"));
                            String object = jsonObject.getString("object");
                            String code=jsonObject.getString("code");

                            if(code.equals("200")){

                                SharedPreferences spf = getActivity().getSharedPreferences("spf",Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor=spf.edit();
                                editor.putString("name",_name);
                                editor.putString("password",_password);
                                editor.apply();
//                                Intent intenttest=new Intent(getActivity(),ProfileFragment.class);

//                                startActivity(intenttest);
                                getActivity().runOnUiThread( new  Runnable() {
                                    @Override
                                    public  void  run() {
                                        Toast toastCenter = Toast.makeText(getActivity(), "登录成功", Toast.LENGTH_LONG);
                                        //确定Toast显示位置，并显示
                                        toastCenter.setGravity(Gravity.CENTER, 0, 0);
                                        toastCenter.show();
                                    }
                                });

                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                intent.putExtra("name",_name);
                                startActivity(intent);
                            }
                            else {
                                getActivity().runOnUiThread( new  Runnable() {
                                    @Override
                                    public  void  run() {
                                        Toast toastCenter = Toast.makeText(getActivity(), "登录失败", Toast.LENGTH_LONG);
                                        //确定Toast显示位置，并显示
                                        toastCenter.setGravity(Gravity.CENTER, 0, 0);
                                        toastCenter.show();
                                    }
                                });
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            getActivity().runOnUiThread( new  Runnable() {
                                @Override
                                public  void  run() {
                                    Toast toastCenter = Toast.makeText(getActivity(), "登录失败", Toast.LENGTH_LONG);
                                    //确定Toast显示位置，并显示
                                    toastCenter.setGravity(Gravity.CENTER, 0, 0);
                                    toastCenter.show();
                                }
                            });
                        }

                    }
                }).start();

            }
        });

        //管理员入口按钮的响应
        //登录按钮的响应
        adm_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转逻辑
                Intent intent = new Intent(getActivity(), AdmLoginActivity.class);
                startActivity(intent);
            }
        });
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ForgotpwdActivity.class);
                startActivity(intent);
            }
        });


        return root;

    }
}
