package com.example.onlinecarhailing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;


public class LoginActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    Button login;

    float v =0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tabLayout=findViewById(R.id.tab_layout);
        viewPager=findViewById(R.id.view_pager);

        tabLayout.addTab(tabLayout.newTab().setText("登录"));
        tabLayout.addTab(tabLayout.newTab().setText("注册"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final LoginAdapter adapter=new LoginAdapter(getSupportFragmentManager(),this.tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setTranslationY(300);
        tabLayout.setAlpha(v);

        tabLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();




    }
}
