package com.preritrajput.peertopeer;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.preritrajput.peertopeer.databinding.ActivitySplashscreenBinding;

public class SplashScreen extends AppCompatActivity {

    private ActivitySplashscreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black_trans80));
        binding = ActivitySplashscreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Animation animFadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        Animation animFadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        Shader shader = new LinearGradient(0, 0, 0, binding.appName.getLineHeight(),
                Color.parseColor("#b17be1"), Color.parseColor("#459b41ee"), Shader.TileMode.REPEAT);
        binding.appName.getPaint().setShader(shader);

        SharedPreferences settings = getSharedPreferences(OptionsPage.LOGIN, 0);
        boolean hasLoggedIn = settings.getBoolean("hasLoggedIn", false);
        boolean hasRegistered = settings.getBoolean("hasRegistered", false);
        boolean hasRegistered2 = settings.getBoolean("hasRegistered2", false);
        boolean seenOnBoarding = settings.getBoolean("seenOnBoarding", false);

        binding.squareLogo.setOnClickListener(v -> {
            binding.squareLogo.startAnimation(animFadeOut);
            binding.circleLogo.setVisibility(View.VISIBLE);
            animFadeIn.reset();
            binding.circleLogo.clearAnimation();
            binding.circleLogo.startAnimation(animFadeIn);
            binding.squareLogo.setVisibility(View.INVISIBLE);
            new Handler().postDelayed(() -> {

                if (seenOnBoarding && !hasRegistered) {
                    Intent intent = new Intent(SplashScreen.this, OptionsPage.class);
                    startActivity(intent);
                    finish();
                } else if (seenOnBoarding && !(hasRegistered2)) {
                    Intent intent = new Intent(SplashScreen.this, SignUp2.class);
                    startActivity(intent);
                    finish();
                } else if (seenOnBoarding && !hasLoggedIn) {
                    Intent intent = new Intent(SplashScreen.this, OptionsPage.class);
                    startActivity(intent);
                    finish();
                }else if(hasRegistered && hasRegistered2 && hasLoggedIn){
                    Intent intent = new Intent(SplashScreen.this, ChooseFlow.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(SplashScreen.this, GetStarted.class);
                    startActivity(intent);
                    finish();
                }


            }, 2200);
        });
    }
}