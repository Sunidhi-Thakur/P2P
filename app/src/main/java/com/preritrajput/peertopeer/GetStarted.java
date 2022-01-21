package com.preritrajput.peertopeer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.preritrajput.peertopeer.databinding.ActivityGetStartedBinding;
import com.preritrajput.peertopeer.databinding.ActivitySplashscreenBinding;

public class GetStarted extends AppCompatActivity {

    private ActivityGetStartedBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGetStartedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        SharedPreferences settings = getSharedPreferences(OptionsPage.LOGIN, 0);
        boolean hasLoggedIn = settings.getBoolean("hasLoggedIn", false);
        boolean hasRegistered = settings.getBoolean("hasRegistered", false);
        boolean hasRegistered2 = settings.getBoolean("hasRegistered2", false);
        boolean seenOnBoarding = settings.getBoolean("seenOnBoarding", false);

        Animation animFadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        binding.parent.setBackgroundResource(R.drawable.car_dark);
        animFadeIn.reset();
        binding.parent.clearAnimation();
        binding.parent.startAnimation(animFadeIn);

        Shader shader = new LinearGradient(0, 0, 0, binding.appName.getLineHeight(),
                Color.parseColor("#b17be1"), Color.parseColor("#459b41ee"), Shader.TileMode.REPEAT);
        binding.appName.getPaint().setShader(shader);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation animFadeIn = AnimationUtils.loadAnimation(GetStarted.this, R.anim.fade_in_2);
                binding.getStarted.setVisibility(View.VISIBLE);
                animFadeIn.reset();
                binding.getStarted.clearAnimation();
                binding.getStarted.startAnimation(animFadeIn);
            }
        }, 1000);



        binding.getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (seenOnBoarding && !hasLoggedIn) {
//                    Intent intent = new Intent(GetStarted.this, OptionsPage.class);
//                    startActivity(intent);
//                    finish();
//                } else if (seenOnBoarding && !hasRegistered) {
//                    Intent intent = new Intent(GetStarted.this, SignUp1.class);
//                    startActivity(intent);
//                    finish();
//                } else if (seenOnBoarding && !(hasRegistered2)) {
//                    Intent intent = new Intent(GetStarted.this, SignUp2.class);
//                    startActivity(intent);
//                    finish();
//                } else if (hasLoggedIn && hasRegistered && hasRegistered2) {
//                    Intent intent = new Intent(GetStarted.this, Dashboard.class);
//                    startActivity(intent);
//                    finish();
//                } else {
                    Intent intent = new Intent(GetStarted.this, OnBoarding.class);
                    startActivity(intent);
                    finish();
                //}
            }
        });
    }
}