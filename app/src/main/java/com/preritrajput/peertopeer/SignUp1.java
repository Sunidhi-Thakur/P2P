package com.preritrajput.peertopeer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.preritrajput.peertopeer.databinding.ActivitySignUp1Binding;

public class SignUp1 extends AppCompatActivity {

    private ActivitySignUp1Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUp1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferences settings = getSharedPreferences(MainActivity.LOGIN, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("seenOnBoarding", true);
        editor.apply();
        binding.nextButton.setOnClickListener(v -> {
            Intent intent = new Intent(SignUp1.this, SignUp2.class);
            startActivity(intent);
        });

    }
}