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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class SignupTabFragment extends Fragment {

    //声明变量
    EditText phone_number,password,confirm;
    Button signup;

    //用来设置动画透明度的
    float v=0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.fragment_signup_tab,container,false);

        //把变量和控件进行绑定
        phone_number=root.findViewById(R.id.phone_number);
        password=root.findViewById(R.id.password);
        confirm=root.findViewById(R.id.confirm);
        signup=root.findViewById(R.id.signup);

        //切换为"登录"时的动画设置
        phone_number.setTranslationY(800);
        password.setTranslationY(800);
        confirm.setTranslationY(800);
        phone_number.setAlpha(v);
        password.setAlpha(v);
        confirm.setAlpha(v);
        phone_number.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        password.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        confirm.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();

        //注册按钮的响应
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //捕获按键对应的文本
                String _phone_number=phone_number.getText().toString();
                String _password=password.getText().toString();
                String _confirm=confirm.getText().toString();

                //现在只要两次密码输入正确就允许注册
                if(_password.equals(_confirm)){
                    //定义Toast显示内容
                    Toast toastCenter = Toast.makeText(getActivity(),"注册成功\n已自动登录",Toast.LENGTH_LONG);
                    //确定Toast显示位置，并显示
                    toastCenter.setGravity(Gravity.CENTER,0,0);
                    toastCenter.show();

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }else {
                    AlertDialog dialog;
                    dialog = new AlertDialog.Builder(getActivity())
                            .setTitle("提示：")  //设置标题
                            .setMessage("两次密码输入不一致\n请重新输入") //提示信息
                            .setPositiveButton("好的",null)
                            .create();  //创建对话框
                    dialog.show();  //显示对话框
                }
            }
        });

        return root;

    }
}
