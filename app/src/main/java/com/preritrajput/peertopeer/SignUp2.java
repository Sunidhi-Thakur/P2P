package com.preritrajput.peertopeer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.preritrajput.peertopeer.databinding.ActivitySignUp2Binding;

public class SignUp2 extends AppCompatActivity {
    private ActivitySignUp2Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUp2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.nextButton.setOnClickListener(v -> {
            SharedPreferences settings = getSharedPreferences(MainActivity.LOGIN, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("seenOnBoarding", true);
            editor.apply();
            editor.putBoolean("hasRegistered", true);
            editor.apply();
            editor.putBoolean("hasLoggedIn", true);
            editor.apply();

            Intent intent = new Intent(SignUp2.this, Dashboard.class);
            startActivity(intent);

        });
    }
}