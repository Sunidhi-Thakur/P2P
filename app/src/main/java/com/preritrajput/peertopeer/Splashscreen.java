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
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.preritrajput.peertopeer.databinding.ActivitySplashscreenBinding;

public class Splashscreen extends AppCompatActivity {

    private ActivitySplashscreenBinding binding;
    static int flag = 0;

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

        binding.squareLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.squareLogo.startAnimation(animFadeOut);
                binding.circleLogo.setVisibility(View.VISIBLE);
                animFadeIn.reset();
                binding.circleLogo.clearAnimation();
                binding.circleLogo.startAnimation(animFadeIn);
                binding.squareLogo.setVisibility(View.INVISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(Splashscreen.this, GetStarted.class);
                        startActivity(intent);
                        finish();
                    }
                }, 2200);
            }
        });

    }
}