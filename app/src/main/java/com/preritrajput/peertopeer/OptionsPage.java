package com.preritrajput.peertopeer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.View;

import com.preritrajput.peertopeer.databinding.ActivityGetStartedBinding;
import com.preritrajput.peertopeer.databinding.ActivityOptionsPageBinding;

public class OptionsPage extends AppCompatActivity {

    private ActivityOptionsPageBinding binding;
    public static final String LOGIN = "login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOptionsPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Shader shader = new LinearGradient(0, 0, 0, binding.appName.getLineHeight(),
                Color.parseColor("#b17be1"), Color.parseColor("#459b41ee"), Shader.TileMode.REPEAT);
        binding.appName.getPaint().setShader(shader);

        binding.LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OptionsPage.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OptionsPage.this, SignUp1.class);
                startActivity(intent);
                finish();
            }
        });


    }
}