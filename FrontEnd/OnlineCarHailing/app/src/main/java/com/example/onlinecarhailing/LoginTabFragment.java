package com.example.onlinecarhailing;

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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class LoginTabFragment extends Fragment {

    //声明变量
    EditText phone_number,password;
    TextView adm_entry;
    Button login;

    //用来设置动画透明度的
    float v=0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.fragment_login_tab,container,false);

        //把变量和控件进行绑定
        phone_number=root.findViewById(R.id.phone_number);
        password=root.findViewById(R.id.password);
        login=root.findViewById(R.id.login);
        adm_entry=root.findViewById(R.id.adm_entry);

        //切换为"注册"时的动画设置
        //位置变化
        phone_number.setTranslationY(800);
        password.setTranslationY(800);
        //透明度
        phone_number.setAlpha(v);
        password.setAlpha(v);
        //动画
        phone_number.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        password.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();


        //登录按钮的响应
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //捕获按键对应的文本
                String _phone_number=phone_number.getText().toString();
                String _password=password.getText().toString();

                //判断帐号和密码正确性
                //先写死一个哈
                if(_phone_number.equals("123")&&_password.equals("123")){
                    //定义Toast显示内容
                    Toast toastCenter = Toast.makeText(getActivity(),"登陆成功",Toast.LENGTH_LONG);
                    //确定Toast显示位置，并显示
                    toastCenter.setGravity(Gravity.CENTER,0,0);
                    toastCenter.show();

                    //跳转逻辑
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);

                }else {
                    AlertDialog dialog;
                    dialog = new AlertDialog.Builder(getActivity())
                            .setTitle("提示：")  //设置标题
                            .setMessage("密码错误\n请重新输入") //提示信息
                            .setPositiveButton("好的",null)
                            .create();  //创建对话框
                    dialog.show();  //显示对话框
                }
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

        return root;

    }
}
