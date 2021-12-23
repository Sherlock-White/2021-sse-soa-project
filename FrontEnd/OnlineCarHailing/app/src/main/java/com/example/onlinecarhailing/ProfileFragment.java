package com.example.onlinecarhailing;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProfileFragment extends Fragment {

    EditText phone_number, commonaddress1, commonaddress2, username, truename;
    Button change2;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_profile, container, false);

        Intent intenttest = getActivity().getIntent();
        String _username = intenttest.getStringExtra("name");
        change2 = root.findViewById(R.id.change2);
        username = root.findViewById(R.id.username2);
        phone_number = root.findViewById(R.id.phone_number2);
        truename = root.findViewById(R.id.truename2);
        commonaddress1 = root.findViewById(R.id.commonaddress1);
        commonaddress2 = root.findViewById(R.id.commonaddress2);
        username.setText(_username);
        final String[] _phone_number = {null};
        final String[] _truename = {null};
        final String[] _commonaddress1 = {null};
        final String[] _commonaddress2 = {null};
        final String[] passwd = {null};
        final String[] salt = {null};

//        String _username = username.getText().toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FormBody.Builder params = new FormBody.Builder();
                    params.add("name", _username);

                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://47.103.9.250:9000/api/v1/userservice/returnclientchage")
//                                    .url("http://127.0.0.1:9000/api/v1/userservice/registration")
                            .post(params.build())
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    JSONObject jsonObject = new JSONObject(responseData);
//                    Log.d("msg", jsonObject.getString("msg"));
//                    Log.d("object", jsonObject.getString("object"));
//                    String object = jsonObject.getString("object");
                    String code = jsonObject.getString("code");
                    JSONObject object2 = jsonObject.getJSONObject("object");
                    _phone_number[0] = object2.getString("phone");
                    _truename[0] = object2.getString("truename");
                    _commonaddress1[0] = object2.getString("commonaddress1");
                    _commonaddress2[0] = object2.getString("commonaddress2");
                    passwd[0] = object2.getString("passwd");
                    salt[0] = object2.getString("salt");

                    if (code.equals("200")) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                phone_number.setText(_phone_number[0]);
                                truename.setText(_truename[0]);
                                commonaddress1.setText(_commonaddress1[0]);
                                commonaddress2.setText(_commonaddress2[0]);
                                Toast toastCenter = Toast.makeText(getActivity(), "获取个人信息成功", Toast.LENGTH_LONG);
                                //确定Toast显示位置，并显示
                                toastCenter.setGravity(Gravity.CENTER, 0, 0);
                                toastCenter.show();
                            }
                        });

//                        Intent intent = new Intent(getActivity(), MainActivity.class);
//                        startActivity(intent);
                    } else {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast toastCenter = Toast.makeText(getActivity(), "获取个人信息失败", Toast.LENGTH_LONG);
                                //确定Toast显示位置，并显示
                                toastCenter.setGravity(Gravity.CENTER, 0, 0);
                                toastCenter.show();
                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast toastCenter = Toast.makeText(getActivity(), "获取个人信息失败", Toast.LENGTH_LONG);
                            //确定Toast显示位置，并显示
                            toastCenter.setGravity(Gravity.CENTER, 0, 0);
                            toastCenter.show();
                        }
                    });
                }

            }
        }).start();


        change2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //捕获按键对应的文本
                _phone_number[0] = phone_number.getText().toString();
                _truename[0] = truename.getText().toString();
                 _commonaddress1[0] = commonaddress1.getText().toString();
                _commonaddress2[0] = commonaddress2.getText().toString();
//                String _phone_number = phone_number.getText().toString();
//                String _password = password.getText().toString();
//                String _identity = identity.getText().toString();
//                String _verification_code = verification_code.getText().toString();
//                String _username = username.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
//                            String json = "{\n" +
//                                    "    \"commonaddress1\": \"string\",\n" +
//                                    "  \"commonaddress2\": \"string\",\n" +
//                                    "  \"id\": 0,\n" +
//                                    "  \"name\": \"string\",\n" +
//                                    "  \"passwd\": \"string\",\n" +
//                                    "  \"phone\": \"string\",\n" +
//                                    "  \"salt\": \"string\",\n" +
//                                    "  \"truename\": \"string\"\n" +
//                                    "}";
                            String json = "{\r\n" +
                                    "    \"commonaddress1\": \"" + _commonaddress1[0] + "\",\n" +
                                    "  \"commonaddress2\": \"" + _commonaddress2[0] + "\",\n" +
                                    "  \"name\": \"" + _username + "\",\n" +
                                    "  \"passwd\": \"" + passwd[0] + "\",\n" +
                                    "  \"phone\": \"" + _phone_number[0] + "\",\n" +
                                    "  \"salt\": \"" + salt[0] + "\",\n" +
                                    "  \"truename\": \"" + _truename[0] + "\"\n" +
                                    "}";
                            OkHttpClient client = new OkHttpClient().newBuilder()
                                    .build();
                            MediaType mediaType = MediaType.parse("application/json");
                            RequestBody body = RequestBody.create(mediaType, json);
                            Request request = new Request.Builder()
                                    .url("http://47.103.9.250:9000/api/v1/userservice/clientchage")
                                    .method("POST", body)
                                    .addHeader("Content-Type", "application/json")
                                    .build();
                            Response response = client.newCall(request).execute();
                            String responseData = response.body().string();
                            JSONObject jsonObject = new JSONObject(responseData);
                            String code = jsonObject.getString("code");

                            if (code.equals("200")) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast toastCenter = Toast.makeText(getActivity(), "修改信息成功\n已自动登录", Toast.LENGTH_LONG);
                                        //确定Toast显示位置，并显示
                                        toastCenter.setGravity(Gravity.CENTER, 0, 0);
                                        toastCenter.show();
                                    }
                                });


                            } else {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast toastCenter = Toast.makeText(getActivity(), "修改信息失败", Toast.LENGTH_LONG);
                                        //确定Toast显示位置，并显示
                                        toastCenter.setGravity(Gravity.CENTER, 0, 0);
                                        toastCenter.show();
                                    }
                                });
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast toastCenter = Toast.makeText(getActivity(), "修改失败", Toast.LENGTH_LONG);
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


        return root;
    }
}
