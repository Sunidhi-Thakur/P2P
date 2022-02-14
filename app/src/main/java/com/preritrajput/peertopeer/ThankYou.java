package com.preritrajput.peertopeer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.preritrajput.peertopeer.databinding.ActivityThankyouBinding;

public class ThankYou extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.black_trans80));
        com.preritrajput.peertopeer.databinding.ActivityThankyouBinding binding = ActivityThankyouBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Shader shader = new LinearGradient(0, 0, 0, binding.appName.getLineHeight(),
                Color.parseColor("#b17be1"), Color.parseColor("#459b41ee"), Shader.TileMode.REPEAT);
        binding.appName.getPaint().setShader(shader);

        binding.discoverBtn.setOnClickListener(view -> {
            Intent intent = new Intent(ThankYou.this, AccountVerified.class);
            startActivity(intent);
            finish();
        });

        SharedPreferences settings = getSharedPreferences(OptionsPage.LOGIN, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("hasRegistered2", true);
        editor.apply();


    }
}