package com.preritrajput.peertopeer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.preritrajput.peertopeer.databinding.ActivityOtpverificationBinding;

public class OTPVerification extends AppCompatActivity {

    private ActivityOtpverificationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpverificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backButton.setOnClickListener(v -> {
            Intent intent = new Intent(OTPVerification.this, SignUp1.class);
            startActivity(intent);
        });

        binding.continueButton.setOnClickListener(v -> {
            Intent intent = new Intent(OTPVerification.this, SignUp2.class);
            startActivity(intent);
        });



    }
}