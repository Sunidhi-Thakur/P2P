package com.preritrajput.peertopeer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.preritrajput.peertopeer.databinding.ActivityOtpverificationBinding;
import com.preritrajput.peertopeer.utility.GenericTextWatcher;

import org.jetbrains.annotations.NotNull;

public class OTPVerification extends AppCompatActivity {

    private ActivityOtpverificationBinding binding;
    private String OTP;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpverificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        OTP = getIntent().getStringExtra("auth");

        EditText[] edit = {binding.box1, binding.box2, binding.box3, binding.box4, binding.box5,binding.box6};

        binding.box1.addTextChangedListener(new GenericTextWatcher(binding.box1, edit));
        binding.box2.addTextChangedListener(new GenericTextWatcher(binding.box2, edit));
        binding.box3.addTextChangedListener(new GenericTextWatcher(binding.box3, edit));
        binding.box4.addTextChangedListener(new GenericTextWatcher(binding.box4, edit));
        binding.box5.addTextChangedListener(new GenericTextWatcher(binding.box5, edit));
        binding.box6.addTextChangedListener(new GenericTextWatcher(binding.box6, edit));

        binding.backButton.setOnClickListener(v -> {
            Intent intent = new Intent(OTPVerification.this, SignUp1.class);
            startActivity(intent);
        });

        binding.continueButton.setOnClickListener(v -> {
            String verification_code = binding.box1.getText().toString()+binding.box2.getText().toString()
                    +binding.box3.getText().toString()+binding.box4.getText().toString()
                    +binding.box5.getText().toString()+binding.box6.getText().toString();
            Log.d("Sunidhi", OTP+","+verification_code);
            if(checkCode(verification_code)) {
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
                    }

                }
            });
        }
}