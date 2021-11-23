package com.example.onlinecarhailing;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import org.w3c.dom.Text;


public class WelcomeActivity extends AppCompatActivity {

    ImageView logo;
    Button begin;
    LottieAnimationView lottieAnimationView;
    TextView name;
    Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        logo=findViewById(R.id.logo);
        name=findViewById(R.id.name);
        begin =findViewById(R.id.begin);

        lottieAnimationView=findViewById(R.id.lottie);

        anim= AnimationUtils.loadAnimation(this,R.anim.o_n_anim);

        begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}