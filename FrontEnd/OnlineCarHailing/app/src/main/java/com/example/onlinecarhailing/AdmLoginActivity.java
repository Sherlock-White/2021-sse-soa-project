package com.example.onlinecarhailing;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

public class AdmLoginActivity extends AppCompatActivity {

    //声明变量
    EditText phone_number,password;
    TextView adm_entry;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_login);
        //把变量和控件进行绑定
        phone_number=findViewById(R.id.phone_number);
        password=findViewById(R.id.password);
        login=findViewById(R.id.login);
//        adm_entry=findViewById(R.id.adm_entry);

        //登录按钮的响应
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //捕获按键对应的文本
                String _phone_number=phone_number.getText().toString();
                String _password=password.getText().toString();

                //判断帐号和密码正确性
                //先写死一个哈
                if(_phone_number.equals("111")&&_password.equals("111")){
                    //定义Toast显示内容
                    Toast toastCenter = Toast.makeText(AdmLoginActivity.this,"登陆成功",Toast.LENGTH_LONG);
                    //确定Toast显示位置，并显示
                    toastCenter.setGravity(Gravity.CENTER,0,0);
                    toastCenter.show();

                    //跳转逻辑
                    Intent intent = new Intent(AdmLoginActivity.this, AdmMainActivity.class);
                    startActivity(intent);

                }else {
                    AlertDialog dialog;
                    dialog = new AlertDialog.Builder(AdmLoginActivity.this)
                            .setTitle("提示：")  //设置标题
                            .setMessage("密码错误\n请重新输入") //提示信息
                            .setPositiveButton("好的",null)
                            .create();  //创建对话框
                    dialog.show();  //显示对话框
                }
            }
        });

    }
}