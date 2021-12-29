package com.example.onlinecarhailing;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.onlinecarhailing.hail.CarHailingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DrivermainActivity extends AppCompatActivity {

    //声明控件变量
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drivermain);

        //绑定变量和控件
        bottomNavigationView=findViewById(R.id.dbottomNav);

        //创建默认fragment
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.dfragmentContainer,new DcarHailingFragment()).commit();
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                //根据选择不同显示不同界面
                switch(item.getItemId()){
                    case R.id.dcar_hailing:
                        fragment=new DcarHailingFragment();
                        break;
                    case R.id.dprofile:
                        fragment=new DprofileFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.dfragmentContainer,fragment).commit();
                return true;
            }
        });


    }
}