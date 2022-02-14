package com.preritrajput.peertopeer;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.preritrajput.peertopeer.databinding.ActivityGetStartedBinding;

public class GetStarted extends AppCompatActivity {

    private ActivityGetStartedBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGetStartedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Animation animFadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        binding.parent.setBackgroundResource(R.drawable.car_dark);
        animFadeIn.reset();
        binding.parent.clearAnimation();
        binding.parent.startAnimation(animFadeIn);

        Shader shader = new LinearGradient(0, 0, 0, binding.appName.getLineHeight(),
                Color.parseColor("#b17be1"), Color.parseColor("#459b41ee"), Shader.TileMode.REPEAT);
        binding.appName.getPaint().setShader(shader);

        new Handler().postDelayed(() -> {
            Animation animFadeIn1 = AnimationUtils.loadAnimation(GetStarted.this, R.anim.fade_in_2);
            binding.getStarted.setVisibility(View.VISIBLE);
            animFadeIn1.reset();
            binding.getStarted.clearAnimation();
            binding.getStarted.startAnimation(animFadeIn1);
        }, 1000);


        binding.getStarted.setOnClickListener(v -> {
                Intent intent = new Intent(GetStarted.this, OnBoarding.class);
                startActivity(intent);
                finish();
        });
    }
}