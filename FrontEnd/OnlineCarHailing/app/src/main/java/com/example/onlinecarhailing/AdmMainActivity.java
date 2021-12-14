package com.example.onlinecarhailing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdmMainActivity extends AppCompatActivity {
    //声明控件变量
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_main);

        //绑定变量和控件
        bottomNavigationView=findViewById(R.id.bottomNav);

        //创建默认fragment
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new CertificationFragment()).commit();
        }

        //设置fragment的切换监听
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                //根据选择不同显示不同界面
                switch(item.getItemId()){
                    case R.id.certification:
                        fragment=new CertificationFragment();
                        break;
                    case R.id.complaint:
                        fragment=new ComplaintFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
                return true;
            }
        });
    }
}