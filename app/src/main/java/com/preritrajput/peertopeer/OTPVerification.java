package com.preritrajput.peertopeer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.preritrajput.peertopeer.databinding.ActivityOtpverificationBinding;
import com.preritrajput.peertopeer.utility.GenericTextWatcher;


import java.util.concurrent.TimeUnit;

public class OTPVerification extends AppCompatActivity {

    private ActivityOtpverificationBinding binding;
    private String OTP, phone;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpverificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        OTP = getIntent().getStringExtra("auth");
        phone = getIntent().getStringExtra("phone");

        EditText[] edit = {binding.box1, binding.box2, binding.box3, binding.box4, binding.box5,binding.box6};

        binding.box1.addTextChangedListener(new GenericTextWatcher(binding.box1, edit));
        binding.box2.addTextChangedListener(new GenericTextWatcher(binding.box2, edit));
        binding.box3.addTextChangedListener(new GenericTextWatcher(binding.box3, edit));
        binding.box4.addTextChangedListener(new GenericTextWatcher(binding.box4, edit));
        binding.box5.addTextChangedListener(new GenericTextWatcher(binding.box5, edit));
        binding.box6.addTextChangedListener(new GenericTextWatcher(binding.box6, edit));

        new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                binding.resend.setText("Resend in " + millisUntilFinished / 1000);
            }
            public void onFinish() {
               binding.resend.setText("Send now!");
            }
        }.start();

        binding.mobile.setText("+91 ******"+phone.substring(6));

        binding.resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resend = binding.resend.getText().toString();
                if (resend.equals("Send now!")) {
                    binding.resend.setText("Sent!");
                }
            }

        });

        binding.backButton.setOnClickListener(v -> {
            Intent intent = new Intent(OTPVerification.this, SignUp1.class);
            startActivity(intent);
        });

        binding.continueButton.setOnClickListener(v -> {
            String verification_code = binding.box1.getText().toString()+binding.box2.getText().toString()
                    +binding.box3.getText().toString()+binding.box4.getText().toString()
                    +binding.box5.getText().toString()+binding.box6.getText().toString();
            if(checkCode(verification_code)) {
                binding.progressBar.setVisibility(View.VISIBLE);
                SharedPreferences settings = getSharedPreferences(MainActivity.LOGIN, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("hasRegistered", true);
                editor.apply();
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(OTP, verification_code);
                signIn(credential);
            }else
                checkCode(verification_code);
        });
    }


    private boolean checkCode(String verification_code) {
        if (verification_code.isEmpty()) {
            Toast.makeText(OTPVerification.this, "Enter valid OTP", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

        private void signIn(PhoneAuthCredential credential){
            mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(OTPVerification.this, SignUp2.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(OTPVerification.this, "Incorrect OTP", Toast.LENGTH_SHORT).show();
                        binding.progressBar.setVisibility(View.INVISIBLE);
                    }

                }
            });
        }
}