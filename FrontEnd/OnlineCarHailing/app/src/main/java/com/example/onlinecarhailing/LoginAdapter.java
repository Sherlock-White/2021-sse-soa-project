package com.example.onlinecarhailing;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class LoginAdapter extends FragmentPagerAdapter{
    int totalTabs;
    private String mTabs[]=new String[2];

    public LoginAdapter(FragmentManager fm,int totalTabs){
        super(fm);
        this.totalTabs=totalTabs;
        mTabs[0]="登录";
        mTabs[1]="注册";
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                LoginTabFragment loginTabFragment =new LoginTabFragment();
                return loginTabFragment;
            case 1:
                SignupTabFragment signupTabFragment = new SignupTabFragment();
                return signupTabFragment;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabs[position];
    };
}
