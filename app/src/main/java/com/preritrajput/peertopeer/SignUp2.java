package com.preritrajput.peertopeer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.preritrajput.peertopeer.databinding.ActivitySignUp2Binding;

public class SignUp2 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.black_trans80));
        com.preritrajput.peertopeer.databinding.ActivitySignUp2Binding binding = ActivitySignUp2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.nextButton.setOnClickListener(v -> {
            Intent intent = new Intent(SignUp2.this, DrivingLicence.class);
            startActivity(intent);
            finish();
        });

        binding.backButton.setOnClickListener(v -> {
            Intent intent = new Intent(SignUp2.this, SignUp1.class);
            startActivity(intent);
        });

    }
}