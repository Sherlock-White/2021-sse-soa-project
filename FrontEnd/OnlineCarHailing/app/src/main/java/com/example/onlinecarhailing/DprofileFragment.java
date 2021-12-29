package com.example.onlinecarhailing;

import android.content.Intent;
import android.os.Bundle;
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

public class DprofileFragment extends Fragment {

    EditText phone_number, username, truename,identitynum,numberplate;
    Button change2,didentcode2;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_dprofile, container, false);

        Intent intenttest = getActivity().getIntent();
        String _username = intenttest.getStringExtra("name");
        didentcode2=root.findViewById(R.id.didentcode2);
        change2 = root.findViewById(R.id.dchange2);
        username = root.findViewById(R.id.dusername2);
        phone_number = root.findViewById(R.id.dphone_number2);
        truename = root.findViewById(R.id.dtruename2);
        identitynum=root.findViewById(R.id.dident2);
        numberplate=root.findViewById(R.id.dcarnum2);
        username.setText(_username);
        final String[] _phone_number = {null};
        final String[] _truename = {null};
        final String[] _commonaddress1 = {null};
        final String[] _commonaddress2 = {null};
        final String[] passwd = {null};
        final String[] salt = {null};
        final String[] _identitynum={null};
        final String[] _numberplate={null};
//        String _username = username.getText().toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FormBody.Builder params = new FormBody.Builder();
                    params.add("name", _username);

                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://47.103.9.250:9000/api/v1/userservice/returndriverchage")
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
                    _identitynum[0]=object2.getString("identitynum");
                    _numberplate[0]=object2.getString("numberplate");
                    passwd[0] = object2.getString("passwd");
                    salt[0] = object2.getString("salt");

                    if (code.equals("200")) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                phone_number.setText(_phone_number[0]);
                                truename.setText(_truename[0]);
                                identitynum.setText(_identitynum[0]);
                                numberplate.setText(_numberplate[0]);
//                                Toast toastCenter = Toast.makeText(getActivity(), "获取个人信息成功", Toast.LENGTH_LONG);
//                                //确定Toast显示位置，并显示
//                                toastCenter.setGravity(Gravity.CENTER, 0, 0);
//                                toastCenter.show();
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
                                        Toast toastCenter = Toast.makeText(getActivity(), "修改信息成功", Toast.LENGTH_LONG);
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

        didentcode2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //捕获按键对应的文本
//                _phone_number[0] = phone_number.getText().toString();
                _truename[0] = truename.getText().toString();
                _identitynum[0]=identitynum.getText().toString();
//                String _phone_number = phone_number.getText().toString();
//                String _password = password.getText().toString();
//                String _identity = identity.getText().toString();
//                String _verification_code = verification_code.getText().toString();
//                String _username = username.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String url="http://47.103.9.250:9000/api/v1/userservice/identify?name="+_username+"&truename="+_truename[0] +"&identifynum="+_identitynum[0] ;
                            OkHttpClient client = new OkHttpClient().newBuilder()
                                    .build();
                            MediaType mediaType = MediaType.parse("text/plain");
                            RequestBody body = RequestBody.create(mediaType, "");
                            Request request = new Request.Builder()
                                    .url(url)
                                    .method("POST", body)
                                    .build();
                            Response response = client.newCall(request).execute();
//                            Response response = client.newCall(request).execute();
                            String responseData = response.body().string();
                            JSONObject jsonObject = new JSONObject(responseData);
                            String code = jsonObject.getString("code");

                            if (code.equals("200")) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast toastCenter = Toast.makeText(getActivity(), "身份认证成功", Toast.LENGTH_LONG);
                                        //确定Toast显示位置，并显示
                                        toastCenter.setGravity(Gravity.CENTER, 0, 0);
                                        toastCenter.show();
                                    }
                                });


                            } else {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast toastCenter = Toast.makeText(getActivity(), "身份认证失败", Toast.LENGTH_LONG);
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
                                    Toast toastCenter = Toast.makeText(getActivity(), "身份认证失败", Toast.LENGTH_LONG);
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
