package com.preritrajput.peertopeer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_login);

        LottieAnimationView animationView;
        animationView =  findViewById(R.id.imgView_logo);

        animationView.playAnimation();

    }
}