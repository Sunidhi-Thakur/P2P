package com.preritrajput.peertopeer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;
import com.preritrajput.peertopeer.databinding.ActivitySignUp1Binding;
import com.preritrajput.peertopeer.db.UserHelper;

import java.util.concurrent.TimeUnit;

public class SignUp1 extends AppCompatActivity {

    private ActivitySignUp1Binding binding;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.black_trans80));
        binding = ActivitySignUp1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        binding.backButton.setOnClickListener(v -> {
            Intent intent = new Intent(SignUp1.this, OptionsPage.class);
            startActivity(intent);
        });

        binding.continueButton.setOnClickListener(v -> {
            String fName = binding.fNameEditTxt.getText().toString();
            String lName = binding.lNameEditTxt.getText().toString();
            String mobile = binding.mobEditText.getText().toString();
            String email = binding.emailEditText.getText().toString();

            boolean check = checkFields(fName, lName, mobile, email);
            if (check) {
                UserHelper user = new UserHelper(fName,lName,mobile, email);
                DatabaseReference reference = FirebaseDatabase.getInstance("https://peertopeer-5851a-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Registered Users");
                reference.child(mobile).setValue(user);

                binding.progressBar.setVisibility(View.VISIBLE);
                binding.continueButton.setVisibility(View.INVISIBLE);

                PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91"+mobile)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(SignUp1.this)
                        .setCallbacks(mCallBacks)
                        .build();

                PhoneAuthProvider.verifyPhoneNumber(options);

            }else{
                checkFields(fName, lName, mobile,email);
            }
        });
        mCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull @NotNull PhoneAuthCredential phoneAuthCredential) {
                signIn(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull @NotNull FirebaseException e) {
                binding.progressBar.setVisibility(View.INVISIBLE);
                binding.continueButton.setVisibility(View.VISIBLE);
                Toast.makeText(SignUp1.this, e.getMessage(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull @NotNull String s, @NonNull @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                binding.progressBar.setVisibility(View.INVISIBLE);
                binding.continueButton.setVisibility(View.VISIBLE);
                Toast.makeText(SignUp1.this, "OTP Sent",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUp1.this, OTPVerification.class);
                        intent.putExtra("auth",s);
                        intent.putExtra("resend", forceResendingToken);
                        intent.putExtra("phone", binding.mobEditText.getText().toString());
                        startActivity(intent);
            }
        };
    }

    private boolean checkFields(String fName, String lName, String mobile, String email) {
        boolean check = true;
        if(fName.length() == 0) {
            binding.fNameEditTxt.setError("First Name cannot be Empty");
            check = false;
        }
        else if (!fName.matches("[a-zA-Z]+")) {
            binding.fNameEditTxt.setError("First Name cannot have numbers or special characters");
            check = false;
        }

        if (lName.length() == 0) {
            binding.lNameEditTxt.setError("Last Name cannot be Empty");
            check = false;
        }

        else if (!lName.matches("[a-zA-Z]+")) {
            binding.lNameEditTxt.setError("Last Name cannot have numbers or special characters");
            check = false;
        }
        if (!mobile.matches("[0-9]+")) {
            binding.mobEditText.setError("Invalid Phone Number");
            check = false;
        }
        if (mobile.length() != 10) {
            binding.mobEditText.setError("Invalid Phone Number");
            check = false;
        }
        if(email.length() <= 5 || !email.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")){
            binding.emailEditText.setError("Invalid email address.");
            check = false;
        }
        return check;
    }

    private void signIn(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                Intent intent = new Intent(SignUp1.this, OTPVerification.class);
                startActivity(intent);
            }else{
                Toast.makeText(SignUp1.this, "Error occurred", Toast.LENGTH_SHORT).show();
            }

        });
    }
}