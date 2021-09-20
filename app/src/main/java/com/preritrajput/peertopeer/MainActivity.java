package com.preritrajput.peertopeer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.preritrajput.peertopeer.databinding.ActivityLoginBinding;
import com.preritrajput.peertopeer.databinding.ActivitySplashLoginBinding;

public class MainActivity extends AppCompatActivity {

    private ActivitySplashLoginBinding binding;
    public static final String LOGIN = "login";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.black_trans80));
        binding = ActivitySplashLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        LottieAnimationView animationView;
        animationView =  findViewById(R.id.imgView_logo);
        SharedPreferences settings = getSharedPreferences(MainActivity.LOGIN, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("seenOnBoarding", true);
        editor.apply();

        animationView.playAnimation();

        binding.LoginButton.setOnClickListener(v -> {
            editor.putBoolean("hasLoggedIn", true);
            editor.apply();
            editor.putBoolean("hasRegistered", true);
            editor.apply();
            Intent intent = new Intent(MainActivity.this, Dashboard.class);
            startActivity(intent);
        });

        binding.tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignUp1.class);
            startActivity(intent);
        });


    }
}