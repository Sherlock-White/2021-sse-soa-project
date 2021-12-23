package com.example.onlinecarhailing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ForgotpwdActivity extends AppCompatActivity {


    EditText phone_number, newpassword, verification_code, username;
    Button resignup,getcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpwd);

        phone_number=findViewById(R.id.phone_number1);
        newpassword=findViewById(R.id.newpwd1);
        verification_code=findViewById(R.id.verification_code1);
        username=findViewById(R.id.myname1);

        getcode=findViewById(R.id.getcode1);
        resignup=findViewById(R.id.login1);

        getcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String _phone_number = phone_number.getText().toString();
                String _password = newpassword.getText().toString();
                String _verification_code = verification_code.getText().toString();
                String _username = username.getText().toString();



                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            FormBody.Builder params = new FormBody.Builder();
                            params.add("name",_username);
                            params.add("phone", _phone_number);


                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder()
                                    .url("http://47.103.9.250:9000/api/v1/userservice/resetphonecode")
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
                                runOnUiThread( new  Runnable() {
                                    @Override
                                    public  void  run() {
                                        Toast toastCenter = Toast.makeText(ForgotpwdActivity.this, "获取验证码成功", Toast.LENGTH_LONG);
                                        //确定Toast显示位置，并显示
                                        toastCenter.setGravity(Gravity.CENTER, 0, 0);
                                        toastCenter.show();
                                    }
                                });

                            }
                            else {
                                runOnUiThread( new  Runnable() {
                                    @Override
                                    public  void  run() {
                                        Toast toastCenter = Toast.makeText(ForgotpwdActivity.this, "获取验证码失败", Toast.LENGTH_LONG);
                                        //确定Toast显示位置，并显示
                                        toastCenter.setGravity(Gravity.CENTER, 0, 0);
                                        toastCenter.show();
                                    }
                                });
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            runOnUiThread( new  Runnable() {
                                @Override
                                public  void  run() {
                                    Toast toastCenter = Toast.makeText(ForgotpwdActivity.this, "获取验证码失败", Toast.LENGTH_LONG);
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

        resignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String _phone_number = phone_number.getText().toString();
                String _password = newpassword.getText().toString();
                String _verification_code = verification_code.getText().toString();
                String _username = username.getText().toString();



                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            FormBody.Builder params = new FormBody.Builder();
                            params.add("name",_username);
                            params.add("phone", _phone_number);
                            params.add("newpasswd",_password);
                            params.add("identifyCode",_verification_code);

                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder()
                                    .url("http://47.103.9.250:9000/api/v1/userservice/resetpasswords")
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
                                runOnUiThread( new  Runnable() {
                                    @Override
                                    public  void  run() {
                                        Toast toastCenter = Toast.makeText(ForgotpwdActivity.this, "重置密码成功", Toast.LENGTH_LONG);
                                        //确定Toast显示位置，并显示
                                        toastCenter.setGravity(Gravity.CENTER, 0, 0);
                                        toastCenter.show();
                                    }
                                });
                                Intent intent0 = new Intent(ForgotpwdActivity.this, LoginTabFragment.class);
                                startActivity(intent0);

                            }
                            else {
                                runOnUiThread( new  Runnable() {
                                    @Override
                                    public  void  run() {
                                        Toast toastCenter = Toast.makeText(ForgotpwdActivity.this, "重置密码失败", Toast.LENGTH_LONG);
                                        //确定Toast显示位置，并显示
                                        toastCenter.setGravity(Gravity.CENTER, 0, 0);
                                        toastCenter.show();
                                    }
                                });
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            runOnUiThread( new  Runnable() {
                                @Override
                                public  void  run() {
                                    Toast toastCenter = Toast.makeText(ForgotpwdActivity.this, "重置密码失败", Toast.LENGTH_LONG);
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

    }
}