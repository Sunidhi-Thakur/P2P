package com.preritrajput.peertopeer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.preritrajput.peertopeer.databinding.ActivityLoginBinding;
import com.preritrajput.peertopeer.databinding.ActivitySplashLoginBinding;

public class MainActivity extends AppCompatActivity {

    private ActivitySplashLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        LottieAnimationView animationView;
        animationView =  findViewById(R.id.imgView_logo);

        animationView.playAnimation();

        binding.LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OnBoarding.class);
                startActivity(intent);
            }
        });


    }
}